package edu.psuti.alexandrov;

import java.nio.file.Path;

public interface MetaProperties {

    interface Csv {

        Path PATH_TO_TRAINING_DATA = Path.of("src\\main\\resources\\csv\\star_cl_train.csv"),
        PATH_TO_VALIDATION_DATA = Path.of("src\\main\\resources\\csv\\star_cl_validation.csv");
        char SEPARATOR = ',';
        int SKIP_LINES = 0;
        boolean IGNORE_QUOTATIONS = true;
    }

    interface NeuralNetwork {

        long INPUTS = 9,
                FIRST_LAYER_UNITS = 16,
                SECOND_LAYER_UNITS = 25,
                THIRD_LAYER_UNITS = 16,
                OUTPUTS = 3;
        int BATCH_SIZE = 64;
        boolean RANDOM_SAMPLING = true;
    }

    interface Training {

        int N_THREADS = 4, EPOCHS = 10;
        String OUTPUT_DIR = "src\\main\\resources\\training";
    }
}
