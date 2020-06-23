package com.nayoung.app.controller;

import com.nayoung.app.domain.Student;
import com.nayoung.app.repository.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/students/new")
    public String showStudentForm(Model model) {
        model.addAttribute("studentForm", new StudentForm());
        return "students/studentForm";
    }

    @PostMapping("/students/new")
    public String createStudent(@Valid StudentForm studentForm, BindingResult result) {
        if (result.hasErrors()) {
            return "students/studentForm";
        }

        Student student = new Student();
        student.setName(studentForm.getName());
        student.setEmail(studentForm.getEmail());
        studentRepository.save(student);
        return "redirect:/students";
    }

    @GetMapping("/students")
    public String list(Model model) {
        List<Student> students = studentRepository.findAll();
        model.addAttribute("students", students);
        return "students/studentList";
    }

    @GetMapping("/students/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model){
        Student student = studentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));
        model.addAttribute("student", student);
        return "students/studentUpdate";
    }

    @PostMapping("/students/update/{id}")
    public String updateStudent(Student student){
        studentRepository.save(student);
        return "redirect:/students";
    }

    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable("id") Long id, Model model) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));
        studentRepository.delete(student);
        model.addAttribute("students", studentRepository.findAll());
        return "redirect:/students";
    }
}
