// QuickOriginFit - LatexFormat.java
//---------------------------------------------------------------------
// [opis pliku]
//---------------------------------------------------------------------
// Utworzono 22:43 24.10.2017 w IntelliJ IDEA
// (C)PKua, wszystkie prawa zastrzeżone
//---------------------------------------------------------------------

package pl.edu.uj.student.kubala.piotr.qof.format;

import pl.edu.uj.student.kubala.piotr.qof.ParsedParam;

import javax.swing.*;
import java.util.ArrayList;

public class PlainLatexFormat implements Format {
    @Override
    public String generate(ArrayList<ParsedParam> params) {
        return getClass().getSimpleName() + " " + params;
    }

    @Override
    public JPanel getExtraOptionsPane() {
        return null;
    }

    @Override
    public String toString() {
        return "LaTeX (czysty)";
    }
}
