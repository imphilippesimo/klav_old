package com.klav.service.dto;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

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


    private Set<Long> profilePictures = new HashSet<>();


    private Set<Long> bookings = new HashSet<>();

    private Set<Long> reviews = new HashSet<>();

    private Set<Long> chats = new HashSet<>();

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

    public Set<Long> getProfilePictures() {
        return profilePictures;
    }

    public void setProfilePictures(Set<Long> profilePictures) {
        this.profilePictures = profilePictures;
    }

    public Set<Long> getBookings() {
        return bookings;
    }

    public void setBookings(Set<Long> bookings) {
        this.bookings = bookings;
    }

    public Set<Long> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Long> reviews) {
        this.reviews = reviews;
    }

    public Set<Long> getChats() {
        return chats;
    }

    public void setChats(Set<Long> chats) {
        this.chats = chats;
    }
}
