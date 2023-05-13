package ru.job4j.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.domain.Operation;
import ru.job4j.domain.Person;
import ru.job4j.dto.PersonDto;
import ru.job4j.service.PersonsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/person")
@AllArgsConstructor
@Slf4j
public class PersonController {

    private final PersonsService personsService;
    private final ObjectMapper objectMapper;

    @GetMapping("/")
    public List<Person> findAll() {
        return personsService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        Optional<Person> person = personsService.findById(id);
        if (person.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("Not found Person with id %d", id)
            );
        }
        return ResponseEntity.ok(person.get());
    }

    @PostMapping("/")
    public ResponseEntity<Person> create(@RequestBody @Validated(Operation.OnCreate.class) Person person) {
        Optional<Person> saved = personsService.save(person);
        if (saved.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There was a problem when save Person");
        }
        return ResponseEntity.ok(saved.get());
    }

    @PutMapping("/")
    public ResponseEntity<Person> update(@RequestBody @Validated(Operation.OnUpdate.class) Person person) {
        Optional<Person> updated = personsService.save(person);
        if (updated.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There was a problem when update Person");
        }
        return ResponseEntity.ok(updated.get());
    }

    @PatchMapping("/")
    public ResponseEntity<Person> patch(@RequestBody @Validated(Operation.OnUpdate.class) PersonDto person) {
        Optional<Person> patched = personsService.patch(person);
        if (patched.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There was a problem when patch Person");
        }
        return ResponseEntity.ok(patched.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Validated(Operation.OnDelete.class) int id) {
        if (!personsService.delete(id)) {
            throw new DeleteException(String.format("There is a problem with deleting person with id %d", id));
        }
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(value = { DeleteException.class })
    public void deleteExceptionHandler(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap() { {
            put("message", e.getMessage());
            put("type", e.getClass());
        }}));

        log.error(e.getLocalizedMessage());
    }

}
