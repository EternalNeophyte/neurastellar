package edu.psuti.alexandrov;

import ai.djl.Model;
import ai.djl.metric.Metrics;
import ai.djl.training.Trainer;
import ai.djl.training.dataset.RandomAccessDataset;
import ai.djl.translate.TranslateException;
import edu.psuti.alexandrov.stellar.StellarPresets;

import java.io.IOException;
import java.time.LocalDateTime;

import static ai.djl.training.EasyTrain.trainBatch;
import static ai.djl.training.EasyTrain.validateBatch;

public final class Neuractions implements MetaProperties.Training {

    private Neuractions() {
        throw new AssertionError("Not supposed to be instantiated");
    }

    public static void train() {
        var presets = StellarPresets.setup();
        String modelName = "stellar-model-" + LocalDateTime.now();

        try(Model model = Model.newInstance(modelName)) {
            var config = presets.trainingConfig();
            var trainingDataset = presets.trainingDataset();
            RandomAccessDataset validationDataset = null;
            try(Trainer trainer = model.newTrainer(config)) {
                trainer.setMetrics(new Metrics());

                for(int i = 0; i < EPOCHS; i++) {
                    for(var batch : trainer.iterateDataset(trainingDataset)) {
                        trainBatch(trainer, batch);
                        trainer.step();
                        batch.close();
                    }

                    for(var batch : trainer.iterateDataset(validationDataset)) {
                        validateBatch(trainer, batch);
                        batch.close();
                    }
                }
            } catch (TranslateException | IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
