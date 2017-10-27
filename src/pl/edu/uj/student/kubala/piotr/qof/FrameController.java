// QuickOriginFit - FrameController.java
//---------------------------------------------------------------------
// [opis pliku]
//---------------------------------------------------------------------
// Utworzono 22:43 24.10.2017 w IntelliJ IDEA
// (C)PKua, wszystkie prawa zastrzeżone
//---------------------------------------------------------------------

package pl.edu.uj.student.kubala.piotr.qof;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.event.ActionEvent;

public class FrameController implements EDTInitializable {
    private FormatList formatList;
    private ParamInfoList paramInfoList;
    private Document textOutput;
    private Document textInput;
    private GenerateAction generateAction;
    private Format selectedFormat;

    public FrameController(FormatList formatList, ParamInfoList paramInfoList, Document textInput, Document textOutput) {
        this.formatList = formatList;
        this.paramInfoList = paramInfoList;
        this.textInput = textInput;
        this.textOutput = textOutput;

        generateAction = new GenerateAction();
        EDTInitManager manager = EDTInitManager.getInstance();
        manager.registerElement(this);
    }

    @Override
    public void init() {

    }

    @Override
    public String getEDTInitializableName() {
        return "Frame Controller";
    }

    public void onFormatChange(Format newFormat) {
        setOutputText("Zmiana formatu: " + newFormat);
        selectedFormat = newFormat;
    }

    public GenerateAction getGenerateAction() {
        return generateAction;
    }

    private class GenerateAction extends AbstractAction {

        public GenerateAction() {
            super("Generuj");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (selectedFormat != null) {
                setOutputText("Wprowadzono: " + getInputText() + "\n"
                        + "Wybrany format: " + formatList.getFormatIdx(selectedFormat) + ": " + selectedFormat);
            }
        }
    }

    private void setOutputText(String str) {
        try {
            textOutput.remove(0, textOutput.getLength());
            textOutput.insertString(0, str, null);
        } catch (BadLocationException e1) {
            throw new AssertionError();
        }
    }

    private String getInputText() {
        try {
            return textInput.getText(0, textInput.getLength());
        } catch (BadLocationException e) {
            throw new AssertionError();
        }
    }
}
