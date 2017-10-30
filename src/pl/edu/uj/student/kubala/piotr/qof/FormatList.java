// QuickOriginFit - FormatList.java
//---------------------------------------------------------------------
// [opis pliku]
//---------------------------------------------------------------------
// Utworzono 22:43 24.10.2017 w IntelliJ IDEA
// (C)PKua, wszystkie prawa zastrzeżone
//---------------------------------------------------------------------

package pl.edu.uj.student.kubala.piotr.qof;

import pl.edu.uj.student.kubala.piotr.qof.format.Format;

public interface FormatList {
    void addFormat(Format format);
    Format getFormat(int i);
    int getNumOfFormats();
    int deleteFormat(int i);
    int deleteFormat(Format format);
    int getFormatIdx(Format format);
}
