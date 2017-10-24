// QuickOriginFit - Main.java
//---------------------------------------------------------------------
// [opis pliku]
//---------------------------------------------------------------------
// Utworzono 22:42 24.10.2017 w IntelliJ IDEA
// (C)PKua, wszystkie prawa zastrzeżone
//---------------------------------------------------------------------

package pl.edu.uj.student.kubala.piotr.qof;

public class Main {
    public static void main(String[] args) {
        Frame.installLookAndFeel();

        // Model
        ComboBoxFormatList formatList = new ComboBoxFormatList();
        formatList.addFormat(new LatexFormat());
        ParamInfoList paramInfoList = new DefaultParamInfoList();

        // Controller
        FrameController controller = new FrameController(formatList, paramInfoList);

        // View
        Frame frame = new Frame(controller);
        frame.getFormatList().setModel(formatList);

        // Init
        EDTInitManager.getInstance().initElements();
    }
}
