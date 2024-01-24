package question1;

import java.util.Iterator;

public class StudentsList {
	public static void main(String[] args) {
		// Create three students
		Student s1 = new Student("Harry", "Potter", "123", "2021");
		Student s2 = new Student("John", "Doe", "789", "2001");
		Student s3 = new Student("Foo", "Bar", "456", "2000");

		// Create a students list of above students
		Integer[] studentsAverageGrades = new Integer[] { 90, 80, 88 };
		Student[] students = new Student[] { s1, s2, s3 };
		AssociationTable<Student, Integer> studentsList;
		try {
			studentsList = new AssociationTable<Student, Integer>(students, studentsAverageGrades);
		} catch (IllegalArgumentException e) {
			System.err.println("ERROR creating students list: " + e.getMessage());
			return;
		}

		// Add a new student
		Student newStudent = new Student("Sam", "Sam", "111", "2010");
		studentsList.add(newStudent, 100);

		// Update average grade of student
		studentsList.add(newStudent, 99);

		// Delete an existing student
		studentsList.remove(s2);

		// Log students list using the studenst list key iterator
		Iterator<Student> studentsIterator = studentsList.keyIterator();
		String studentsListString = "{\n";
		while (studentsIterator.hasNext()) {
			Student student = studentsIterator.next();
			Object value = studentsList.get(student);
			studentsListString += "\t" + student + ": " + value + ",\n";
		}
		studentsListString += "}";
		System.out.println(studentsListString);
	}
}
