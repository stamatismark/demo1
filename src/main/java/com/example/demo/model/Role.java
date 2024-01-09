package com.example.demo.model;

public enum Role {
	PROFESSOR("PROFESSOR"),
    STUDENT("STUDENT");

    private final String value;

    private Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}