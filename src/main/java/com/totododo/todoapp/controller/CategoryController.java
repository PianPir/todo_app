package com.totododo.todoapp.controller;


import com.totododo.todoapp.model.Category;
import com.totododo.todoapp.model.Task;
import com.totododo.todoapp.repository.CategoryRepository;
import com.totododo.todoapp.repository.TaskRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:8080")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @GetMapping("/{id}/tasks")
    public List<Task> getTasksByCategory(@PathVariable Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return taskRepository.findByCategory(category);
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@Valid @RequestBody Category category, BindingResult result) {
        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, result.getAllErrors().get(0).getDefaultMessage());
        }

        Category saveCategory = categoryRepository.save(category);
        return new ResponseEntity<>(saveCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @Valid @RequestBody Category categoryDetails,
                                                   BindingResult result) {
        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, result.getAllErrors().get(0).getDefaultMessage());
        }

        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));

        category.setName(categoryDetails.getName());

        Category updateCategory = categoryRepository.save(category);
        return ResponseEntity.ok(updateCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        categoryRepository.delete(category);
        return ResponseEntity.noContent().build();
    }

}
