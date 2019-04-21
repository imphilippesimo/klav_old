package com.klav.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Booking.
 */
@Entity
@Table(name = "booking")
public class Booking implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("bookings")
    private Travel travel;

    @ManyToOne
    @JsonIgnoreProperties("bookings")
    private KlavUser klavUser;

    @OneToMany(mappedBy = "booking")
    private Set<TravelPackage> packages = new HashSet<>();
    @OneToMany(mappedBy = "booking")
    private Set<Status> statuses = new HashSet<>();
    @OneToMany(mappedBy = "booking")
    private Set<TrustIndex> trustIndices = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Travel getTravel() {
        return travel;
    }

    public Booking travel(Travel travel) {
        this.travel = travel;
        return this;
    }

    public void setTravel(Travel travel) {
        this.travel = travel;
    }

    public KlavUser getKlavUser() {
        return klavUser;
    }

    public Booking klavUser(KlavUser klavUser) {
        this.klavUser = klavUser;
        return this;
    }

    public void setKlavUser(KlavUser klavUser) {
        this.klavUser = klavUser;
    }

    public Set<TravelPackage> getPackages() {
        return packages;
    }

    public Booking packages(Set<TravelPackage> travelPackages) {
        this.packages = travelPackages;
        return this;
    }

    public Booking addPackages(TravelPackage travelPackage) {
        this.packages.add(travelPackage);
        travelPackage.setBooking(this);
        return this;
    }

    public Booking removePackages(TravelPackage travelPackage) {
        this.packages.remove(travelPackage);
        travelPackage.setBooking(null);
        return this;
    }

    public void setPackages(Set<TravelPackage> travelPackages) {
        this.packages = travelPackages;
    }

    public Set<Status> getStatuses() {
        return statuses;
    }

    public Booking statuses(Set<Status> statuses) {
        this.statuses = statuses;
        return this;
    }

    public Booking addStatus(Status status) {
        this.statuses.add(status);
        status.setBooking(this);
        return this;
    }

    public Booking removeStatus(Status status) {
        this.statuses.remove(status);
        status.setBooking(null);
        return this;
    }

    public void setStatuses(Set<Status> statuses) {
        this.statuses = statuses;
    }

    public Set<TrustIndex> getTrustIndices() {
        return trustIndices;
    }

    public Booking trustIndices(Set<TrustIndex> trustIndices) {
        this.trustIndices = trustIndices;
        return this;
    }

    public Booking addTrustIndex(TrustIndex trustIndex) {
        this.trustIndices.add(trustIndex);
        trustIndex.setBooking(this);
        return this;
    }

    public Booking removeTrustIndex(TrustIndex trustIndex) {
        this.trustIndices.remove(trustIndex);
        trustIndex.setBooking(null);
        return this;
    }

    public void setTrustIndices(Set<TrustIndex> trustIndices) {
        this.trustIndices = trustIndices;
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
        Booking booking = (Booking) o;
        if (booking.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), booking.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Booking{" +
            "id=" + getId() +
            "}";
    }
}
