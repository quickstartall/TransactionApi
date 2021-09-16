package com.raquibul.bank.payment.rest.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class User {
    @NotNull
    private String name;
    @NotNull
    private String email;
    private String mobile;
    @NotNull
    private String password;
}
