package infnet.edu.br.DR1AT.exception;

public class GradeNotFoundException extends RuntimeException {

    public GradeNotFoundException(String message) {
        super(message);
    }

    public GradeNotFoundException(Long id) {
        super("Grade not found with id: " + id);
    }
}
