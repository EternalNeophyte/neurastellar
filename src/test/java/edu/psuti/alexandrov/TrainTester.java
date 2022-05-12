package edu.psuti.alexandrov;

import edu.psuti.alexandrov.task.TrainModelTask;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class TrainTester {

    @Test
    public void train() {
        assertDoesNotThrow(() -> {
            TrainModelTask task = new TrainModelTask();
            var m = task.callModel();
        });
    }
}
