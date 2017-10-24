// QuickOriginFit - ListModelFormatList.java
//---------------------------------------------------------------------
// [opis pliku]
//---------------------------------------------------------------------
// Utworzono 22:43 24.10.2017 w IntelliJ IDEA
// (C)PKua, wszystkie prawa zastrzeżone
//---------------------------------------------------------------------

package pl.edu.uj.student.kubala.piotr.qof;

import javax.swing.*;
import javax.swing.event.ListDataListener;

public class ListModelFormatList implements FormatList, ListModel<Format> {
    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public Format getElementAt(int index) {
        return null;
    }

    @Override
    public void addListDataListener(ListDataListener l) {

    }

    @Override
    public void removeListDataListener(ListDataListener l) {

    }

    @Override
    public void addFormat(Format format) {

    }

    @Override
    public Format getFormat(int i) {
        return null;
    }

    @Override
    public int getNumOfFormats() {
        return 0;
    }

    @Override
    public int deleteFormat(int i) {
        return 0;
    }

    @Override
    public int deleteFormat(Format format) {
        return 0;
    }

    @Override
    public int getFormatIdx(Format format) {
        return 0;
    }
}
