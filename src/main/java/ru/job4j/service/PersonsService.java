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
}
