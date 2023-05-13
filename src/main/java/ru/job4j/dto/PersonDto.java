package ru.job4j.dto;

import ru.job4j.domain.Operation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record PersonDto(
        @NotBlank(message = "Login must be not blank", groups = {
                Operation.OnCreate.class, Operation.OnUpdate.class
        })
        String login,

        @NotBlank(message = "Password must be not blank", groups = {
                Operation.OnCreate.class, Operation.OnUpdate.class
        })
        @Size(min = 8, message = "Password must be more than 8 symbols")
        String password
) { }
