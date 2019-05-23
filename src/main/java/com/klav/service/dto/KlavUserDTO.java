package com.klav.service.dto;

import com.klav.domain.Address;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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

    private String login;

    private String firstName;


    private String lastName;


    private String email;


    private Boolean activated;


    private String activationKey;


    private String resetKey;


    private Instant resetDate;

    private String password;

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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public String getResetKey() {
        return resetKey;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    public Instant getResetDate() {
        return resetDate;
    }

    public void setResetDate(Instant resetDate) {
        this.resetDate = resetDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
