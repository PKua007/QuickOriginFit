package pl.edu.uj.student.kubala.piotr.qof.test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.uj.student.kubala.piotr.qof.ComboBoxFormatList;
import pl.edu.uj.student.kubala.piotr.qof.format.Format;
import pl.edu.uj.student.kubala.piotr.qof.ParsedParam;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.util.ArrayList;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

// QuickOriginFit - ComboBoxFormatListTest.java
//---------------------------------------------------------------------
// [opis pliku]
//---------------------------------------------------------------------
// Utworzono 10:35 25.10.2017 w IntelliJ IDEA
// (C)PKua, wszystkie prawa zastrzeżone
//---------------------------------------------------------------------

class ComboBoxFormatListTest {
    private ComboBoxFormatList freshList;
    private static MockFormat[] mockFormat;
    private final ListDataEvent[] event = new ListDataEvent[1];

    private ListDataListener testListListener = new ListDataListener() {
        @Override
        public void intervalAdded(ListDataEvent e) {
            event[0] = e;
        }

        @Override
        public void intervalRemoved(ListDataEvent e) {
            event[0] = e;
        }

        @Override
        public void contentsChanged(ListDataEvent e) {
            event[0] = e;
        }
    };

    static class MockFormat implements Format {

        private int idx;

        MockFormat(int idx) {
            this.idx = idx;
        }

        @Override
        public String generate(ArrayList<ParsedParam> params) {
            return null;
        }

        @Override
        public JPanel getExtraOptionsPane() {
            return null;
        }

        @Override
        public String toString() {
            return "MockFormat{" +
                    "idx=" + idx +
                    '}';
        }
    }

    @BeforeAll
    static void setUpClass() {
        mockFormat = IntStream.rangeClosed(0, 4)
                .mapToObj(MockFormat::new)
                .toArray(MockFormat[]::new);

    }

    @BeforeEach
    void setUp() {
        freshList = new ComboBoxFormatList();
    }

    private void addThree() {
        freshList.addFormat(mockFormat[0]);
        freshList.addFormat(mockFormat[1]);
        freshList.addFormat(mockFormat[2]);
    }

    @Test
    void addNullFormatShouldThrow() {
        assertThrows(NullPointerException.class, () -> freshList.addFormat(null));
    }

    @Test
    void addFormat() {
        addThree();
        assertEquals(3, freshList.getNumOfFormats());
        assertEquals(3, freshList.getSize());
    }

    @Test
    void addDuplicateShouldThrow() {
        addThree();
        assertThrows(IllegalArgumentException.class, () -> freshList.addFormat(mockFormat[0]));
    }

    @Test
    void getFormat() {
        addThree();
        assertEquals(mockFormat[0], freshList.getFormat(0));
        assertEquals(mockFormat[1], freshList.getFormat(1));
        assertEquals(mockFormat[2], freshList.getFormat(2));
        assertEquals(mockFormat[0], freshList.getElementAt(0));
        assertEquals(mockFormat[1], freshList.getElementAt(1));
        assertEquals(mockFormat[2], freshList.getElementAt(2));
    }

