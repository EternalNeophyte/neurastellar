package edu.psuti.alexandrov.stellar;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;

import java.io.Serializable;
import java.math.BigDecimal;

public class StellarObject implements Serializable {

    @CsvBindByName(column = "obj_ID")
    String getId;

    @CsvBindByName(column = "spec_obj_ID")
    String specialId;

    @CsvBindByName(column = "alpha")
    BigDecimal rightAscension;

    @CsvBindByName(column = "delta")
    BigDecimal declination;

    @CsvBindByName(column = "u")
    BigDecimal ultraviolet;

    @CsvBindByName(column = "g")
    BigDecimal green;

    @CsvBindByName(column = "r")
    BigDecimal red;

    @CsvBindByName(column = "i")
    BigDecimal nearInfrared;

    @CsvBindByName(column = "z")
    BigDecimal infrared;

    @CsvBindByName(column = "run_ID")
    int run;

    @CsvBindByName(column = "rerun_ID")
    int rerun;

    @CsvBindByName(column = "cam_col")
    int cameraColumn;

    @CsvBindByName(column = "field_ID")
    int field;

    @CsvCustomBindByName(column = "class", converter = StellarClassConverter.class)
    StellarClass outputClass;

    @CsvBindByName(column = "redshift")
    BigDecimal redshift;

    @CsvBindByName(column = "plate")
    int plate;

    @CsvBindByName(column = "MJD")
    int modifiedJulianDate;

    @CsvBindByName(column = "fiber_ID")
    int fiber;

    public void setGetId(String getId) {
        this.getId = getId;
    }

    public void setSpecialId(String specialId) {
        this.specialId = specialId;
    }

    public void setRightAscension(BigDecimal rightAscension) {
        this.rightAscension = rightAscension;
    }

    public void setDeclination(BigDecimal declination) {
        this.declination = declination;
    }

    public void setUltraviolet(BigDecimal ultraviolet) {
        this.ultraviolet = ultraviolet;
    }

    public void setGreen(BigDecimal green) {
        this.green = green;
    }

    public void setRed(BigDecimal red) {
        this.red = red;
    }

    public void setNearInfrared(BigDecimal nearInfrared) {
        this.nearInfrared = nearInfrared;
    }

    public void setInfrared(BigDecimal infrared) {
        this.infrared = infrared;
    }

    public void setRun(int run) {
        this.run = run;
    }

    public void setRerun(int rerun) {
        this.rerun = rerun;
    }

    public void setCameraColumn(int cameraColumn) {
        this.cameraColumn = cameraColumn;
    }

    public void setField(int field) {
        this.field = field;
    }

    public void setOutputClass(StellarClass outputClass) {
        this.outputClass = outputClass;
    }

    public void setRedshift(BigDecimal redshift) {
        this.redshift = redshift;
    }

    public void setPlate(int plate) {
        this.plate = plate;
    }

    public void setModifiedJulianDate(int modifiedJulianDate) {
        this.modifiedJulianDate = modifiedJulianDate;
    }

    public void setFiber(int fiber) {
        this.fiber = fiber;
    }
}
