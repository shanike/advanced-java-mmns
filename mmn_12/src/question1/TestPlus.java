package question1;

import java.util.ArrayList;

public class TestPlus {

    private ArrayList<ArrayList<Polynom>> tests = new ArrayList<ArrayList<Polynom>>();

    public void test() {
        try {
            createTest1();
            createTest1_2();
            createTest2();
            for (ArrayList<Polynom> test : tests) {
                Polynom summed = test.get(0).plus(test.get(1));
                System.out.println("Pol 1: " + test.get(0));
                System.out.println("Pol 2: " + test.get(1));
                System.out.println("SUMMED: " + summed);
                System.out.println("EXPECTED: " + test.get(2));
                System.out.println("ARE EQUAL? " + test.get(2).toString().equals(summed.toString()));
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println("ERROR running test: " + e);
        }
    }

    private void createTest1() throws Exception {
        ArrayList<Polynom> test = new ArrayList<Polynom>(3);
        try {
            test.add(new Polynom(new double[] { 1, 1, 1 }, new int[] { 1, 3, 0 }));
            test.add(new Polynom(new double[] { 1, -1, 0, 2 }, new int[] { 1, 3, 0, 2 }));
            test.add(new Polynom(new double[] { 2, 0, 1, 2 }, new int[] { 1, 3, 0, 2 }));
        } catch (Exception e) {
            throw new Exception("ERROR creating test1: " + e);
        }
        tests.add(test);
    }

    private void createTest1_2() throws Exception {
        ArrayList<Polynom> test = new ArrayList<Polynom>(3);
        try {
            test.add(new Polynom(new double[] { 1, 1, 1 }, new int[] { 1, 3, 0 }));
            test.add(new Polynom(new double[] { -1, 0, 2 }, new int[] { 3, 0, 2 }));
            test.add(new Polynom(new double[] { 1, 0, 1, 2 }, new int[] { 1, 3, 0, 2 }));
        } catch (Exception e) {
            throw new Exception("ERROR creating test1: " + e);
        }
        tests.add(test);
    }

    private void createTest2() throws Exception {
        ArrayList<Polynom> test = new ArrayList<Polynom>(3);
        try {
            test.add(new Polynom(new double[] { -3, 8, -1, 7 }, new int[] { 2, 3, 1, 0 }));
            test.add(new Polynom(new double[] { 1, -1, 0, -6, 4 }, new int[] { 2, 3, 1, 0, 9 }));
            test.add(new Polynom(new double[] { -2, 7, -1, 1, 4 }, new int[] { 2, 3, 1, 0, 9 }));
        } catch (Exception e) {
            throw new Exception("ERROR creating test2: " + e);
        }
        tests.add(test);
    }

}
