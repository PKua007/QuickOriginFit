package pl.edu.uj.student.kubala.piotr.qof;// QuickOriginFit - Frame.java
//---------------------------------------------------------------------
// [opis pliku]
//---------------------------------------------------------------------
// Utworzono 00:05 22.10.2017 w IntelliJ IDEA
// (C)PKua, wszystkie prawa zastrzeżone
//---------------------------------------------------------------------

import javax.swing.*;

public class Frame implements EDTInitializable {
    private JPanel contentPane;
    private JTextArea inputArea;
    private JTextArea outputArea;
    private JComboBox formatList;
    private JButton generateButton;
    private JLabel lineLabel;
    private JFormattedTextField aName;
    private JFormattedTextField aUnit;
    private JFormattedTextField bName;
    private JFormattedTextField bUnit;
    private JPanel extraOptionsPane;

    @Override
    public void init() {
        aName.setFormatterFactory(new TipTextFormatterFactory("nazwa parametru a"));
        aUnit.setFormatterFactory(new TipTextFormatterFactory("jednostka parametru a"));
        bName.setFormatterFactory(new TipTextFormatterFactory("nazwa parametru b"));
        bUnit.setFormatterFactory(new TipTextFormatterFactory("jednostka parametru b"));

        JFrame frame = new JFrame("Frame");
        frame.setContentPane(contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setMinimumSize(frame.getSize());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void installLookAndFeel() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }
    }

    @Override
    public String getEDTInitializableName() {
        return "Main Frame";
    }
}
