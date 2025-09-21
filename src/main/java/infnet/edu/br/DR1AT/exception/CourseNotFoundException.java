package infnet.edu.br.DR1AT.exception;

public class CourseNotFoundException extends RuntimeException {

    public CourseNotFoundException(String message) {
        super(message);
    }

    public CourseNotFoundException(Long id) {
        super("Course not found with id: " + id);
    }
}
