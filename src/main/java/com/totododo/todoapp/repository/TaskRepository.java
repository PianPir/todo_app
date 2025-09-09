package com.totododo.todoapp.repository;

import com.totododo.todoapp.model.Category;
import com.totododo.todoapp.model.Task ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByCategory(Category category);
}