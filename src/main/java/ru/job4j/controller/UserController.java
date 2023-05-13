package ru.job4j.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.domain.Operation;
import ru.job4j.domain.Person;
import ru.job4j.service.PersonsService;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final PersonsService personsService;

    @PostMapping("/sign-up")
    @Validated(Operation.OnCreate.class)
    public ResponseEntity<Person> signUp(@RequestBody Person person) {
        return personsService.save(person)
                .map(p -> ResponseEntity.ok().body(p))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "There is user with the same login"
                ));
    }

    @GetMapping("/all")
    public List<Person> findAll() {
        return personsService.findAll();
    }

}
