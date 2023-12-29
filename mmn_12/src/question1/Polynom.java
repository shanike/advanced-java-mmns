package question1;

import java.util.ArrayList;
import java.util.Collections;

public class Polynom {

    private ArrayList<Monom> monoms;

    private static final String BAD_REQUEST_MESSAGE = "BAD_REQUEST";

    /**
     * Creates a new Polynom instance.
     * 
     * If exponents have duplicate values: will add the coefficients to a single
     * exponent
     * 
     * @param coefficients
     * @param exponents
     * @throws Exception if coefficient or exponents array are invalid:
     *                   1) If the arrays are not the same length.
     *                   2) If there's a non-natural integer in {@code exponents}
     */
    public Polynom(double[] coefficients, int[] exponents) throws Exception {
        if (coefficients.length != exponents.length) {
            throw new Exception(BAD_REQUEST_MESSAGE);
        }

        for (int exponent : exponents) {
            if (exponent < 0) {
                throw new Exception(BAD_REQUEST_MESSAGE);
            }
        }

        Polynom polynom = new Polynom();
        int polynomLength = coefficients.length;
        for (int i = 0; i < polynomLength; i++) {
            double coefficient = coefficients[i];
            int exponent = exponents[i];
            Monom monom = new Monom(coefficient, exponent);
            polynom.addMonomToPolynom(monom);
        }
        Collections.sort(polynom.monoms);
        this.monoms = polynom.monoms;
    }

    /**
     * Create an empty Polynom.
     */
    private Polynom() {
        this.monoms = new ArrayList<Monom>();
    }

    /**
     * @param otherPolynom to add to {@code this} polynom
     * @return a new polynom representing the result of adding {@code otherPolynom}
     *         to {@code this} polynom
     * @throws Exception if the generated polynom is invalid.
     * @note - another way is to build arrays of exponents and coefficients. But
     *       that seems unstable because the two arrays need to be strongly related
     */
    public Polynom plus(Polynom otherPolynom) throws Exception {

        Polynom summedPolynom = new Polynom(new double[] {}, new int[] {});

        int maxSize = Math.max(this.monoms.size(), otherPolynom.monoms.size());

        for (int i = 0; i < maxSize; i++) {
            if (i < this.monoms.size()) {
                summedPolynom.addMonomToPolynom(this.monoms.get(i));
            }
            if (i < otherPolynom.monoms.size()) {
                summedPolynom.addMonomToPolynom(otherPolynom.monoms.get(i));
            }
        }
        Collections.sort(summedPolynom.monoms);
        return summedPolynom;
    }

    /**
     * @param otherPolynom to subtract from {@code this} polynom
     * @return a new polynom representing the result of subtracting
     *         {@code otherPolynom} from {@code this} polynom
     * @throws Exception if the generated polynom is invalid.
     */
    public Polynom minus(Polynom otherPolynom) throws Exception {
        Polynom oppositeOtherPolynom = new Polynom();
        for (Monom monom : otherPolynom.monoms) {
            Monom negativeMonom = new Monom(-1 * monom.coefficient, monom.exponent);
            oppositeOtherPolynom.addMonomToPolynom(negativeMonom);
        }
        return this.plus(oppositeOtherPolynom);
    }

    /**
     * @return a new polynom representing the derivative of {@code this} Polynom
     */
    public Polynom derive() {
        Polynom derivedPolynom = new Polynom();
        for (Monom monom : this.monoms) {
            if (monom.exponent <= 0) {
                // If exponent is 0: the derived polynom will not have this coefficient
                continue;
            }
            Monom derivedMonom = new Monom(monom.exponent * monom.coefficient, monom.exponent - 1);
            derivedPolynom.addMonomToPolynom(derivedMonom);
        }
        // * If {@code this.monoms} is ordered by exponent, then also derivedPolynom is.
        return derivedPolynom;
    }

    /**
     * @return whether or not the monoms of the other {@code obj}
     *         ({@code instanceof Polynom}) are equal to this polynom's monoms
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof Polynom)) {
            return false;
        }

        Polynom otherPolynom = (Polynom) obj;

        if (otherPolynom.monoms.size() != this.monoms.size()) {
            return false;
        }
        for (int i = 0; i < this.monoms.size(); i++) {
            if (this.monoms.get(i).coefficient != otherPolynom.monoms.get(i).coefficient) {
                return false;
            }
            if (this.monoms.get(i).exponent != otherPolynom.monoms.get(i).exponent) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        if (monoms.size() <= 0) {
            return "Empty polynom";
        }

        String finalStr = "";
        for (int i = 0; i < monoms.size(); i++) {
            Monom monom = monoms.get(i);

            if (i != 0 && monom.coefficient >= 0) {
                /*
                 * The coefficient is a positive number in the middle of the polynom:
                 * render a preceding "+".
                 */
                finalStr += "+";
            }

            if (Math.abs(monom.coefficient) != 1 || monom.exponent == 0) {
                /*
                 * The coefficient is **not** 1 and **not** -1,
                 * OR it **is** 1 or -1 **but** the exponent is 0:
                 * render the coefficient.
                 */
                finalStr += monom.coefficient;
            } else if (monom.coefficient == -1) {
                /**
                 * The coefficient is -1, (and the exponent is **not** 0) and even though it's
                 * negative:
                 * render the "-" because we don't render the coefficient itself when it's -1
                 */
                finalStr += "-";
            }

            // * SUM: we set the coefficient

            if (monom.exponent != 0 && monom.coefficient != 0) {
                /*
                 * The exponent is **not** 0:
                 * render the "x" (/and the exponent/).
                 */
                finalStr += "x";

                if (monom.exponent != 1) {
                    /*
                     * If the exponent is **not** 1:
                     * render the exponent.
                     */
                    finalStr += "^" + monom.exponent;
                }
            }

        }
        return finalStr;
    }

    /**
     * A private method for adding a new Monom to {@code this} polynom
     */
    private void addMonomToPolynom(Monom monomToAdd) {
        boolean doesExponentExists = false;
        for (Monom monom : this.monoms) {
            if (monom.exponent == monomToAdd.exponent) {
                // Exponent exists in this polynom
                doesExponentExists = true;
                monom.addToCoefficient(monomToAdd.coefficient);
                break;
            }
        }
        if (!doesExponentExists) {
            // This polynom doesn't have this exponent yet
            Monom copiedMonom = new Monom(monomToAdd.coefficient, monomToAdd.exponent);
            this.monoms.add(copiedMonom);
        }
    }

}
