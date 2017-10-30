// QuickMean - FormattedMeasureFactory.java
//---------------------------------------------------------------------
// Fabryka sformatowanych pomiarów na podstawie ustalonego wzorca.
//---------------------------------------------------------------------
// Utworzono 18:22 09.06.2017 w IntelliJ IDEA
// (C)PKua, wszystkie prawa zastrzeżone
//---------------------------------------------------------------------

package pl.edu.uj.student.kubala.piotr.qof.formatting;

import java.util.Locale;

public class FormattedQuantityFactory {
    private static final int DEFAULT_MIN_FIXED_EXPONENT = -3;
    private static final int DEFAULT_MAX_FIXED_EXPONENT = 3;
    private static final int DEFAULT_ERROR_SIGNIFICANT_DIGITS = 2;
    private static final Locale ENGLISH_LOCALE = Locale.ENGLISH;

    private int minFixedExponent;       // Minimalny wykładnik, dla której zostaje postać zwykła
    private int maxFixedExponent;       // Maksymalny wykładnik, dla którego zostaje postać zwykła
    private int errorSignificantDigits; // Ilość cyfr znaczących w błędzie

    /**
     * Konstruktor inicjujący domyślne wartości
     */
    public FormattedQuantityFactory() {
        this(DEFAULT_MIN_FIXED_EXPONENT, DEFAULT_MAX_FIXED_EXPONENT, DEFAULT_ERROR_SIGNIFICANT_DIGITS, false);
    }

    /**
     * Konstruktor specyfikujący, czy rozdzielać niepewności
     * @param separateErrors czy rozdzielać neipewności
     */
    public FormattedQuantityFactory(boolean separateErrors) {
        this(DEFAULT_MIN_FIXED_EXPONENT, DEFAULT_MAX_FIXED_EXPONENT, DEFAULT_ERROR_SIGNIFICANT_DIGITS, separateErrors);
    }

    /**
     * Konstruktor przyjmujący wszystkie parametry
     *
     * @param minFixedExponent       minimalny wykładnik, dla której zostaje postać zwykła
     * @param maxFixedExponent       maksymalny wykładnik, dla którego zostaje postać zwykła
     * @param errorSignificantDigits ilość cyfr znaczących w błędzie
     * @param separateErrors         czy rozdzielać niepewności
     */
    public FormattedQuantityFactory(int minFixedExponent, int maxFixedExponent, int errorSignificantDigits, boolean separateErrors) {
        this.minFixedExponent = minFixedExponent;
        this.maxFixedExponent = maxFixedExponent;
        this.errorSignificantDigits = errorSignificantDigits;
    }

    /* Gettery i settery */

    public int getMinFixedExponent() {
        return minFixedExponent;
    }

    public void setMinFixedExponent(int minFixedExponent) {
        this.minFixedExponent = minFixedExponent;
    }

    public int getMaxFixedExponent() {
        return maxFixedExponent;
    }

    public void setMaxFixedExponent(int maxFixedExponent) {
        this.maxFixedExponent = maxFixedExponent;
    }

    public int getErrorSignificantDigits() {
        return errorSignificantDigits;
    }

    public void setErrorSignificantDigits(int errorSignificantDigits) {
        this.errorSignificantDigits = errorSignificantDigits;
    }

    /**
     * Formatuje pomiar na podstawie ustalonych parametrów.
     *
     * @param quantity wielkość do sformatowania
     * @return sformatowany pomiar
     */
    public FormattedQuantity format(Quantity quantity) {
        FormattedQuantity result;
        int valueFirstDigitPos = getSignifOrZero(quantity.getValue());
        int errFirstDigitPos = getSignifOrIntMin(quantity.getError());

        if (valueFirstDigitPos > maxFixedExponent || valueFirstDigitPos < minFixedExponent)
            result = formatScientific(quantity, valueFirstDigitPos, errFirstDigitPos);  // Przedstaw w postaci wykładniczej
        else
            result = formatDecimal(quantity, valueFirstDigitPos, errFirstDigitPos);     // Przedstaw w postaci "tradycyjnej"

        return result;
    }

