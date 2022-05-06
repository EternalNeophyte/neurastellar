package edu.psuti.alexandrov;

import edu.psuti.alexandrov.stellar.StellarPresets;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StellarPresetsTester {

    @Test
    public void testSetup() {
        assertDoesNotThrow(StellarPresets::setup);
    }
}
