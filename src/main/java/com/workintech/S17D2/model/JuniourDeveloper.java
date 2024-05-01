package com.workintech.S17D2.model;

public class JuniourDeveloper extends Developer {
    public JuniourDeveloper(Integer id, String name, Double salary) {
        super(id, name, salary, Experience.JUNIOR);
    }
}
