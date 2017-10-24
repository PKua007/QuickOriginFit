// QuickOriginFit - Main.java
//---------------------------------------------------------------------
// [opis pliku]
//---------------------------------------------------------------------
// Utworzono 22:42 24.10.2017 w IntelliJ IDEA
// (C)PKua, wszystkie prawa zastrzeżone
//---------------------------------------------------------------------

package pl.edu.uj.student.kubala.piotr.qof;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Frame.installLookAndFeel();
        Frame frame = new Frame();
        EDTInitManager manager = EDTInitManager.getInstance();
        manager.registerElement(frame);
        manager.initElements();
    }
}
