package com.totododo.todoapp.controller;

import com.totododo.todoapp.model.Category;
import com.totododo.todoapp.repository.CategoryRepository;
import com.totododo.todoapp.repository.TaskRepository;
import com.totododo.todoapp.model.Task;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:8080")
public class TaskController {

    private final TaskRepository taskRepository;
    private final CategoryRepository  categoryRepository;

    @GetMapping
    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task, BindingResult result){
        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, result.getAllErrors().get(0).getDefaultMessage());
        }

        if(task.getCategory()!=null && task.getCategory().getId()!=null){
            Category category = categoryRepository.findById(task.getCategory().getId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            task.setCategory(category);
        }

        task.setCompleted(false);
        Task savedTask = taskRepository.save(task);
        return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @Valid @RequestBody Task taskDetails, BindingResult result){
        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, result.getAllErrors().get(0).getDefaultMessage());
        }


        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));

        if(task.getCategory()!=null && task.getCategory().getId()!=null){
            Category category = categoryRepository.findById(task.getCategory().getId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            task.setCategory(category);
        }

        task.setTitle(taskDetails.getTitle());
        task.setCompleted(taskDetails.isCompleted());

        Task updatedTask = taskRepository.save(task);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        taskRepository.delete(task);
        return ResponseEntity.noContent().build();
    }
}
