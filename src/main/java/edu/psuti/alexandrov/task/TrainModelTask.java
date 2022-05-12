package edu.psuti.alexandrov.task;

import ai.djl.MalformedModelException;
import ai.djl.Model;
import ai.djl.metric.Metrics;
import ai.djl.ndarray.types.Shape;
import ai.djl.training.Trainer;
import ai.djl.translate.TranslateException;
import edu.psuti.alexandrov.MetaProperties;
import edu.psuti.alexandrov.stellar.StellarPresets;
import javafx.concurrent.Task;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import static ai.djl.training.EasyTrain.trainBatch;
import static ai.djl.training.EasyTrain.validateBatch;
import static edu.psuti.alexandrov.MetaProperties.NeuralNetwork.*;

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
                int trainded, validated, batchSize;
                long total = (trainingDataset.size() + validationDataset.size()) * EPOCHS;

                for (int i = 1; i <= EPOCHS; i++) {
                    updateMessage("Эпоха " + i + " из " + EPOCHS);
                    trainded = validated = 0;

                    for (var batch : trainer.iterateDataset(trainingDataset)) {
                        batchSize = batch.getSize();
                        updateMessage("Обучение на фрагменте данных " + trainded + "-" +
                                (trainded += batchSize) + "...");
                        updateProgress(batchSize, total);
                        trainBatch(trainer, batch);
                        trainer.step();
                        batch.close();
                    }

                    for (var batch : trainer.iterateDataset(validationDataset)) {
                        batchSize = batch.getSize();
                        updateMessage("Валидация по фрагменту данных " + validated + "-" +
                                (validated += batchSize) + "...");
                        updateProgress(batchSize, total);
                        validateBatch(trainer, batch);
                        batch.close();
                    }
                }
                updateMessage("Обучение модели завершено. Сохранение...");
                model.save(Paths.get(OUTPUT_DIR), modelName);
            } catch (TranslateException | IOException e) {
                throw new RuntimeException(e);
            }
            return model;
        }

    }

}
