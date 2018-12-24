package com.klav.service.dto;

import com.klav.domain.enumeration.DeliveryMode;

import javax.validation.constraints.*;
import java.time.Instant;

public class TravelDTO {


    private Long id;

    @Future
    private Instant departureDate;

    @Future
    private Instant arrivalDate;

    @NotBlank
    @Size(min = 3, max = 30)
    private String departureCountry;

    @NotBlank
    @Size(min = 3, max = 30)
    private String departureCity;

    @NotBlank
    @Size(min = 3, max = 30)
    private String arrivalCountry;

    @NotBlank
    @Size(min = 3, max = 30)
    private String arrivalCity;

    @NotNull
    //maximum of 2 integral numbers and 5 fractional numbers
    @Digits(integer = 2, fraction = 5)
    private Double availableKGs;

    @NotNull
    //maximum of 2 integral numbers and 5 fractional numbers
    @Digits(integer = 2, fraction = 5)
    private Float pricePerKG;

    @NotBlank
    @Size(min = 3, max = 30)
    private String travelMode;

    @NotNull
    private Boolean isFreeOfCharge;

    @NotNull
    private Boolean isAcceptingFragilePackages;

    @NotNull
    private DeliveryMode deleveryMode;

    @NotBlank
    private String howToContactDescription;


    private String complementaryRules;

    @NotNull
    private Boolean bookable;

    @NotNull
    private Long destinationAddress;

    public TravelDTO() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Instant departureDate) {
        this.departureDate = departureDate;
    }

    public Instant getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Instant arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getDepartureCountry() {
        return departureCountry;
    }

    public void setDepartureCountry(String departureCountry) {
        this.departureCountry = departureCountry;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public String getArrivalCountry() {
        return arrivalCountry;
    }

    public void setArrivalCountry(String arrivalCountry) {
        this.arrivalCountry = arrivalCountry;
    }

    public String getArrivalCity() {
        return arrivalCity;
    }

    public void setArrivalCity(String arrivalCity) {
        this.arrivalCity = arrivalCity;
    }

    public Double getAvailableKGs() {
        return availableKGs;
    }

    public void setAvailableKGs(Double availableKGs) {
        this.availableKGs = availableKGs;
    }

    public Float getPricePerKG() {
        return pricePerKG;
    }

    public void setPricePerKG(Float pricePerKG) {
        this.pricePerKG = pricePerKG;
    }

    public String getTravelMode() {
        return travelMode;
    }

    public void setTravelMode(String travelMode) {
        this.travelMode = travelMode;
    }

    public Boolean getFreeOfCharge() {
        return isFreeOfCharge;
    }

    public void setFreeOfCharge(Boolean freeOfCharge) {
        isFreeOfCharge = freeOfCharge;
    }

    public Boolean getAcceptingFragilePackages() {
        return isAcceptingFragilePackages;
    }

    public void setAcceptingFragilePackages(Boolean acceptingFragilePackages) {
        isAcceptingFragilePackages = acceptingFragilePackages;
    }

    public DeliveryMode getDeleveryMode() {
        return deleveryMode;
    }

    public void setDeleveryMode(DeliveryMode deleveryMode) {
        this.deleveryMode = deleveryMode;
    }

    public String getHowToContactDescription() {
        return howToContactDescription;
    }

    public void setHowToContactDescription(String howToContactDescription) {
        this.howToContactDescription = howToContactDescription;
    }

    public String getComplementaryRules() {
        return complementaryRules;
    }

    public void setComplementaryRules(String complementaryRules) {
        this.complementaryRules = complementaryRules;
    }

    public Boolean getBookable() {
        return bookable;
    }

    public void setBookable(Boolean bookable) {
        this.bookable = bookable;
    }


    public Long getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(Long destinationAddress) {
        this.destinationAddress = destinationAddress;
    }
}
