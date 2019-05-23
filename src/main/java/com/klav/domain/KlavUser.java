package com.klav.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A KlavUser.
 */
@Entity
@Table(name = "klav_user")
public class KlavUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "birthdate")
    private Instant birthdate;

    @Column(name = "self_description")
    private String selfDescription;

    @Column(name = "gender")
    private String gender;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "login")
    private String login;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "activated")
    private Boolean activated;

    @Column(name = "activation_key")
    private String activationKey;

    @Column(name = "reset_key")
    private String resetKey;

    @Column(name = "reset_date")
    private Instant resetDate;

    @Column(name = "jhi_password")
    private String password;

    @OneToOne    @JoinColumn(unique = true)
    private Address livesAt;

    @OneToMany(mappedBy = "klavUser")
    private Set<File> profilePictures = new HashSet<>();
    @OneToMany(mappedBy = "klavUser")
    private Set<Booking> bookings = new HashSet<>();
    @OneToMany(mappedBy = "klavUser")
    private Set<Review> reviews = new HashSet<>();
    @ManyToMany
    @JoinTable(name = "klav_user_chat",
               joinColumns = @JoinColumn(name = "klav_users_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "chats_id", referencedColumnName = "id"))
    private Set<Chat> chats = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public KlavUser phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Instant getBirthdate() {
        return birthdate;
    }

    public KlavUser birthdate(Instant birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    public void setBirthdate(Instant birthdate) {
        this.birthdate = birthdate;
    }

    public String getSelfDescription() {
        return selfDescription;
    }

    public KlavUser selfDescription(String selfDescription) {
        this.selfDescription = selfDescription;
        return this;
    }

    public void setSelfDescription(String selfDescription) {
        this.selfDescription = selfDescription;
    }

    public String getGender() {
        return gender;
    }

    public KlavUser gender(String gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public KlavUser nationality(String nationality) {
        this.nationality = nationality;
        return this;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getLogin() {
        return login;
    }

    public KlavUser login(String login) {
        this.login = login;
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public KlavUser firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public KlavUser lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public KlavUser email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean isActivated() {
        return activated;
    }

    public KlavUser activated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public KlavUser activationKey(String activationKey) {
        this.activationKey = activationKey;
        return this;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public String getResetKey() {
        return resetKey;
    }

    public KlavUser resetKey(String resetKey) {
        this.resetKey = resetKey;
        return this;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    public Instant getResetDate() {
        return resetDate;
    }

    public KlavUser resetDate(Instant resetDate) {
        this.resetDate = resetDate;
        return this;
    }

    public void setResetDate(Instant resetDate) {
        this.resetDate = resetDate;
    }

    public String getPassword() {
        return password;
    }

    public KlavUser password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Address getLivesAt() {
        return livesAt;
    }

    public KlavUser livesAt(Address address) {
        this.livesAt = address;
        return this;
    }

    public void setLivesAt(Address address) {
        this.livesAt = address;
    }

    public Set<File> getProfilePictures() {
        return profilePictures;
    }

    public KlavUser profilePictures(Set<File> files) {
        this.profilePictures = files;
        return this;
    }

    public KlavUser addProfilePictures(File file) {
        this.profilePictures.add(file);
        file.setKlavUser(this);
        return this;
    }

    public KlavUser removeProfilePictures(File file) {
        this.profilePictures.remove(file);
        file.setKlavUser(null);
        return this;
    }

    public void setProfilePictures(Set<File> files) {
        this.profilePictures = files;
    }

    public Set<Booking> getBookings() {
        return bookings;
    }

    public KlavUser bookings(Set<Booking> bookings) {
        this.bookings = bookings;
        return this;
    }

    public KlavUser addBookings(Booking booking) {
        this.bookings.add(booking);
        booking.setKlavUser(this);
        return this;
    }

    public KlavUser removeBookings(Booking booking) {
        this.bookings.remove(booking);
        booking.setKlavUser(null);
        return this;
    }

    public void setBookings(Set<Booking> bookings) {
        this.bookings = bookings;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public KlavUser reviews(Set<Review> reviews) {
        this.reviews = reviews;
        return this;
    }

    public KlavUser addReviews(Review review) {
        this.reviews.add(review);
        review.setKlavUser(this);
        return this;
    }

    public KlavUser removeReviews(Review review) {
        this.reviews.remove(review);
        review.setKlavUser(null);
        return this;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public Set<Chat> getChats() {
        return chats;
    }

    public KlavUser chats(Set<Chat> chats) {
        this.chats = chats;
        return this;
    }

    public KlavUser addChat(Chat chat) {
        this.chats.add(chat);
        chat.getKlavUsers().add(this);
        return this;
    }

    public KlavUser removeChat(Chat chat) {
        this.chats.remove(chat);
        chat.getKlavUsers().remove(this);
        return this;
    }

    public void setChats(Set<Chat> chats) {
        this.chats = chats;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        KlavUser klavUser = (KlavUser) o;
        if (klavUser.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), klavUser.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "KlavUser{" +
            "id=" + getId() +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", birthdate='" + getBirthdate() + "'" +
            ", selfDescription='" + getSelfDescription() + "'" +
            ", gender='" + getGender() + "'" +
            ", nationality='" + getNationality() + "'" +
            ", login='" + getLogin() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", activated='" + isActivated() + "'" +
            ", activationKey='" + getActivationKey() + "'" +
            ", resetKey='" + getResetKey() + "'" +
            ", resetDate='" + getResetDate() + "'" +
            ", password='" + getPassword() + "'" +
            "}";
    }
}
