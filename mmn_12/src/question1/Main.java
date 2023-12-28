package question1;

public class Main {

    public static void main(String[] args) {
        try {
            Polynom pol = new Polynom(new double[] { -6, 24, -1, 56 }, new int[] { 1, 2, 0, 7 });
            System.out.println(pol);
        } catch (Exception e) {
            System.out.println("ERROR: " + e);
        }

    }

}
