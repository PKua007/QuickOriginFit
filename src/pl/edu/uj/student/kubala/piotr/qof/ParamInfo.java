// QuickOriginFit - ParamInfo.java
//---------------------------------------------------------------------
// [opis pliku]
//---------------------------------------------------------------------
// Utworzono 22:43 24.10.2017 w IntelliJ IDEA
// (C)PKua, wszystkie prawa zastrzeżone
//---------------------------------------------------------------------

package pl.edu.uj.student.kubala.piotr.qof;

public class ParamInfo {
    private String name;
    private String unit;

    public ParamInfo() {
        this("", "");
    }

    public ParamInfo(String name, String unit) {
        this.name = name;
        this.unit = unit;
    }

    public ParamInfo(ParamInfo other) {
        this.name = other.name;
        this.unit = other.unit;
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "ParamInfo{" +
                "name='" + name + '\'' +
                ", unit='" + unit + '\'' +
                '}';
    }
}
