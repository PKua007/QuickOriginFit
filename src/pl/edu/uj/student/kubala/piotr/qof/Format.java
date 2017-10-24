// QuickOriginFit - Format.java
//---------------------------------------------------------------------
// [opis pliku]
//---------------------------------------------------------------------
// Utworzono 22:43 24.10.2017 w IntelliJ IDEA
// (C)PKua, wszystkie prawa zastrzeżone
//---------------------------------------------------------------------

package pl.edu.uj.student.kubala.piotr.qof;

import javax.swing.*;
import java.util.ArrayList;

public interface Format {
    String generate(ArrayList<ParsedParam> params);
    JPanel getExtraOptionsPane();
}
