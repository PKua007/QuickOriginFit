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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ParsedParam param = (ParsedParam) o;
        return Double.compare(param.value, value) == 0 &&
                Double.compare(param.error, error) == 0;
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), value, error);
    }
}