    /* Pomocnicza metoda formatująca wielkość jako liczbę dziesiętną */
    private FormattedQuantity formatDecimal(Quantity quantity, int valueFirstDigitPos, int errorFirstDigitPos) {

        String value = "";
        String valueDigits;
        String error;
        String errorDigits;

        if (errorFirstDigitPos > Integer.MIN_VALUE) {      // Którykolwiek z błedów niezerowy
            // Pozycja ostatniej niezerowej cyfry - normalnie ostatnia cyfra niepewności, chyba że niepewność
            // większa niż wynik, to pierwsa cyfra wyniku
            int lastDigitPos = calculateLastDigitPos(valueFirstDigitPos, errorFirstDigitPos);
            valueDigits = generateDigits(lastDigitPos, quantity.getValue());
            errorDigits = generateDigits(lastDigitPos, quantity.getError());

            if (lastDigitPos < 0) {  // Występuje część ułamkowa, bądź kończy się na jednościach
                value = String.format(ENGLISH_LOCALE, "%." + (-lastDigitPos) + "f", quantity.getValue());
                error = String.format(ENGLISH_LOCALE, "%." + (-lastDigitPos) + "f", quantity.getError());
            } else {    // Ostatnia cyfra to cyfra dziesiątek, setek, itd. Trzeba dopisać ileś zer
                String trailingZeros = generateZeros(lastDigitPos);
                if (quantity.getValue() < 0)
                    value = "-";
                value += valueDigits + trailingZeros;
                error = errorDigits + trailingZeros;
            }
        } else {        // Oba błedy zerowe - walnij maksymalną dokładność
            value = Double.toString(quantity.getValue());
            valueDigits = "foo";
            error = "0";
            errorDigits = generateZeros(errorSignificantDigits);
        }

        return new FormattedQuantity(value, valueDigits, error, errorDigits, false, 0);
    }

    /* Pomocnicza metoda formatująca wielkość jako liczbę w postaci naukowej (wykładniczej) */
    private FormattedQuantity formatScientific(Quantity quantity, int valueFirstDigitPos, int errorFirstDigitPos) {
        String value;
        String valueDigits;
        String error;
        String errorDigits;
        int exponent = valueFirstDigitPos;

        if (errorFirstDigitPos > Integer.MIN_VALUE) {      // Którykolwiek z błedów niezerowy
            int lastDigitPos = calculateLastDigitPos(valueFirstDigitPos, errorFirstDigitPos);
            valueDigits = generateDigits(lastDigitPos, quantity.getValue());
            errorDigits = generateDigits(lastDigitPos, quantity.getError());
            // Liczba cyfr po przecinku do wyświetlenia - taka, żeby była odpowiednia ilość cyfr znaczących większego
            // błędu
            int fractionDigits = calculateFractionDigits(valueFirstDigitPos, errorFirstDigitPos);
            double factor = Math.pow(10, -exponent);

            value = String.format(ENGLISH_LOCALE, "%." + fractionDigits + "f", quantity.getValue() * factor);
            error = String.format(ENGLISH_LOCALE, "%." + fractionDigits + "f", quantity.getError() * factor);
        } else {        // Oba błedy zerowe - walnij maksymalna dokładność
            value = Double.toString(quantity.getValue() * Math.pow(10, -exponent));
            valueDigits = "foo";
            error = "0";
            errorDigits = generateZeros(errorSignificantDigits);
        }

        return new FormattedQuantity(value, valueDigits, error, errorDigits, true, exponent);
    }

    private int calculateLastDigitPos(int valueFirstDigitPos, int errorFirstDigitPos) {
        return Math.min(valueFirstDigitPos, errorFirstDigitPos - this.errorSignificantDigits + 1);
    }

    private int calculateFractionDigits(int valueFirstDigitPos, int errorFirstDigitPos) {
        return Math.max(0, valueFirstDigitPos - errorFirstDigitPos + this.errorSignificantDigits - 1);
    }

    private String generateDigits(int lastDigitPos, double value) {
        return String.format(ENGLISH_LOCALE, "%.0f", Math.abs(value) * Math.pow(10, -lastDigitPos));
    }

    private String generateZeros(int numberOfZeros) {
        return new String(new char[numberOfZeros]).replace('\0', '0');
    }

    /* Zwraca pozycję pierwszej znaczącej cyfry, lub zero, jeśli wartość to 0 */
    private int getSignifOrZero(double value) {
        if (value == 0)
            return 0;
        else
            return (int) Math.floor(Math.log10(Math.abs(value)));
    }

    /* Zwraca pozycję pierwszej znaczącej cyfry, lub minimalnego inta, jeśli wartość to 0 */
    private int getSignifOrIntMin(double value) {
        if (value == 0)
            return Integer.MIN_VALUE;
        else
            return (int) Math.floor(Math.log10(Math.abs(value)));
    }
}