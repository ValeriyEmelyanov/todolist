package com.example.todolist.services;

import com.example.todolist.persist.entity.ToDo;
import com.example.todolist.persist.entity.User;
import com.example.todolist.persist.repo.ToDoRepository;
import com.example.todolist.repr.ToDoRepr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ToDoService {

    private ToDoRepository toDoRepository;

    private UserService userService;

    @Autowired
    public ToDoService(ToDoRepository toDoRepository, UserService userService) {
        this.toDoRepository = toDoRepository;
        this.userService = userService;
    }

    public Optional<ToDoRepr> findById(Long id) {
        return toDoRepository.findById(id).map(ToDoRepr::new);
    }

    public List<ToDoRepr> findToDosByUserId(Long userId) {
        return toDoRepository.findToDosByUserId(userId);
    }

    public void save(ToDoRepr toDoRepr) {
        Optional<String> currentUserOptional = userService.getCurrentUser();
        if (currentUserOptional.isPresent()) {
            Optional<User> userOptional = userService.getByUsername(currentUserOptional.get());
            if (userOptional.isPresent()) {
                ToDo toDo = new ToDo();
                toDo.setId(toDoRepr.getId());
                toDo.setDescription(toDoRepr.getDescription());
                toDo.setTargetDate(toDoRepr.getTargetDate());
                toDo.setUser(userOptional.get());
                toDoRepository.save(toDo);
            }
        }
    }

    public void delete(Long id) {
        toDoRepository.findById(id)
                .ifPresent(toDo -> toDoRepository.delete(toDo));
    }
}
