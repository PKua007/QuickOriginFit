package pl.edu.uj.student.kubala.piotr.qof;// QuickOriginFit - TipTextFormatterFactory.java
//---------------------------------------------------------------------
// [opis pliku]
//---------------------------------------------------------------------
// Utworzono 15:17 22.10.2017 w IntelliJ IDEA
// (C)PKua, wszystkie prawa zastrzeżone
//---------------------------------------------------------------------

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import java.text.ParseException;

class TipTextFormatterFactory extends DefaultFormatterFactory
{
    private static class TextOnNullFormatter extends JFormattedTextField.AbstractFormatter {

        private String textToShow;
        public TextOnNullFormatter(String textToShow) {
            this.textToShow = textToShow;
        }

        @Override
        public Object stringToValue(String text) {
            return text;
        }

        @Override
        public String valueToString(Object value) {
            return value == null || "".equals(value) ? textToShow : value.toString();
        }

    }

    private static class TransparentFormatter extends JFormattedTextField.AbstractFormatter {

        @Override
        public Object stringToValue(String text) {
            return text;
        }

        @Override
        public String valueToString(Object value) {
            return value == null ? "" : value.toString();
        }

    }

    public TipTextFormatterFactory(String textToShow) {
        super(new TransparentFormatter(), new TextOnNullFormatter(textToShow));
    }
}
