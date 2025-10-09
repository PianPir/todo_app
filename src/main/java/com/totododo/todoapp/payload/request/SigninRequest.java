package com.totododo.todoapp.payload.request;


import lombok.Data;

@Data
public class SinginRequest {
    private String username;
    private String password;
}
