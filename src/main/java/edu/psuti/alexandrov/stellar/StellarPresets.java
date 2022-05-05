package edu.psuti.alexandrov.stellar;

import ai.djl.Device;
import ai.djl.nn.Activation;
import ai.djl.nn.Block;
import ai.djl.nn.Blocks;
import ai.djl.nn.SequentialBlock;
import ai.djl.nn.core.Linear;
import ai.djl.training.DefaultTrainingConfig;
import ai.djl.training.TrainingConfig;
import ai.djl.training.dataset.RandomAccessDataset;
import ai.djl.training.evaluator.Accuracy;
import ai.djl.training.listener.TrainingListener;
import ai.djl.training.loss.Loss;
import com.opencsv.bean.CsvToBeanBuilder;
import edu.psuti.alexandrov.MetaProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public record StellarPresets
        (RandomAccessDataset dataset,
         Block neuralNetwork,
         TrainingConfig trainingConfig)
        implements MetaProperties.Csv, MetaProperties.NeuralNetwork {

    private static final Logger LOG = LoggerFactory.getLogger(StellarPresets.class);

    public static StellarPresets setup() {
        return new StellarPresets(newDataset(), newNeuralNetwork(), newTrainingConfig());
    }

    private static List<StellarObject> newCsvObjects() {
        try(var reader = Files.newBufferedReader(PATH_TO_TRAINING_DATA)) {
            LOG.info("Reading data from CSV...");
            return new CsvToBeanBuilder<StellarObject>(reader)
                    .withType(StellarObject.class)
                    .withSeparator(SEPARATOR)
                    .withSkipLines(SKIP_LINES)
                    .withIgnoreQuotations(IGNORE_QUOTATIONS)
                    .build()
                    .parse();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static RandomAccessDataset newDataset() {
        return new StellarDataset.Builder(newCsvObjects()).build();
    }

    private static Block newNeuralNetwork() {
        //tahn, sigmoid - better for classification: https://neurohive.io/ru/osnovy-data-science/activation-functions/
        return new SequentialBlock()
                .add(Blocks.batchFlattenBlock(INPUTS))
                .add(Linear.builder()
                        .setUnits(FIRST_LAYER_UNITS)
                        .build()
                )
                .add(Activation::tanh)
                .add(Linear.builder()
                        .setUnits(SECOND_LAYER_UNITS)
                        .build()
                )
                .add(Activation::sigmoid)
                .add(Linear.builder()
                        .setUnits(THIRD_LAYER_UNITS)
                        .build()
                )
                .add(Activation::tanh)
                .add(Linear.builder()
                        .setUnits(OUTPUTS)
                        .build()
                );
    }

    private static TrainingConfig newTrainingConfig() {
        return new DefaultTrainingConfig(Loss.sigmoidBinaryCrossEntropyLoss())
                .addEvaluator(new Accuracy())
                //.optDevices()
                .addTrainingListeners(TrainingListener.Defaults.logging())
        ;
    }

}
