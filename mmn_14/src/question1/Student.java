package question1;

public class Student implements Comparable<Student> {
	private String firstName;
	private String lastName;
	private String identityNumber;
	private String yearOfBirth;

	public Student(String firstName, String lastName, String identityNumber, String yearOfBirth) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.identityNumber = identityNumber;
		this.yearOfBirth = yearOfBirth;
	}

	@Override
	public String toString() {
		return "{\n\tfirstName: " + firstName + ",\n\tlastName: " + lastName + ",\n\tidentityNumber: " + identityNumber
				+ ",\n\tyearOfBirth: " + yearOfBirth + ",\n}";
	}

	@Override
	public int compareTo(Student s) {
		return s.identityNumber.compareTo(this.identityNumber);
	}

}
