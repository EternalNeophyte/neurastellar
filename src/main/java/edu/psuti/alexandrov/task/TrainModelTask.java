package edu.psuti.alexandrov.task;

import ai.djl.MalformedModelException;
import ai.djl.Model;
import ai.djl.metric.Metrics;
import ai.djl.ndarray.types.Shape;
import ai.djl.training.EasyTrain;
import ai.djl.training.Trainer;
import ai.djl.training.dataset.Batch;
import ai.djl.training.dataset.RandomAccessDataset;
import ai.djl.translate.TranslateException;
import edu.psuti.alexandrov.MetaProperties;
import edu.psuti.alexandrov.stellar.StellarPresets;
import javafx.concurrent.Task;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.function.BiConsumer;

import static ai.djl.training.EasyTrain.trainBatch;

/**
 * Created on 11.05.2022
 *
 * @author alexandrov
 */
public class TrainModelTask extends Task<Model> implements MetaProperties.Training {

    public Model callModel() {
        return call();
    }

    @Override
    protected Model call() {
        var presets = StellarPresets.setup();
        String modelName = "stellar-model-" + LocalDateTime.now();

        try (Model model = Model.newInstance(modelName)) {
            var config = presets.trainingConfig();
            var trainingDataset = presets.trainingDataset();
            var validationDataset = presets.validationDataset();

            model.setBlock(presets.neuralNetwork());

            try (Trainer trainer = model.newTrainer(config)) {

                trainer.initialize(new Shape(-1, 256));
                trainer.setMetrics(new Metrics());
                long total = (trainingDataset.size() + validationDataset.size()) * EPOCHS;

                for (int i = 1; i <= EPOCHS; i++) {
                    updateMessage("Эпоха " + i + " из " + EPOCHS);
                    iterateWith(trainer, trainingDataset, "Обучение на фрагменте данных", total,
                                (tr, b) -> {
                                    trainBatch(trainer, b);
                                    trainer.step();
                                });
                    iterateWith(trainer, validationDataset, "Валидация по фрагменту данных",
                                total, EasyTrain::validateBatch);
                }
                updateMessage("Обучение модели завершено. Сохранение...");
                model.save(Paths.get(OUTPUT_DIR), modelName);
            } catch (TranslateException | IOException e) {
                throw new RuntimeException(e);
            }
            return model;
        }

    }

    private void iterateWith(Trainer trainer,
                             RandomAccessDataset dataset,
                             String uniqueMessagePart,
                             long total,
                             BiConsumer<Trainer, Batch> trainBatchConsumer) throws TranslateException, IOException {
        int iterated = 0, batchSize;
        for (var batch : trainer.iterateDataset(dataset)) {
            batchSize = batch.getSize();
            updateMessage("%s: %d-%d...".formatted(uniqueMessagePart, iterated, iterated += batchSize));
            updateProgress(batchSize, total);
            trainBatchConsumer.accept(trainer, batch);
            batch.close();
        }
    }

}
