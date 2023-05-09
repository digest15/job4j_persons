package ru.job4j.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.domain.Person;
import ru.job4j.dto.PersonDto;
import ru.job4j.service.PersonsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

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
        return personsService.findById(id)
                .map(p -> ResponseEntity.ok().body(p))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, String.format("Not found Person with id %d", id)
                ));
    }

    @PostMapping("/")
    public ResponseEntity<Person> create(@RequestBody Person person) {
        personsService.validatePerson(person);

        return personsService.save(person)
                .map(p -> ResponseEntity.ok().body(p))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "There was a problem when save Person"
                ));
    }

    @PutMapping("/")
    public ResponseEntity<Person> update(@RequestBody Person person) {
        personsService.validatePerson(person);

        return personsService.save(person)
                .map(p -> ResponseEntity.ok().body(p))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "There was a problem when update Person"
                ));
    }

    @PatchMapping("/")
    public ResponseEntity<Person> patch(@RequestBody PersonDto person) {
        personsService.validatePersonDto(person);

        return personsService.patch(person)
                .map(p -> ResponseEntity.ok().body(p))
                .orElseThrow(() -> new ResponseStatusException(
                             HttpStatus.NOT_FOUND, "There was a problem when patch Person"
                ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        if (!personsService.delete(id)) {
            throw new DeleteException(String.format("There is a problem with deleting person with id %d", id));
        }
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(value = { IllegalArgumentException.class })
    public void exceptionHandler(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap() { {
            put("message", e.getMessage());
            put("type", e.getClass());
        }}));

        log.error(e.getLocalizedMessage());
    }

}
