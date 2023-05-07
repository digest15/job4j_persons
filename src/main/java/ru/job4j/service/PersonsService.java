package ru.job4j.service;

import ru.job4j.domain.Person;

import java.util.List;
import java.util.Optional;

public interface PersonsService {

    List<Person> findAll();

    Optional<Person> findById(int id);

    Optional<Person> save(Person person);

    boolean delete(int id);

    Optional<Person> findByLogin(String username);

    default void validatePerson(Person person) throws IllegalArgumentException {
        if (person.getLogin() == null || person.getPassword() == null) {
            throw new IllegalArgumentException("login and password mustn't be null");
        }
        if (person.getLogin().isEmpty() || person.getPassword().isEmpty()) {
            throw new IllegalArgumentException("login and password mustn't be empty");
        }
    }
}
