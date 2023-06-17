/*
Robert Sherwood
rsherwo1@umbc.edu
IS247
*/

import java.util.*;

/**
 * Interface to calculate grades
 */
interface GradeCalculator {
    /**
     * Calculates grade
     * @return the grade
     */
    double calculateGrade();
}

/**
 * Abstract class that implements GradeCalculator
 */
abstract class Course implements GradeCalculator {
    private final String name;
    private double finalProjectGrade;

    /**
     * Course name constructor
     * @param name of the course
     */

    public Course(String name) {
        this.name = name;
    }

    /**
     * Set Project Grade
     * @param grade of final project
     */
    public void setFinalProjectGrade(double grade) {
        finalProjectGrade = grade;
    }

    /**
     * retrieves project grade
     * @return project grade
     */
    protected double getFinalProjectGrade() {
        return finalProjectGrade;
    }

    /**
     * calculate class grade
     * @return grade
     */
    public abstract double calculateGrade();

    public String toString() {
        return "Course: " + name + "\n" +
                "Final Project Grade: " + finalProjectGrade + "%";
    }
}

/**
 * Class to create generic course that extends the course class
 */
class GenericCourse extends Course {

    private final double[] examGrades;
    //labs and homeworks grouped together as they are weighted the same
    private final double[] labGrades;
    private final double[] quizGrades;
    private List<GradeRange> gradeRanges;

    /**
     * Constructs GenericCourse
     * @param name of course
     */

    public GenericCourse(String name) {
        super(name);
        examGrades = new double[3];
        labGrades = new double[10];
        quizGrades = new double[10];
        initializeGradeRanges();
    }

    /**
     * Sets exam grade
     * @param index of exam grade
     * @param grade of exam
     */
    public void setExamGrade(int index, double grade) {
        examGrades[index] = grade;
    }

    /**
     * sets lab/HW grade
     * @param index lab/HW grade
     * @param grade of lab/HW
     */
    public void setLabGrade(int index, double grade) {
        labGrades[index] = grade;
    }

    /**
     * sets quiz grade
     * @param index quiz grade
     * @param grade of quiz
     */
    public void setQuizGrade(int index, double grade) {
        quizGrades[index] = grade;
    }

    /**
     * Calculates the final grade
     * @return the final grade
     */
    public double calculateGrade() {
        double examAverage = calculateAverage(examGrades);
        double labAverage = calculateAverage(labGrades);
        double quizAverage = calculateAverage(quizGrades);

        double finalGrade = getFinalProjectGrade() * 0.1 + examAverage * 0.3 + labAverage * 0.3 + quizAverage * 0.3;
        String letterGrade = findLetterGrade(finalGrade);

        System.out.println("Final Grade: " + String.format("%.2f", finalGrade) + "%");
        System.out.println("Letter Grade: " + letterGrade);

        return finalGrade;
    }

    /**
     * Calculates the grade average for an array of grades
     * @param grades from the array
     * @return the grade average
     */
    private double calculateAverage(double[] grades) {
        double sum = 0;
        for (double grade : grades) {
            sum += grade;
        }
        return sum / grades.length;
    }

    /**
     * Initialize the grade range
     */
    private void initializeGradeRanges() {
        gradeRanges = new ArrayList<>();
        gradeRanges.add(new GradeRange(90, 100, "A"));
        gradeRanges.add(new GradeRange(80, 89.9, "B"));
        gradeRanges.add(new GradeRange(70, 79.9, "C"));
        gradeRanges.add(new GradeRange(60, 69.9, "D"));
        gradeRanges.add(new GradeRange(0, 59.9, "F"));
    }

    /**
     * Correlate Letter grade to grade average
     * @param grade the grade
     * @return the letter grade
     */
    private String findLetterGrade(double grade) {
        for (GradeRange gradeRange : gradeRanges) {
            if (grade >= gradeRange.getMinPercentage() && grade <= gradeRange.getMaxPercentage()) {
                return gradeRange.getLetterGrade();
            }
        }
        return "N/A";
    }

    /**
     * returns string of GenericCourse
     * @return string
     */
    public String toString() {
        double examAverage = calculateAverage(examGrades);
        //String examAverage = String.format("%.2f",calculateAverage(examGrades));
        double labAverage = calculateAverage(labGrades);
        double quizAverage = calculateAverage(quizGrades);

        return super.toString() + "\n" +
                "Exam Average: " + String.format("%.2f",examAverage) + "%\n" +
                "Lab Average: " + String.format("%.2f",labAverage) + "%\n" +
                "Quiz Average: " + String.format("%.2f",quizAverage) + "%";
    }
}

/**
 * GradeRange class
 */
class GradeRange {
    private final double minPercentage;
    private final double maxPercentage;
    private final String letterGrade;

    /**
     * Coonstruct GradeRange object
     * @param minPercentage min %
     * @param maxPercentage max %
     * @param letterGrade latter grade
     */
    public GradeRange(double minPercentage, double maxPercentage, String letterGrade) {
        this.minPercentage = minPercentage;
        this.maxPercentage = maxPercentage;
        this.letterGrade = letterGrade;
    }

    /**
     * retrieves minimum percentage
     * @return minimum
     */
    public double getMinPercentage() {
        return minPercentage;
    }
    /**
     * retrieves max percentage
     * @return max
     */
    public double getMaxPercentage() {
        return maxPercentage;
    }
    /**
     * retrieves Letter grade
     * @return letter grade
     */
    public String getLetterGrade() {
        return letterGrade;
    }
}

/**
 * GradeTracker Class
 * main program operations
 */
class GradeTracker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("NOTE: Homeworks and Labs named the same.");
            System.out.println("The 10 Labs are made of 5 HW's and 5 Labs");
            System.out.print("Enter course name: ");
            String courseName = scanner.nextLine();

            GenericCourse course = new GenericCourse(courseName);

            System.out.print("Final Project Grade: ");
            double finalProjectGrade = scanner.nextDouble();
            course.setFinalProjectGrade(finalProjectGrade);

            for (int i = 0; i < 3; i++) {
                System.out.print("Grade for Exam " + (i + 1) + ": ");
                double examGrade = scanner.nextDouble();
                course.setExamGrade(i, examGrade);
            }

            for (int i = 0; i < 10; i++) {
                System.out.print("Grade for Lab " + (i + 1) + ": ");
                double labGrade = scanner.nextDouble();
                course.setLabGrade(i, labGrade);
            }

            for (int i = 0; i < 10; i++) {
                System.out.print("Grade for Quiz " + (i + 1) + ": ");
                double quizGrade = scanner.nextDouble();
                course.setQuizGrade(i, quizGrade);
            }

            System.out.println("\n" + course);
            course.calculateGrade();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter only number.");
        } finally {
            scanner.close();
        }
    }
}