package com.airtribe.learntrack.entity;

import com.airtribe.learntrack.util.IdGenerator;

public class Trainer extends Person {
    private String expertise;

    public Trainer(String firstName, String lastName, String email, String expertise) {
        super(IdGenerator.getNextTrainerId(), firstName, lastName, email);
        this.expertise = expertise;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    @Override
    public String getDisplayName() {
        return "Trainer: " + super.getDisplayName() + " (" + expertise + ")";
    }
}
