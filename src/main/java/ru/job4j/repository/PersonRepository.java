package ru.job4j.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.domain.Person;
import ru.job4j.dto.PersonDto;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends CrudRepository<Person, Integer> {
    @Override
    List<Person> findAll();

    Optional<Person> findByLogin(String login);

    @Transactional
    default Optional<Person> patch(PersonDto person) {
        return findByLogin(person.login())
                .map(p -> {
                    p.setPassword(person.password());
                    return p;
                });
    }
}
