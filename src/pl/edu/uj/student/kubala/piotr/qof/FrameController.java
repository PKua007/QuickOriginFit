// QuickOriginFit - FrameController.java
//---------------------------------------------------------------------
// [opis pliku]
//---------------------------------------------------------------------
// Utworzono 22:43 24.10.2017 w IntelliJ IDEA
// (C)PKua, wszystkie prawa zastrzeżone
//---------------------------------------------------------------------

package pl.edu.uj.student.kubala.piotr.qof;

import pl.edu.uj.student.kubala.piotr.qof.format.Format;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

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
        appendOutputText("Zmiana formatu: " + newFormat + "\n");
        selectedFormat = newFormat;
    }

    public void onParamNameChange(int index, String newName) {
        appendOutputText("Zmiana nazwy: " + index + " " + newName + "\n");
        paramInfoList.getParamInfo(index).setName(newName);
    }

    public void onParamUnitChange(int index, String newUnit) {
        appendOutputText("Zmiana jednostki: " + index + " " + newUnit + "\n");
        paramInfoList.getParamInfo(index).setUnit(newUnit);
    }

    public GenerateAction getGenerateAction() {
        return generateAction;
    }

    private class GenerateAction extends AbstractAction {

        private final InputParser inputParser = new InputParser();

        public GenerateAction() {
            super("Generuj");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (selectedFormat != null) {
                // Try to parse input
                ArrayList<ParsedParam> params = null;
                try {
                    params = inputParser.parse(getInputText(), paramInfoList);
                } catch (ParseException e1) {
                    setOutputText("Wystąpił błąd:\n" + e1.getMessage());
                }

                // If parsed, generate output
                if (params != null) {
                    String out = selectedFormat.generate(params);
                    setOutputText(out);
                }
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

    private void appendOutputText(String str) {
        try {
            textOutput.insertString(textOutput.getLength(), str, null);
        } catch (BadLocationException e) {
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
