// QuickOriginFit - FrameController.java
//---------------------------------------------------------------------
// [opis pliku]
//---------------------------------------------------------------------
// Utworzono 22:43 24.10.2017 w IntelliJ IDEA
// (C)PKua, wszystkie prawa zastrzeżone
//---------------------------------------------------------------------

package pl.edu.uj.student.kubala.piotr.qof;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class FrameController implements EDTInitializable {
    private FormatList formatList;
    private ParamInfoList paramInfoList;
    private GenerateAction generateAction;

    public FrameController(FormatList formatList, ParamInfoList paramInfoList) {
        this.formatList = formatList;
        this.paramInfoList = paramInfoList;

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

    public void onFormatChange(int newIdx) {

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

        }
    }
}
