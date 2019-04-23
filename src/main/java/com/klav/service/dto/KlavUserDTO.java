package com.klav.service.dto;

import javax.validation.constraints.NotNull;
import java.time.Instant;

public class KlavUserDTO {

    public KlavUserDTO() {
    }

    private Long id;


    private String phoneNumber;


    private Instant birthdate;


    private String selfDescription;


    private String gender;


    private String nationality;


    private Long livesAt;


    @NotNull
    private UserDTO person;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Instant getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Instant birthdate) {
        this.birthdate = birthdate;
    }

    public String getSelfDescription() {
        return selfDescription;
    }

    public void setSelfDescription(String selfDescription) {
        this.selfDescription = selfDescription;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Long getLivesAt() {
        return livesAt;
    }

    public void setLivesAt(Long livesAt) {
        this.livesAt = livesAt;
    }

    public UserDTO getPerson() {
        return person;
    }

    public void setPerson(UserDTO person) {
        this.person = person;
    }


}
