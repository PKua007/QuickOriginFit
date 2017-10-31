// QuickMean - FormattedMeasureFactory.java
//---------------------------------------------------------------------
// Fabryka sformatowanych pomiarów na podstawie ustalonego wzorca.
//---------------------------------------------------------------------
// Utworzono 18:22 09.06.2017 w IntelliJ IDEA
// (C)PKua, wszystkie prawa zastrzeżone
//---------------------------------------------------------------------

package pl.edu.uj.student.kubala.piotr.qof.quantity;

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
        int valueFirstDigitPos = getSignifOrZero(quantity.getValue());
        int errFirstDigitPos = getSignifOrIntMin(quantity.getError());

        if (valueFirstDigitPos > maxFixedExponent || valueFirstDigitPos < minFixedExponent)
            return formatScientific(quantity, valueFirstDigitPos, errFirstDigitPos);// Przedstaw w postaci wykładniczej
        else
            return formatDecimal(quantity, valueFirstDigitPos, errFirstDigitPos);// Przedstaw w postaci "tradycyjnej"
    }

    /* Pomocnicza metoda formatująca wielkość jako liczbę dziesiętną */
    private FormattedQuantity formatDecimal(Quantity quantity, int valueFirstDigitPos, int errorFirstDigitPos) {

        String value = "";
        String valueDigits;
        String error;
        String errorDigits;

        if (quantity.getError() > 0) {      // Którykolwiek z błedów niezerowy
            // Pozycja ostatniej niezerowej cyfry - normalnie ostatnia cyfra niepewności, chyba że niepewność
            // większa niż wynik, to pierwsa cyfra wyniku
            int lastValueDigitPos = calculateLastValueDigitPos(valueFirstDigitPos, errorFirstDigitPos);
            int lastErrorDigitPos = calculateLastErrorDigitPos(errorFirstDigitPos);
            valueDigits = generateDigits(quantity.getValue(), lastValueDigitPos);
            errorDigits = generateDigits(quantity.getError(), lastErrorDigitPos);

            if (lastValueDigitPos < 0) {  // Występuje część ułamkowa, bądź kończy się na jednościach
                value = String.format(ENGLISH_LOCALE, "%." + (-lastValueDigitPos) + "f", quantity.getValue());
                error = String.format(ENGLISH_LOCALE, "%." + (-lastErrorDigitPos) + "f", quantity.getError());
            } else {    // Ostatnia cyfra to cyfra dziesiątek, setek, itd. Trzeba dopisać ileś zer
                if (quantity.getValue() < 0)
                    value = "-";
                value += valueDigits + generateZeros(lastValueDigitPos);
                error = errorDigits + generateZeros(lastErrorDigitPos);
            }
        } else {        // Oba błedy zerowe - walnij maksymalną dokładność
            value = Double.toString(quantity.getValue());
            valueDigits = stripDigits(value);
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

        if (quantity.getError() > 0) {      // Którykolwiek z błedów niezerowy
            int lastValueDigitPos = calculateLastValueDigitPos(valueFirstDigitPos, errorFirstDigitPos);
            int lastErrorDigitPos = calculateLastErrorDigitPos(errorFirstDigitPos);
            valueDigits = generateDigits(quantity.getValue(), lastValueDigitPos);
            errorDigits = generateDigits(quantity.getError(), lastErrorDigitPos);
            // Liczba cyfr po przecinku do wyświetlenia - taka, żeby była odpowiednia ilość cyfr znaczących większego
            // błędu
            int fractionDigits = calculateFractionDigits(valueFirstDigitPos, errorFirstDigitPos);
            if (fractionDigits >= 0) {
                double factor = Math.pow(10, -exponent);
                value = String.format(ENGLISH_LOCALE, "%." + fractionDigits + "f", quantity.getValue() * factor);
                error = String.format(ENGLISH_LOCALE, "%." + fractionDigits + "f", quantity.getError() * factor);
            } else {
                value = valueDigits;
                error = errorDigits + generateZeros(-fractionDigits);
            }
        } else {        // Oba błedy zerowe - walnij maksymalna dokładność
            value = Double.toString(quantity.getValue() * Math.pow(10, -exponent));
            valueDigits = stripDigits(value);
            error = "0";
            errorDigits = generateZeros(errorSignificantDigits);
        }

        return new FormattedQuantity(value, valueDigits, error, errorDigits, true, exponent);
    }

    private String stripDigits(String value) {
        int eIdx = value.indexOf('e');
        if (eIdx == -1)
            eIdx = value.indexOf('E');
        if (eIdx != -1)
            value = value.substring(0, eIdx);
        value = value.replace(".", "");
        if (value.startsWith("-"))
            value = value.substring(1);
        return value;
    }

    private int calculateLastValueDigitPos(int valueFirstDigitPos, int errorFirstDigitPos) {
        return Math.min(valueFirstDigitPos, calculateLastErrorDigitPos(errorFirstDigitPos));
    }

    private int calculateLastErrorDigitPos(int errorFirstDigitPos) {
        return errorFirstDigitPos - this.errorSignificantDigits + 1;
    }

    private int calculateFractionDigits(int valueFirstDigitPos, int errorFirstDigitPos) {
        return valueFirstDigitPos - errorFirstDigitPos + this.errorSignificantDigits - 1;
    }

    private String generateDigits(double value, int lastDigitPos) {
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