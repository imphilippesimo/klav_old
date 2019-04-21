package com.klav.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A PackageType.
 */
@Entity
@Table(name = "package_type")
public class PackageType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JsonIgnoreProperties("acceptedPackageTypes")
    private Travel travel;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public PackageType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Travel getTravel() {
        return travel;
    }

    public PackageType travel(Travel travel) {
        this.travel = travel;
        return this;
    }

    public void setTravel(Travel travel) {
        this.travel = travel;
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
        PackageType packageType = (PackageType) o;
        if (packageType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), packageType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PackageType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
