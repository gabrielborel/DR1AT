package infnet.edu.br.DR1AT.exception;

public class DuplicateStudentException extends RuntimeException {

    public DuplicateStudentException(String message) {
        super(message);
    }

    public static DuplicateStudentException forDocument(String document) {
        return new DuplicateStudentException("Student with Document " + document + " already exists");
    }

    public static DuplicateStudentException forEmail(String email) {
        return new DuplicateStudentException("Student with email " + email + " already exists");
    }
}
