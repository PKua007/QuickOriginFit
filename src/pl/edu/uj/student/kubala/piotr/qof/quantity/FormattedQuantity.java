// QuickMean - FormattedMeasure.java
//---------------------------------------------------------------------
// Klasa formatująca pomiar na podstawie jego niepewności.
// Wybiera
//---------------------------------------------------------------------
// Utworzono 18:03 09.06.2017 w IntelliJ IDEA
// (C)PKua, wszystkie prawa zastrzeżone
//---------------------------------------------------------------------

package pl.edu.uj.student.kubala.piotr.qof.quantity;

public class FormattedQuantity implements Cloneable {

    private static final char PLUS_MINUS = '±';
    private static final String PLUS_MINUS_SPACE = " ± ";

    private String value;
    private String valueDigits;
    private String error;
    private String errorDigits;
    private boolean scientificForm;
    private int exponent;

    public FormattedQuantity() {
    }

    public FormattedQuantity(String value, String valueDigits, String error, String errorDigits, boolean scientificForm, int exponent) {
        this.value = value;
        this.valueDigits = valueDigits;
        this.error = error;
        this.errorDigits = errorDigits;
        this.scientificForm = scientificForm;
        this.exponent = exponent;
    }

    public String getValue() {
        return value;
    }

    public String getValueDigits() {
        return valueDigits;
    }

    public String getError() {
        return error;
    }

    public String getErrorDigits() {
        return errorDigits;
    }

    public boolean isScientificForm() {
        return scientificForm;
    }

    public int getExponent() {
        return exponent;
    }

    /**
     * Zwraca reprezentację Stringową postaci (3.06 +- 0.56 +- 0.40) * 10^5
     * @return reprezentacja Stringowa
     */
    @Override
    public String toString()
    {
        String body = value + PLUS_MINUS_SPACE + error;
        if (scientificForm)
            return "(" + body + ") * 10^" + exponent;
        else
            return body;
    }

    /**
     * Zwraca reprezentację Stringową postaci 3.0e5+-0.56e5+-0.40e5
     * @return reprezentacja Stringowa
     */
    public String toComputerString()
    {
        String value = this.value;
        String error = this.error;
        if (scientificForm) {
            value += "e" + exponent;
            error += "e" + exponent;
        }
        return value + "+-" + error;
    }

    @Override
    public Object clone()
    {
        try {
            // Płytka kopia, żadnych referencji do mutualnego typu
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
