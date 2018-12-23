package com.klav.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.klav.domain.enumeration.DeliveryMode;

/**
 * A Travel.
 */
@Entity
@Table(name = "travel")
public class Travel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "departure_date", nullable = false)
    private Instant departureDate;

    @NotNull
    @Column(name = "arrival_date", nullable = false)
    private Instant arrivalDate;

    @NotNull
    @Column(name = "departure_country", nullable = false)
    private String departureCountry;

    @NotNull
    @Column(name = "departure_city", nullable = false)
    private String departureCity;

    @NotNull
    @Column(name = "arrival_country", nullable = false)
    private String arrivalCountry;

    @NotNull
    @Column(name = "arrival_city", nullable = false)
    private String arrivalCity;

    @NotNull
    @Column(name = "available_k_gs", nullable = false)
    private Double availableKGs;

    @NotNull
    @Column(name = "price_per_kg", nullable = false)
    private Float pricePerKG;

    @NotNull
    @Column(name = "travel_mode", nullable = false)
    private String travelMode;

    @Column(name = "is_free_of_charge")
    private Boolean isFreeOfCharge;

    @Column(name = "is_accepting_fragile_packages")
    private Boolean isAcceptingFragilePackages;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "delevery_mode", nullable = false)
    private DeliveryMode deleveryMode;

    @NotNull
    @Column(name = "how_to_contact_description", nullable = false)
    private String howToContactDescription;

    @Column(name = "complementary_rules")
    private String complementaryRules;

    @NotNull
    @Column(name = "bookable", nullable = false)
    private Boolean bookable;

    @Column(name = "access_code")
    private String accessCode;

    @OneToOne    @JoinColumn(unique = true)
    private Address destinationAddress;

    @OneToMany(mappedBy = "travel")
    private Set<File> travelProofs = new HashSet<>();
    @OneToMany(mappedBy = "travel")
    private Set<PackageType> acceptedPackageTypes = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDepartureDate() {
        return departureDate;
    }

    public Travel departureDate(Instant departureDate) {
        this.departureDate = departureDate;
        return this;
    }

    public void setDepartureDate(Instant departureDate) {
        this.departureDate = departureDate;
    }

    public Instant getArrivalDate() {
        return arrivalDate;
    }

    public Travel arrivalDate(Instant arrivalDate) {
        this.arrivalDate = arrivalDate;
        return this;
    }

    public void setArrivalDate(Instant arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getDepartureCountry() {
        return departureCountry;
    }

    public Travel departureCountry(String departureCountry) {
        this.departureCountry = departureCountry;
        return this;
    }

    public void setDepartureCountry(String departureCountry) {
        this.departureCountry = departureCountry;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public Travel departureCity(String departureCity) {
        this.departureCity = departureCity;
        return this;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public String getArrivalCountry() {
        return arrivalCountry;
    }

    public Travel arrivalCountry(String arrivalCountry) {
        this.arrivalCountry = arrivalCountry;
        return this;
    }

    public void setArrivalCountry(String arrivalCountry) {
        this.arrivalCountry = arrivalCountry;
    }

    public String getArrivalCity() {
        return arrivalCity;
    }

    public Travel arrivalCity(String arrivalCity) {
        this.arrivalCity = arrivalCity;
        return this;
    }

    public void setArrivalCity(String arrivalCity) {
        this.arrivalCity = arrivalCity;
    }

    public Double getAvailableKGs() {
        return availableKGs;
    }

    public Travel availableKGs(Double availableKGs) {
        this.availableKGs = availableKGs;
        return this;
    }

    public void setAvailableKGs(Double availableKGs) {
        this.availableKGs = availableKGs;
    }

    public Float getPricePerKG() {
        return pricePerKG;
    }

    public Travel pricePerKG(Float pricePerKG) {
        this.pricePerKG = pricePerKG;
        return this;
    }

    public void setPricePerKG(Float pricePerKG) {
        this.pricePerKG = pricePerKG;
    }

    public String getTravelMode() {
        return travelMode;
    }

    public Travel travelMode(String travelMode) {
        this.travelMode = travelMode;
        return this;
    }

    public void setTravelMode(String travelMode) {
        this.travelMode = travelMode;
    }

    public Boolean isIsFreeOfCharge() {
        return isFreeOfCharge;
    }

    public Travel isFreeOfCharge(Boolean isFreeOfCharge) {
        this.isFreeOfCharge = isFreeOfCharge;
        return this;
    }

    public void setIsFreeOfCharge(Boolean isFreeOfCharge) {
        this.isFreeOfCharge = isFreeOfCharge;
    }

    public Boolean isIsAcceptingFragilePackages() {
        return isAcceptingFragilePackages;
    }

    public Travel isAcceptingFragilePackages(Boolean isAcceptingFragilePackages) {
        this.isAcceptingFragilePackages = isAcceptingFragilePackages;
        return this;
    }

    public void setIsAcceptingFragilePackages(Boolean isAcceptingFragilePackages) {
        this.isAcceptingFragilePackages = isAcceptingFragilePackages;
    }

    public DeliveryMode getDeleveryMode() {
        return deleveryMode;
    }

    public Travel deleveryMode(DeliveryMode deleveryMode) {
        this.deleveryMode = deleveryMode;
        return this;
    }

    public void setDeleveryMode(DeliveryMode deleveryMode) {
        this.deleveryMode = deleveryMode;
    }

    public String getHowToContactDescription() {
        return howToContactDescription;
    }

    public Travel howToContactDescription(String howToContactDescription) {
        this.howToContactDescription = howToContactDescription;
        return this;
    }

    public void setHowToContactDescription(String howToContactDescription) {
        this.howToContactDescription = howToContactDescription;
    }

    public String getComplementaryRules() {
        return complementaryRules;
    }

    public Travel complementaryRules(String complementaryRules) {
        this.complementaryRules = complementaryRules;
        return this;
    }

    public void setComplementaryRules(String complementaryRules) {
        this.complementaryRules = complementaryRules;
    }

    public Boolean isBookable() {
        return bookable;
    }

    public Travel bookable(Boolean bookable) {
        this.bookable = bookable;
        return this;
    }

    public void setBookable(Boolean bookable) {
        this.bookable = bookable;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public Travel accessCode(String accessCode) {
        this.accessCode = accessCode;
        return this;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public Address getDestinationAddress() {
        return destinationAddress;
    }

    public Travel destinationAddress(Address address) {
        this.destinationAddress = address;
        return this;
    }

    public void setDestinationAddress(Address address) {
        this.destinationAddress = address;
    }

    public Set<File> getTravelProofs() {
        return travelProofs;
    }

    public Travel travelProofs(Set<File> files) {
        this.travelProofs = files;
        return this;
    }

    public Travel addTravelProofs(File file) {
        this.travelProofs.add(file);
        file.setTravel(this);
        return this;
    }

    public Travel removeTravelProofs(File file) {
        this.travelProofs.remove(file);
        file.setTravel(null);
        return this;
    }

    public void setTravelProofs(Set<File> files) {
        this.travelProofs = files;
    }

    public Set<PackageType> getAcceptedPackageTypes() {
        return acceptedPackageTypes;
    }

    public Travel acceptedPackageTypes(Set<PackageType> packageTypes) {
        this.acceptedPackageTypes = packageTypes;
        return this;
    }

    public Travel addAcceptedPackageType(PackageType packageType) {
        this.acceptedPackageTypes.add(packageType);
        packageType.setTravel(this);
        return this;
    }

    public Travel removeAcceptedPackageType(PackageType packageType) {
        this.acceptedPackageTypes.remove(packageType);
        packageType.setTravel(null);
        return this;
    }

    public void setAcceptedPackageTypes(Set<PackageType> packageTypes) {
        this.acceptedPackageTypes = packageTypes;
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
        Travel travel = (Travel) o;
        if (travel.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), travel.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Travel{" +
            "id=" + getId() +
            ", departureDate='" + getDepartureDate() + "'" +
            ", arrivalDate='" + getArrivalDate() + "'" +
            ", departureCountry='" + getDepartureCountry() + "'" +
            ", departureCity='" + getDepartureCity() + "'" +
            ", arrivalCountry='" + getArrivalCountry() + "'" +
            ", arrivalCity='" + getArrivalCity() + "'" +
            ", availableKGs=" + getAvailableKGs() +
            ", pricePerKG=" + getPricePerKG() +
            ", travelMode='" + getTravelMode() + "'" +
            ", isFreeOfCharge='" + isIsFreeOfCharge() + "'" +
            ", isAcceptingFragilePackages='" + isIsAcceptingFragilePackages() + "'" +
            ", deleveryMode='" + getDeleveryMode() + "'" +
            ", howToContactDescription='" + getHowToContactDescription() + "'" +
            ", complementaryRules='" + getComplementaryRules() + "'" +
            ", bookable='" + isBookable() + "'" +
            ", accessCode='" + getAccessCode() + "'" +
            "}";
    }
}
