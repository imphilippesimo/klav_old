package com.klav.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.klav.domain.enumeration.DeliveryMode;

/**
 * A TravelPackage.
 */
@Entity
@Table(name = "travel_package")
public class TravelPackage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "access_code")
    private String accessCode;

    @Column(name = "delevery_code")
    private String deleveryCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "desired_delevery_mode")
    private DeliveryMode desiredDeleveryMode;

    @Column(name = "description")
    private String description;

    @Column(name = "price_per_kg")
    private Float pricePerKG;

    @Column(name = "fragile")
    private Boolean fragile;

    /**
     * Optional parameter, the receiver of a package may potentially be a user of the platform
     */
    @ApiModelProperty(value = "Optional parameter, the receiver of a package may potentially be a user of the platform")
    @OneToOne    @JoinColumn(unique = true)
    private KlavUser receiver;

    /**
     * A travel package should have a destination address
     */
    @ApiModelProperty(value = "A travel package should have a destination address")
    @OneToOne    @JoinColumn(unique = true)
    private Address destinationAddress;

    @OneToOne    @JoinColumn(unique = true)
    private PackageType type;

    @OneToMany(mappedBy = "travelPackage")
    private Set<File> pictures = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("packages")
    private Booking booking;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public TravelPackage title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getWeight() {
        return weight;
    }

    public TravelPackage weight(Double weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public TravelPackage accessCode(String accessCode) {
        this.accessCode = accessCode;
        return this;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public String getDeleveryCode() {
        return deleveryCode;
    }

    public TravelPackage deleveryCode(String deleveryCode) {
        this.deleveryCode = deleveryCode;
        return this;
    }

    public void setDeleveryCode(String deleveryCode) {
        this.deleveryCode = deleveryCode;
    }

    public DeliveryMode getDesiredDeleveryMode() {
        return desiredDeleveryMode;
    }

    public TravelPackage desiredDeleveryMode(DeliveryMode desiredDeleveryMode) {
        this.desiredDeleveryMode = desiredDeleveryMode;
        return this;
    }

    public void setDesiredDeleveryMode(DeliveryMode desiredDeleveryMode) {
        this.desiredDeleveryMode = desiredDeleveryMode;
    }

    public String getDescription() {
        return description;
    }

    public TravelPackage description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPricePerKG() {
        return pricePerKG;
    }

    public TravelPackage pricePerKG(Float pricePerKG) {
        this.pricePerKG = pricePerKG;
        return this;
    }

    public void setPricePerKG(Float pricePerKG) {
        this.pricePerKG = pricePerKG;
    }

    public Boolean isFragile() {
        return fragile;
    }

    public TravelPackage fragile(Boolean fragile) {
        this.fragile = fragile;
        return this;
    }

    public void setFragile(Boolean fragile) {
        this.fragile = fragile;
    }

    public KlavUser getReceiver() {
        return receiver;
    }

    public TravelPackage receiver(KlavUser klavUser) {
        this.receiver = klavUser;
        return this;
    }

    public void setReceiver(KlavUser klavUser) {
        this.receiver = klavUser;
    }

    public Address getDestinationAddress() {
        return destinationAddress;
    }

    public TravelPackage destinationAddress(Address address) {
        this.destinationAddress = address;
        return this;
    }

    public void setDestinationAddress(Address address) {
        this.destinationAddress = address;
    }

    public PackageType getType() {
        return type;
    }

    public TravelPackage type(PackageType packageType) {
        this.type = packageType;
        return this;
    }

    public void setType(PackageType packageType) {
        this.type = packageType;
    }

    public Set<File> getPictures() {
        return pictures;
    }

    public TravelPackage pictures(Set<File> files) {
        this.pictures = files;
        return this;
    }

    public TravelPackage addPictures(File file) {
        this.pictures.add(file);
        file.setTravelPackage(this);
        return this;
    }

    public TravelPackage removePictures(File file) {
        this.pictures.remove(file);
        file.setTravelPackage(null);
        return this;
    }

    public void setPictures(Set<File> files) {
        this.pictures = files;
    }

    public Booking getBooking() {
        return booking;
    }

    public TravelPackage booking(Booking booking) {
        this.booking = booking;
        return this;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
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
        TravelPackage travelPackage = (TravelPackage) o;
        if (travelPackage.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), travelPackage.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TravelPackage{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", weight=" + getWeight() +
            ", accessCode='" + getAccessCode() + "'" +
            ", deleveryCode='" + getDeleveryCode() + "'" +
            ", desiredDeleveryMode='" + getDesiredDeleveryMode() + "'" +
            ", description='" + getDescription() + "'" +
            ", pricePerKG=" + getPricePerKG() +
            ", fragile='" + isFragile() + "'" +
            "}";
    }
}
