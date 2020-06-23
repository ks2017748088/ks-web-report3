package com.nayoung.app.repository;

import com.nayoung.app.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByName(final String name);
    List<Student> findAllByEmailIsLike(final String email);
}
