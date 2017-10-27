// QuickOriginFit - Main.java
//---------------------------------------------------------------------
// [opis pliku]
//---------------------------------------------------------------------
// Utworzono 22:42 24.10.2017 w IntelliJ IDEA
// (C)PKua, wszystkie prawa zastrzeżone
//---------------------------------------------------------------------

package pl.edu.uj.student.kubala.piotr.qof;

import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

public class Main {
    public static void main(String[] args) {
        Frame.installLookAndFeel();

        // Model
        ComboBoxFormatList formatList = new ComboBoxFormatList();
        ParamInfoList paramInfoList = new DefaultParamInfoList();
        Document textInput = new PlainDocument();
        Document textOutput = new PlainDocument();

        // Controller
        FrameController controller = new FrameController(formatList, paramInfoList, textInput, textOutput);

        // View
        Frame frame = new Frame(controller);
        frame.getFormatList().setModel(formatList);
        frame.getInputArea().setDocument(textInput);
        frame.getOutputArea().setDocument(textOutput);

        // Init
        EDTInitManager.getInstance().initElements();
        SwingUtilities.invokeLater(() -> initModel(formatList));
    }

    public static void initModel(ComboBoxFormatList formatList) {
        formatList.addFormat(new FancyLatexFormat());
        formatList.addFormat(new PlainLatexFormat());
    }
}
