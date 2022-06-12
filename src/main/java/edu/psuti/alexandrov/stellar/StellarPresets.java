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
import ai.djl.training.initializer.XavierInitializer;
import ai.djl.training.listener.EpochTrainingListener;
import ai.djl.training.listener.TrainingListener;
import ai.djl.training.loss.Loss;
import ai.djl.training.optimizer.Optimizer;
import ai.djl.training.tracker.LinearTracker;
import edu.psuti.alexandrov.MetaProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;

public record StellarPresets
        (RandomAccessDataset trainingDataset,
         RandomAccessDataset validationDataset,
         Block neuralNetwork,
         TrainingConfig trainingConfig)
        implements MetaProperties.Csv, MetaProperties.NeuralNetwork, MetaProperties.Training {

    private static final Logger LOG = LoggerFactory.getLogger(StellarPresets.class);

    public static StellarPresets setup() {
        return new StellarPresets
                (newTrainingDataset(),
                 newValidationDataset(),
                 newNeuralNetwork(),
                 newTrainingConfig());
    }

    private static RandomAccessDataset newTrainingDataset() {
        LOG.info("Loading training dataset...");
        return StellarDataset.fromFile(PATH_TO_TRAINING_DATA);
    }

    private static RandomAccessDataset newValidationDataset() {
        LOG.info("Loading validation dataset...");
        return StellarDataset.fromFile(PATH_TO_VALIDATION_DATA);
    }

    private static Block newNeuralNetwork() {
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
        var optimizer = Optimizer.sgd()
                        .setRescaleGrad(1.0f / BATCH_SIZE)
                        .optWeightDecays(0.00001f)
                        .optMomentum(0.9f)
                        .setLearningRateTracker(LinearTracker.builder()
                                .optSlope(1000f)
                                .build()
                        )
                        .build();
        var loss = Loss.sigmoidBinaryCrossEntropyLoss();
        var initializer = new XavierInitializer(
                XavierInitializer.RandomType.UNIFORM,
                XavierInitializer.FactorType.AVG,
                2.24f);
        var executorService = Executors.newFixedThreadPool(N_THREADS);
        return new DefaultTrainingConfig(loss)
                .optOptimizer(optimizer)
                .optInitializer(initializer, "Initializer")
                .optDevices(new Device[] { Device.cpu() })
                .optExecutorService(executorService)
                .addEvaluator(new Accuracy("Accuracy", 2))
                .addTrainingListeners(TrainingListener.Defaults.logging())
                .addTrainingListeners(new EpochTrainingListener());
    }

}
