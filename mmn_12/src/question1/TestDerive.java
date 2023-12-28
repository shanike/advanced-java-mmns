package question1;

import java.util.ArrayList;

public class TestDerive {
    private ArrayList<ArrayList<Polynom>> tests = new ArrayList<ArrayList<Polynom>>();

    public void test() {
        try {
            createTest1();
            createTest1_2();
            createTest2();
            for (ArrayList<Polynom> test : tests) {
                Polynom derived = test.get(0).derive();
                System.out.println("Pol: " + test.get(0));
                System.out.println("DERIVED:  " + derived);
                System.out.println("EXPECTED: " + test.get(1));
                System.out.println("ARE EQUAL? " + test.get(1).toString().equals(derived.toString()));
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
            test.add(new Polynom(new double[] { 1, 2 }, new int[] { 0, 2 }));
        } catch (Exception e) {
            throw new Exception("ERROR creating test1: " + e);
        }
        tests.add(test);
    }

    private void createTest1_2() throws Exception {
        ArrayList<Polynom> test = new ArrayList<Polynom>(3);
        try {
            test.add(new Polynom(new double[] { 10, 2, 3 }, new int[] { 1, 3, 0 }));
            test.add(new Polynom(new double[] { 10, 6 }, new int[] { 0, 2 }));
        } catch (Exception e) {
            throw new Exception("ERROR creating test1: " + e);
        }
        tests.add(test);
    }

    private void createTest2() throws Exception {
        ArrayList<Polynom> test = new ArrayList<Polynom>(3);
        try {
            test.add(new Polynom(new double[] { -3, 8, -1, 7 }, new int[] { 2, 3, 1, 8 }));
            test.add(new Polynom(new double[] { -6, 24, -1, 56 }, new int[] { 1, 2, 0, 7 }));
        } catch (Exception e) {
            throw new Exception("ERROR creating test2: " + e);
        }
        tests.add(test);
    }
}
