package infnet.edu.br.DR1AT.controller;

import infnet.edu.br.DR1AT.dto.request.CourseRequestDTO;
import infnet.edu.br.DR1AT.dto.response.CourseResponseDTO;
import infnet.edu.br.DR1AT.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "*")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping
    public ResponseEntity<CourseResponseDTO> createCourse(@Valid @RequestBody CourseRequestDTO courseRequest) {
        CourseResponseDTO savedCourse = courseService.save(courseRequest);
        return new ResponseEntity<>(savedCourse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CourseResponseDTO>> getAllCourses() {
        List<CourseResponseDTO> courses = courseService.findAll();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
