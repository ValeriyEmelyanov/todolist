package com.example.todolist.controllers;

import com.example.todolist.exceptions.ResourceNotFoundException;
import com.example.todolist.repr.ToDoRepr;
import com.example.todolist.services.ToDoService;
import com.example.todolist.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ToDoController {

    private ToDoService todoService;

    private UserService userService;

    @Autowired
    public ToDoController(ToDoService toDoService, UserService userService) {
        this.todoService = toDoService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String indexPage(Model model) {
        List<ToDoRepr> todos = todoService.findToDosByUserId(
                userService.getCurrentUserId().orElseThrow(ResourceNotFoundException::new));
        model.addAttribute("todos", todos);
        return "index";
    }

    @GetMapping("/todo/{id}")
    public String todoPage(@PathVariable("id") Long id, Model model) {
        ToDoRepr todoRepr = todoService.findById(id).orElseThrow(ResourceNotFoundException::new);
        model.addAttribute("todo", todoRepr);
        return "todo";
    }

    @GetMapping("/todo/create")
    public String createTodoPage(Model model) {
        model.addAttribute("todo", new ToDoRepr());
        return "todo";
    }

    @PostMapping("/todo/create")
    public String createTodoPost(@ModelAttribute("todo") ToDoRepr todo) {
        todoService.save(todo);
        return "redirect:/";
    }

    @GetMapping("/todo/delete/{id}")
    public String deleteTodo(@PathVariable Long id) {
        todoService.delete(id);
        return "redirect:/";
    }
}
