package question1;

public class Monom implements Comparable<Object> {
    double coefficient;
    int exponent;

    public Monom(double coefficient, int exponent) {
        this.coefficient = coefficient;
        this.exponent = exponent;
    }

    public void addToCoefficient(double valueToAdd) {
        this.coefficient += valueToAdd;
    }

    public void subtractToCoefficient(double valueToSubtract) {
        this.coefficient -= valueToSubtract;
    }

    /**
     * A function for {@code Collections} to call when {@code sort}ing an
     * {@code ArrayList} of {@code Monom}s
     */
    public int compareTo(Object o) {
        if (!(o instanceof Monom)) {
            // Shouldn't happen. But in case: send it to the end of the list.
            return -1;
        }
        Monom otherMonom = (Monom) o;
        return otherMonom.exponent - this.exponent;
    }

    public String toString() {
        return coefficient + "x^" + exponent;
    }
}
