package infnet.edu.br.DR1AT.exception;

public class DuplicateCourseException extends RuntimeException {

    public DuplicateCourseException(String message) {
        super(message);
    }

    public static DuplicateCourseException forCode(String code) {
        return new DuplicateCourseException("Course with code " + code + " already exists");
    }
}
