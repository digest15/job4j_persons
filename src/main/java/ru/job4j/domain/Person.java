package ru.job4j.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Person {

    @EqualsAndHashCode.Include
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Positive(message = "Id must be non zero", groups = {
            Operation.OnUpdate.class, Operation.OnDelete.class
    })
    private int id;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Login must be not blank", groups = {
            Operation.OnCreate.class, Operation.OnUpdate.class
    })
    private String login;

    @Column(nullable = false)
    @NotBlank(message = "Password must be not blank", groups = {
            Operation.OnCreate.class, Operation.OnUpdate.class
    })
    @Size(min = 8, message = "Password must be more than 8 symbols")
    private String password;

}
