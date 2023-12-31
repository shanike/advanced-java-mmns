package question1;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	final static int OPERATION_PLUS = 0;
	final static int OPERATION_MINUS = 1;
	final static int OPERATION_DERIVE = 2;
	final static int OPERATION_TO_STRING = 3;
	final static int OPERATION_EQUALS = 4;
	final static int OPERATION_EXIT = -1;
	final static int[] POLYNOM_OPERATIONS = new int[] { OPERATION_PLUS, OPERATION_MINUS, OPERATION_DERIVE,
			OPERATION_TO_STRING, OPERATION_EQUALS };

	static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		startTwoPolynomOperations();
	}

	private static void startTwoPolynomOperations() {
		// Get two polynoms
		Polynom pol1, pol2;
		try {
			// Get pol1
			System.out.println("First polynom:");
			System.out.println("==============");
			pol1 = getPolynom();

			// Get pol2
			System.out.println("Second polynom:");
			System.out.println("===============");
			pol2 = getPolynom();
		} catch (Exception e) {
			System.out.println("Error creating polynom: " + e);
			return;
		}

		// Run polynom(s) operations by user selection till the user selects to exit
		while (true) {
			// Prompt with operations menu
			System.out.println();
			int operation = selectOperation();
			if (operation == OPERATION_EXIT) {
				break;
			}

			// Run selected polynom operation
			try {
				System.out.println();
				runOperation(operation, pol1, pol2);
			} catch (Exception e) {
				System.out.println("Error in selected operation: " + e);
				break;
			}

		}

		System.out.println("Bye, bye");

		scanner.close();
	}

	/**
	 * Asks user for a polynom
	 * 
	 * @return A Polynom instance, representing the polynom the user inputted
	 * @throws Exception If the generated polynom is invalid
	 */
	private static Polynom getPolynom() throws Exception {
		ArrayList<Double> coefficients = new ArrayList<Double>();
		ArrayList<Integer> exponents = new ArrayList<Integer>();

		String userInput;

		while (true) {
			// Get coefficient (till a valid double or an "Enter")
			System.out.println("Enter a coefficient:");
			System.out.println("(Press \"Enter\" to continue)");
			userInput = scanner.nextLine();

			if (userInput.length() == 0) {
				// User pressed "Enter": done getting polynom
				break;
			}

			try {
				double coefficient = Double.parseDouble(userInput);
				coefficients.add(coefficient);
			} catch (NumberFormatException e) {
				// re-ask user
				continue;
			}

			// Get exponent (till a valid int)
			while (true) {
				try {
					System.out.println("Enter an exponent:");
					userInput = scanner.nextLine();
					int exponent = Integer.parseInt(userInput);
					exponents.add(exponent);
					break;
				} catch (NumberFormatException e) {
					// re-ask user for exponent
					continue;
				}
			}
		}

		return new Polynom(listToDoubleArr(coefficients), listToIntArr(exponents));
	}

	/**
	 * Prompt user will all possible operations
	 * 
	 * @return the operation the user selected
	 */
	private static int selectOperation() {
		System.out.println("Enter \"" + OPERATION_PLUS + "\" for plus, \"" + OPERATION_MINUS + "\" for minus, \""
				+ OPERATION_DERIVE + "\" for derive, \"" + OPERATION_TO_STRING + "\" for toString, \""
				+ OPERATION_EQUALS + "\" for equals.");
		System.out.println("(Any other key to exit)");
		String userInput = scanner.nextLine();
		for (int operation : POLYNOM_OPERATIONS) {
			if (userInput.equals(Integer.toString(operation))) {
				return operation;
			}
		}
		return OPERATION_EXIT;
	}

	/**
	 * Runs the {@code operation} on the given polynoms
	 * 
	 * @throws Exception If operation resulted with an invalid polynom
	 */
	private static void runOperation(int operation, Polynom pol1, Polynom pol2) throws Exception {
		switch (operation) {
		case OPERATION_PLUS:
			Polynom sum = pol1.plus(pol2);
			System.out.println("> PLUS:");
			System.out.println(pol1 + " + " + pol2 + " = " + sum);
			break;
		case OPERATION_MINUS:
			Polynom difference = pol1.minus(pol2);
			System.out.println("> MINUS:");
			System.out.println(pol1 + " - " + pol2 + " = " + difference);
			break;
		case OPERATION_DERIVE:
			System.out.println("> DERIVE First Polynom:");
			System.out.println("(" + pol1 + ")' = " + pol1.derive());
			System.out.println("> DERIVE Second Polynom:");
			System.out.println("(" + pol2 + ")' = " + pol2.derive());
			break;
		case OPERATION_TO_STRING:
			System.out.println("> TO_STRING First Polynom:");
			System.out.println(pol1.toString());
			System.out.println("> TO_STRING Second Polynom:");
			System.out.println(pol2.toString());
			break;
		case OPERATION_EQUALS:
			boolean equals = pol1.equals(pol2);
			System.out.println("> EQUALS:");
			System.out.println(generateEqualsMessage(pol1, pol2, equals));
			break;
		default:
			break;
		}
	}

	/**
	 * @return a generated message for whether polynoms are equal or not.
	 */
	private static String generateEqualsMessage(Polynom pol1, Polynom pol2, boolean equals) {
		String msg1 = pol1.toString() + " ";
		if (!equals) {
			msg1 += "!";
		} else {
			msg1 += "=";
		}
		msg1 += "= " + pol2.toString();

		ArrayList<String> msg2 = new ArrayList<String>();
		msg2.add("The polynoms");
		if (!equals) {
			msg2.add("DON'T");
		}
		msg2.add("equal");
		return msg1 + "\n" + String.join(" ", msg2);
	}

	/**
	 * Converts an {@code ArrayList} of {@code Integer}s to a simpler {@code int}
	 * array.
	 * 
	 * @return the new converted {@code int} array
	 */
	private static int[] listToIntArr(ArrayList<Integer> list) {
		return list.stream().mapToInt(i -> i).toArray();
	}

	/**
	 * Converts an {@code ArrayList} of {@code Double}s to a simpler {@code double}
	 * array.
	 * 
	 * @return the new converted {@code double} array
	 */
	private static double[] listToDoubleArr(ArrayList<Double> list) {
		return list.stream().mapToDouble(i -> i).toArray();
	}

}
