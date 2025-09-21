package infnet.edu.br.DR1AT.controller;

import infnet.edu.br.DR1AT.dto.request.StudentRequestDTO;
import infnet.edu.br.DR1AT.dto.response.StudentResponseDTO;
import infnet.edu.br.DR1AT.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping
    public ResponseEntity<StudentResponseDTO> createStudent(@Valid @RequestBody StudentRequestDTO studentRequest) {
        StudentResponseDTO savedStudent = studentService.save(studentRequest);
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<StudentResponseDTO>> getAllStudents() {
        List<StudentResponseDTO> students = studentService.findAll();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @PostMapping("/{studentId}/courses/{courseId}")
    public ResponseEntity<Void> enrollStudentInCourse(@PathVariable Long studentId, @PathVariable Long courseId) {
        studentService.enrollInCourse(studentId, courseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
