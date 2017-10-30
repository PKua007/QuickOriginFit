// QuickMean - Quantity.java
//---------------------------------------------------------------------
// Klasa reprezentująca wielkość fizyczną, wraz z jej niepewnościami.
// Niemutualna
//---------------------------------------------------------------------
// Utworzono 12:31 11.06.2017 w IntelliJ IDEA
// (C)PKua, wszystkie prawa zastrzeżone
//---------------------------------------------------------------------

package pl.edu.uj.student.kubala.piotr.qof.quantity;

import java.io.Serializable;
import java.util.Objects;

public class Quantity implements Serializable
{
    private double value;
    private double error;

    public Quantity()
    {
        this(0, 0);
    }

    public Quantity(double value, double error) {
        if (!Double.isFinite(value))
            throw new IllegalArgumentException("value not finite: " + value);
        if (!Double.isFinite(error) || error < 0)
            throw new IllegalArgumentException("error not finite: " + error);

        this.value = value;
        this.error = error;
    }

    public double getValue() {
        return value;
    }

    public double getError() {
        return error;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quantity quantity = (Quantity) o;
        return Double.compare(quantity.value, value) == 0 &&
                Double.compare(quantity.error, error) == 0;
    }

    @Override
    public int hashCode() {

        return Objects.hash(value, error);
    }
}
