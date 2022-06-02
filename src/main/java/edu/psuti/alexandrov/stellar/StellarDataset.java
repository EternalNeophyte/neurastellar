package edu.psuti.alexandrov.stellar;

import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.NDManager;
import ai.djl.training.dataset.RandomAccessDataset;
import ai.djl.training.dataset.Record;
import ai.djl.util.Progress;
import com.opencsv.bean.CsvToBeanBuilder;
import edu.psuti.alexandrov.MetaProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.util.Collections.emptyList;

public class StellarDataset extends RandomAccessDataset implements MetaProperties.NeuralNetwork {

    private final List<StellarObject> csvObjects;

    public StellarDataset(Builder builder) {
        super(builder);
        this.csvObjects = builder.csvObjects;
    }

    public static StellarDataset fromFile(Path pathToFile) {
        return new StellarDataset.Builder(pathToFile)
                .setSampling(BATCH_SIZE, RANDOM_SAMPLING)
                .build();
    }

    @Override
    public Record get(NDManager manager, long index) {
        var stellarObject = csvObjects.get(Math.toIntExact(index));
        var label = manager.create(stellarObject.getOutputClass().toString());
        var datum = stellarObject
                .datumStream()
                .map(manager::create)
                .toArray(NDArray[]::new);
        return new Record(new NDList(datum), new NDList(label));
    }

    @Override
    protected long availableSize() {
        return csvObjects.size();
    }

    @Override
    public void prepare(Progress progress) { }


    public static final class Builder extends BaseBuilder<Builder> implements MetaProperties.Csv {

        private static final Logger LOG = LoggerFactory.getLogger(Builder.class);
        private final List<StellarObject> csvObjects;

        public Builder(Path pathToFile) {
            this.csvObjects = loadCsvObjects(pathToFile);
        }

        private static List<StellarObject> loadCsvObjects(Path pathToFile) {
            try(var reader = Files.newBufferedReader(pathToFile)) {
                return new CsvToBeanBuilder<StellarObject>(reader)
                        .withType(StellarObject.class)
                        .withSeparator(SEPARATOR)
                        .withSkipLines(SKIP_LINES)
                        .withIgnoreQuotations(IGNORE_QUOTATIONS)
                        .withOrderedResults(false)
                        .build()
                        .parse();
            } catch (IOException e) {
                LOG.error("Failed to read data from " + pathToFile, e);
                return emptyList();
            }
        }

        public StellarDataset build() {
            return new StellarDataset(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
