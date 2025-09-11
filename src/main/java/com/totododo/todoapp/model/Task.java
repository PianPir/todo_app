package com.totododo.todoapp.model;


import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "tasks")
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Column(nullable = false)
    private String title;

    private boolean completed;

    public Task() {}

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
