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
}
