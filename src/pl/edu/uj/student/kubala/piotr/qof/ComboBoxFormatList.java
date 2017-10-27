// QuickOriginFit - ListModelFormatList.java
//---------------------------------------------------------------------
// [opis pliku]
//---------------------------------------------------------------------
// Utworzono 22:43 24.10.2017 w IntelliJ IDEA
// (C)PKua, wszystkie prawa zastrzeżone
//---------------------------------------------------------------------

package pl.edu.uj.student.kubala.piotr.qof;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Objects;

public class ComboBoxFormatList extends AbstractListModel<Format> implements FormatList, ComboBoxModel<Format> {

    private ArrayList<Format> formats = new ArrayList<>();
    private Object selectedItem;

    @Override
    public void addFormat(Format format) {
        if (getFormatIdx(format) != -1)
            throw new IllegalArgumentException(format + " already in list");
        formats.add(Objects.requireNonNull(format));
        fireIntervalAdded(this, getNumOfFormats() - 1, getNumOfFormats() - 1);
        if (getNumOfFormats() == 1 && selectedItem == null)
            setSelectedItem(format);
    }

    @Override
    public Format getFormat(int i)
    {
        return formats.get(i);
    }

    @Override
    public int getNumOfFormats() {
        return formats.size();
    }

    @Override
    public int deleteFormat(int i) {
        if (getElementAt(i) == selectedItem) {
            if (i == 0)
                setSelectedItem(getSize() == 1 ? null : getElementAt(i + 1));
            else
                setSelectedItem(getElementAt(i - 1));
        }
        formats.remove(i);
        fireIntervalRemoved(this, i, i);
        return getNumOfFormats();
    }

    @Override
    public int deleteFormat(Format format) {
        int idx = getFormatIdx(format);
        if (idx != -1)
            deleteFormat(idx);
        return getNumOfFormats();
    }

    @Override
    public int getFormatIdx(Format format) {
        return formats.indexOf(format);
    }

    @Override
    public void setSelectedItem(Object anItem) {
        if ((selectedItem != null && !selectedItem.equals( anItem )) || selectedItem == null && anItem != null) {
            selectedItem = anItem;
            fireContentsChanged(this, -1, -1);
        }

    }

    @Override
    public Object getSelectedItem() {
        return selectedItem;
    }

    @Override
    public int getSize() {
        return getNumOfFormats();
    }

    @Override
    public Format getElementAt(int index) {
        try {
            return getFormat(index);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
}
