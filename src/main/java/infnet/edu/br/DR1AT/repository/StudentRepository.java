package infnet.edu.br.DR1AT.repository;

import infnet.edu.br.DR1AT.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByDocument(String document);
    Optional<Student> findByEmail(String email);
}
