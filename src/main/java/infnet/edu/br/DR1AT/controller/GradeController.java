package infnet.edu.br.DR1AT.controller;

import infnet.edu.br.DR1AT.dto.request.GradeRequestDTO;
import infnet.edu.br.DR1AT.dto.response.GradeResponseDTO;
import infnet.edu.br.DR1AT.service.GradeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grades")
@CrossOrigin(origins = "*")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @PostMapping
    public ResponseEntity<GradeResponseDTO> assignGrade(@Valid @RequestBody GradeRequestDTO gradeRequest) {
        GradeResponseDTO savedGrade = gradeService.assignGrade(gradeRequest);
        return new ResponseEntity<>(savedGrade, HttpStatus.CREATED);
    }

    @GetMapping("/course/{courseId}/approved")
    public ResponseEntity<List<GradeResponseDTO>> getApprovedStudentsByCourse(@PathVariable Long courseId) {
        List<GradeResponseDTO> approvedGrades = gradeService.findApprovedStudentsByCourse(courseId);
        return new ResponseEntity<>(approvedGrades, HttpStatus.OK);
    }

    @GetMapping("/course/{courseId}/failed")
    public ResponseEntity<List<GradeResponseDTO>> getFailedStudentsByCourse(@PathVariable Long courseId) {
        List<GradeResponseDTO> failedGrades = gradeService.findFailedStudentsByCourse(courseId);
        return new ResponseEntity<>(failedGrades, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrade(@PathVariable Long id) {
        gradeService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
