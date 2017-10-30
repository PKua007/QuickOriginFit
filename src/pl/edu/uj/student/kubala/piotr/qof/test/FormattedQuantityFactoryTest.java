package pl.edu.uj.student.kubala.piotr.qof.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.uj.student.kubala.piotr.qof.formatting.FormattedQuantity;
import pl.edu.uj.student.kubala.piotr.qof.formatting.FormattedQuantityFactory;
import pl.edu.uj.student.kubala.piotr.qof.formatting.Quantity;

import static org.junit.jupiter.api.Assertions.*;
// QuickOriginFit - FormattedQuantityFactoryTest.java
//---------------------------------------------------------------------
// [opis pliku]
//---------------------------------------------------------------------
// Utworzono 23:10 30.10.2017 w IntelliJ IDEA
// (C)PKua, wszystkie prawa zastrzeżone
//---------------------------------------------------------------------

class FormattedQuantityFactoryTest {

    private FormattedQuantityFactory factory;
    private FormattedQuantity formattedQuantity;

    private void format(double value, double error) {
        formattedQuantity = factory.format(new Quantity(value, error));
    }

    private void assertQuantity(String value, String valueDigits, String error, String errorDigits,
                                boolean scientificForm, int exponent) {
        assertEquals(value, formattedQuantity.getValue());
        assertEquals(valueDigits, formattedQuantity.getValueDigits());
        assertEquals(error, formattedQuantity.getError());
        assertEquals(errorDigits, formattedQuantity.getErrorDigits());
        assertEquals(scientificForm, formattedQuantity.isScientificForm());
        assertEquals(exponent, formattedQuantity.getExponent());
    }

    @BeforeEach
    void setupFactory() {
        factory = new FormattedQuantityFactory();
    }

    @Test
    void decimalWithErrorWithFractionalPart() {
        format(34.34, 2.55);
        assertQuantity("34.3", "343", "2.6", "26", false, 0);
    }

    @Test
    void negativeDecimal() {
        format(-34.34, 2.55);
        assertQuantity("-34.3", "343", "2.6", "26", false, 0);
    }

    @Test
    void decimalWithErrorTouchingFractionalPart() {
        format(345.34, 27.556);
        assertQuantity("345", "345", "28", "28", false, 0);
    }

    @Test
    void decimalWithErrorNotTouchingFractionalPart() {
        format(8345.34, 277.556);
        assertQuantity("8350", "835", "280", "28", false, 0);
    }

    @Test
    void decimalWithLargerError() {
        format(45.6, 256.5);
        assertQuantity("50", "5", "260", "26", false, 0);
        format(0.456, 1.567);
        assertQuantity("0.5", "5", "1.6", "16", false, 0);
    }

    @Test
    void largeScientific() {
        format(5.67634e4, 0.001223e4);
        assertQuantity("5.6763", "56763", "0.0012", "12", true, 4);
    }

    @Test
    void negativeScientific() {
        format(-5.67634e4, 0.001223e4);
        assertQuantity("-5.6763", "56763", "0.0012", "12", true, 4);
    }

    @Test
    void smallScientific() {
        format(6.67634e-10, 0.002223e-10);
        assertQuantity("6.6763", "66763", "0.0022", "22", true, -10);
    }

    @Test
    void scientificWithLargerError() {
        format(6.67634e-10, 2.22331123e-6);
        assertQuantity("7", "7", "22000", "22", true, -6);
    }
}