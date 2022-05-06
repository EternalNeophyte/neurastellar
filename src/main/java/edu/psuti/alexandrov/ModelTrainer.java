package edu.psuti.alexandrov;

import ai.djl.Model;
import ai.djl.metric.Metrics;
import ai.djl.training.Trainer;
import ai.djl.translate.TranslateException;
import edu.psuti.alexandrov.stellar.StellarPresets;

import java.io.IOException;
import java.text.DateFormat;
import java.time.LocalDateTime;

/**
 * Created on 06.05.2022
 *
 * @author alexandrov
 */
public class ModelTrainer implements Runnable, MetaProperties.Trainig {

    @Override
    public void run() {
        var presets = StellarPresets.setup();
        String modelName = "stellar-model-" + LocalDateTime.now();
        try(Model model = Model.newInstance(modelName)) {

            var config = presets.trainingConfig();
            var dataset = presets.dataset();
            try(Trainer trainer = model.newTrainer(config)) {
                trainer.initialize();
                trainer.setMetrics(new Metrics());
                for(int i = 0; i < EPOCHS; i++) {
                    trainer.iterateDataset(dataset)
                            .forEach(batch -> {
                                trainer.evaluate(batch.getData());
                                trainer.step();
                                batch.close();
                            });
                    //trainer.iterateDataset(dataset)
                            //.forEach(batch -> validate);
                }
            } catch (TranslateException | IOException e) {
                throw new RuntimeException(e);
            }

        }
    }


}
