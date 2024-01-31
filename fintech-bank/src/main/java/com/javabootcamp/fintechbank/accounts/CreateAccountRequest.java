package com.javabootcamp.fintechbank.accounts;

import jakarta.validation.constraints.NotNull;

public class CreateAccountRequest {

    @NotNull
    private String type;

    @NotNull
    private String name;

    private Double balance;

    public CreateAccountRequest() {
    }

    public CreateAccountRequest(String type, String name) {
        this.type = type;
        this.name = name;
        this.balance = 0d;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
