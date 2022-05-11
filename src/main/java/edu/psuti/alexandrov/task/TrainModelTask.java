package edu.psuti.alexandrov.task;

import ai.djl.Model;
import ai.djl.metric.Metrics;
import ai.djl.training.Trainer;
import ai.djl.translate.TranslateException;
import edu.psuti.alexandrov.MetaProperties;
import edu.psuti.alexandrov.stellar.StellarPresets;
import javafx.concurrent.Task;

import java.io.IOException;
import java.time.LocalDateTime;

import static ai.djl.training.EasyTrain.trainBatch;
import static ai.djl.training.EasyTrain.validateBatch;

/**
 * Created on 11.05.2022
 *
 * @author alexandrov
 */
public class TrainModelTask extends Task<Model> implements MetaProperties.Training {

    @Override
    protected Model call() {
        var presets = StellarPresets.setup();
        String modelName = "stellar-model-" + LocalDateTime.now();

        try (Model model = Model.newInstance(modelName)) {
            var config = presets.trainingConfig();
            var trainingDataset = presets.trainingDataset();
            var validationDataset = presets.validationDataset();
            try (Trainer trainer = model.newTrainer(config)) {
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
            } catch (TranslateException | IOException e) {
                throw new RuntimeException(e);
            }
            updateMessage("Обучение модели завершено");
            return model;
        }
    }

}
