package pl.edu.uj.student.kubala.piotr.qof;// QuickOriginFit - Frame.java
//---------------------------------------------------------------------
// [opis pliku]
//---------------------------------------------------------------------
// Utworzono 00:05 22.10.2017 w IntelliJ IDEA
// (C)PKua, wszystkie prawa zastrzeżone
//---------------------------------------------------------------------

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Frame implements EDTInitializable {
    private JPanel contentPane;
    private JTextArea inputArea;
    private JTextArea outputArea;
    private JComboBox<Format> formatList;
    private JButton generateButton;
    private JLabel lineLabel;
    private JFormattedTextField aName;
    private JFormattedTextField aUnit;
    private JFormattedTextField bName;
    private JFormattedTextField bUnit;
    private JPanel extraOptionsPane;
    private FrameController controller;

    public Frame(FrameController controller) {

        this.controller = controller;
        EDTInitManager manager = EDTInitManager.getInstance();
        manager.registerElement(this);
        manager.addDependency(this, controller);
    }

    @Override
    public void init() {
        aName.setFormatterFactory(new TipTextFormatterFactory("nazwa parametru a"));
        aUnit.setFormatterFactory(new TipTextFormatterFactory("jednostka parametru a"));
        bName.setFormatterFactory(new TipTextFormatterFactory("nazwa parametru b"));
        bUnit.setFormatterFactory(new TipTextFormatterFactory("jednostka parametru b"));

        generateButton.setAction(controller.getGenerateAction());

        // Setup listeners
        final Handler handler = new Handler();
        formatList.addItemListener(handler);
        aName.addPropertyChangeListener(handler);
        aUnit.addPropertyChangeListener(handler);
        bName.addPropertyChangeListener(handler);
        bUnit.addPropertyChangeListener(handler);

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

    public JComboBox<Format> getFormatList() {
        return formatList;
    }

    public JTextArea getInputArea() {
        return inputArea;
    }

    public JTextArea getOutputArea() {
        return outputArea;
    }

    public JButton getGenerateButton() {
        return generateButton;
    }

    public JLabel getLineLabel() {
        return lineLabel;
    }

    public JFormattedTextField getaName() {
        return aName;
    }

    public JFormattedTextField getaUnit() {
        return aUnit;
    }

    public JFormattedTextField getbName() {
        return bName;
    }

    public JFormattedTextField getbUnit() {
        return bUnit;
    }

    public JPanel getExtraOptionsPane() {
        return extraOptionsPane;
    }

    @Override
    public String getEDTInitializableName() {
        return "Main Frame";
    }

    private class Handler implements ItemListener, PropertyChangeListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED)
                controller.onFormatChange((Format) e.getItem());
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            Object source = evt.getSource();
            String propertyName = evt.getPropertyName();
            if ("value".equals(propertyName)) {
                final String newValue = evt.getNewValue() == null ? "" : (String) evt.getNewValue();
                if (source == aName)
                    controller.onParamNameChange(0, newValue);
                else if (source == aUnit)
                    controller.onParamUnitChange(0, newValue);
                else if (source == bName)
                    controller.onParamNameChange(1, newValue);
                else if (source == bUnit)
                    controller.onParamUnitChange(1, newValue);
            }
        }
    }
}