    @Test
    void getWrongFormatShouldThrow() {
        addThree();
        assertThrows(IndexOutOfBoundsException.class, () -> freshList.getFormat(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> freshList.getFormat(3));
        assertEquals(null, freshList.getElementAt(-1));
        assertEquals(null, freshList.getElementAt(3));
    }

    @Test
    void deleteFormatByIndex() {
        int left;

        addThree();
        left = freshList.deleteFormat(0);
        assertEquals(2, left);
        assertEquals(mockFormat[1], freshList.getFormat(0));
        assertEquals(mockFormat[2], freshList.getFormat(1));

        freshList.addFormat(mockFormat[3]);
        left = freshList.deleteFormat(1);
        assertEquals(2, left);
        assertEquals(mockFormat[1], freshList.getFormat(0));
        assertEquals(mockFormat[3], freshList.getFormat(1));

        freshList.addFormat(mockFormat[4]);
        left = freshList.deleteFormat(2);
        assertEquals(2, left);
        assertEquals(mockFormat[1], freshList.getFormat(0));
        assertEquals(mockFormat[3], freshList.getFormat(1));
    }

    @Test
    void deleteByWrongIndexShouldThrow() {
        addThree();
        assertThrows(IndexOutOfBoundsException.class, () -> freshList.deleteFormat(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> freshList.deleteFormat(3));
    }

    @Test
    void deleteByReference() {
        addThree();
        int left = freshList.deleteFormat(mockFormat[1]);
        assertEquals(2, left);
        assertEquals(mockFormat[0], freshList.getFormat(0));
        assertEquals(mockFormat[2], freshList.getFormat(1));
    }

    @Test
    void deleteByWrongReference() {
        addThree();
        int left = freshList.deleteFormat(mockFormat[3]);
        assertEquals(3, left);
        assertEquals(mockFormat[0], freshList.getFormat(0));
        assertEquals(mockFormat[1], freshList.getFormat(1));
        assertEquals(mockFormat[2], freshList.getFormat(2));
    }

    @Test
    void getValidFormatIdx() {
        addThree();
        assertEquals(0, freshList.getFormatIdx(mockFormat[0]));
        assertEquals(1, freshList.getFormatIdx(mockFormat[1]));
        assertEquals(2, freshList.getFormatIdx(mockFormat[2]));
    }

    @Test
    void getInvalidFormatIdx() {
        addThree();
        assertEquals(-1, freshList.getFormatIdx(mockFormat[3]));
    }

    @Test
    void setInvalidSelectedItemShouldNotThrow() {
        addThree();
        freshList.setSelectedItem(mockFormat[3]);
    }

    @Test
    void getNoSelectedItem() {
        assertEquals(null, freshList.getSelectedItem());
        addThree();
        freshList.setSelectedItem(mockFormat[3]);
        assertEquals(mockFormat[3], freshList.getSelectedItem());
    }

    @Test
    void getSelectedItem() {
        addThree();
        freshList.setSelectedItem(mockFormat[1]);
        assertEquals(mockFormat[1], freshList.getSelectedItem());
    }

    @Test
    void selectedItemOnFirstAdd() {
        freshList.addFormat(mockFormat[0]);
        assertEquals(mockFormat[0], freshList.getSelectedItem());
    }

    @Test
    void nullSelectedItem() {
        freshList.addFormat(mockFormat[0]);
        freshList.setSelectedItem(null);
        assertEquals(null, freshList.getSelectedItem());
    }

    @Test
    void selectedItemOnDelete() {
        addThree();
        freshList.setSelectedItem(mockFormat[0]);
        freshList.deleteFormat(mockFormat[0]);
        assertEquals(mockFormat[1], freshList.getSelectedItem());
        freshList.setSelectedItem(mockFormat[2]);
        freshList.deleteFormat(mockFormat[2]);
        assertEquals(mockFormat[1], freshList.getSelectedItem());
        freshList.deleteFormat(mockFormat[1]);
        assertEquals(null, freshList.getSelectedItem());
    }

    @Test
    void testListenerOnAdd() {
        prepareListenerTest();
        freshList.addFormat(mockFormat[0]);
        freshList.addFormat(mockFormat[1]);   // Add two not to grab selection event
        assertEquals(ListDataEvent.INTERVAL_ADDED, event[0].getType());
        assertEquals(1, event[0].getIndex0());
        assertEquals(1, event[0].getIndex1());
    }

    @Test
    void testListenerOnDelete() {
        addThree();
        prepareListenerTest();

        freshList.deleteFormat(mockFormat[1]);
        assertEquals(1, event[0].getIndex0());
        assertEquals(1, event[0].getIndex1());
        assertEquals(ListDataEvent.INTERVAL_REMOVED, event[0].getType());
    }

    @Test
    void testListenerOnSelection() {
        addThree();
        prepareListenerTest();

        freshList.setSelectedItem(mockFormat[1]);
        assertEquals(ListDataEvent.CONTENTS_CHANGED, event[0].getType());

        resetEventGrabber();
        freshList.setSelectedItem(mockFormat[1]);
        assertNull(event[0]);
    }

    private void resetEventGrabber() {
        event[0] = null;
    }

    private void prepareListenerTest() {
        resetEventGrabber();
        freshList.addListDataListener(testListListener);
    }
}