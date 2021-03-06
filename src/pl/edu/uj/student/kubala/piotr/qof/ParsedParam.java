// QuickOriginFit - ParsedParam.java
//---------------------------------------------------------------------
// [opis pliku]
//---------------------------------------------------------------------
// Utworzono 22:43 24.10.2017 w IntelliJ IDEA
// (C)PKua, wszystkie prawa zastrzeżone
//---------------------------------------------------------------------

package pl.edu.uj.student.kubala.piotr.qof;

import java.util.Objects;

public class ParsedParam extends ParamInfo {
    private double value;
    private double error;

    public ParsedParam(String name, String unit, double value, double error) {
        super(name, unit);
        this.value = value;
        this.error = error;
    }

    public ParsedParam(ParamInfo info, double value, double error) {
        super(info);
        this.value = value;
        this.error = error;
    }

    public double getValue() {
        return value;
    }

    public double getError() {
        return error;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setError(double error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "ParsedParam{" +
                "value=" + value +
                ", error=" + error +
                "} " + super.toString();
    }
}
