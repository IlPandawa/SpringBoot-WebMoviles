package com.example.demo.controllers;

import com.example.demo.model.Students;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.repository.StudentsRepository;
import org.springframework.http.ResponseEntity;
import com.example.demo.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api")
public class StudentsController {
    @Autowired
    private StudentsRepository studentsRepository;

    // Get all students
    @GetMapping("/students")
    public List<Students> getStudents() {
        return studentsRepository.findAll();
    }

    // Create a new student
    @PostMapping("/students")
    public Students createStudent(@RequestBody Students student) {
        return studentsRepository.save(student);
    }

    // Get a student by id
    @GetMapping("/students/{id}")
    public ResponseEntity<Students> getStudent(@PathVariable(value = "id") Long studentId) {
        Students student = studentsRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + studentId));
        return ResponseEntity.ok().body(student);
    }

    // Update a student
    @PutMapping("/students/{id}")
    public ResponseEntity<Students> updateStudent(@PathVariable(value = "id") Long studentId,
                                                   @RequestBody Students studentDetails) {
        Students student = studentsRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + studentId));
        student.setNoControl(studentDetails.getNoControl());
        student.setFirstName(studentDetails.getFirstName());
        student.setLastName(studentDetails.getLastName());
        student.setEmail(studentDetails.getEmail());
        final Students updatedStudent = studentsRepository.save(student);
        return ResponseEntity.ok(updatedStudent);
    }

    // Delete a student
    @DeleteMapping("/students/{id}")
    // response entity tipo dicionario se tiene que indicar
    public ResponseEntity<Map<String, Boolean>> deleteStudent(@PathVariable(value = "id") Long studentId) {
        Students student = studentsRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + studentId));
        studentsRepository.delete(student);

        //response building
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return ResponseEntity.ok().body(response);
    }
}
