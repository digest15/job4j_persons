package ru.job4j.service;

import ru.job4j.domain.Person;
import ru.job4j.dto.PersonDto;

import java.util.List;
import java.util.Optional;

public interface PersonsService {

    List<Person> findAll();

    Optional<Person> findById(int id);

    Optional<Person> save(Person person);

    boolean delete(int id);

    Optional<Person> findByLogin(String username);

    Optional<Person> patch(PersonDto person);

    default void validatePerson(Person person) throws IllegalArgumentException {
        if (person.getLogin() == null || person.getPassword() == null) {
            throw new IllegalArgumentException("login and password mustn't be null");
        }
        if (person.getLogin().isEmpty() || person.getPassword().isEmpty()) {
            throw new IllegalArgumentException("login and password mustn't be empty");
        }
    }

    default void validatePersonDto(PersonDto person) throws IllegalArgumentException {
        if (person.login() == null || person.password() == null) {
            throw new IllegalArgumentException("login and password mustn't be null");
        }
        if (person.login().isEmpty() || person.password().isEmpty()) {
            throw new IllegalArgumentException("login and password mustn't be empty");
        }
    }
}
