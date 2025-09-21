package infnet.edu.br.DR1AT.exception;

public class DuplicateGradeException extends RuntimeException {

    public DuplicateGradeException(String message) {
        super(message);
    }

    public static DuplicateGradeException forStudentAndCourse(Long studentId, Long courseId) {
        return new DuplicateGradeException("Grade already exists for student " + studentId + " and course " + courseId);
    }
}
