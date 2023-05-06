package ru.job4j.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.job4j.domain.Person;
import ru.job4j.repository.PersonRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class PersonsServiceImpl implements PersonsService {

    private final PersonRepository personRepository;

    @Override
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    public Optional<Person> findById(int id) {
        return personRepository.findById(id);
    }

    @Override
    public Optional<Person> save(Person person) {
        Optional<Person> saved = Optional.empty();
        try {
            saved = Optional.of(personRepository.save(person));
        } catch (Exception e) {
            log.error("Save or Update was wrong", e);
        }

        return saved;
    }

    @Override
    public void delete(int id) {
        Person person = new Person();
        person.setId(id);
        personRepository.delete(person);
    }
}
