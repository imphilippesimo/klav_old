package com.klav.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import com.klav.domain.enumeration.FileType;

/**
 * A File.
 */
@Entity
@Table(name = "file")
public class File implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_url")
    private String fileURL;

    @Column(name = "name")
    private String name;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @Column(name = "mime_type")
    private String mimeType;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private FileType type;

    @ManyToOne
    @JsonIgnoreProperties("travelProofs")
    private Travel travel;

    @ManyToOne
    @JsonIgnoreProperties("profilePictures")
    private KlavUser klavUser;

    @ManyToOne
    @JsonIgnoreProperties("pictures")
    private TravelPackage travelPackage;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileURL() {
        return fileURL;
    }

    public File fileURL(String fileURL) {
        this.fileURL = fileURL;
        return this;
    }

    public void setFileURL(String fileURL) {
        this.fileURL = fileURL;
    }

    public String getName() {
        return name;
    }

    public File name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public File updatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getMimeType() {
        return mimeType;
    }

    public File mimeType(String mimeType) {
        this.mimeType = mimeType;
        return this;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public FileType getType() {
        return type;
    }

    public File type(FileType type) {
        this.type = type;
        return this;
    }

    public void setType(FileType type) {
        this.type = type;
    }

    public Travel getTravel() {
        return travel;
    }

    public File travel(Travel travel) {
        this.travel = travel;
        return this;
    }

    public void setTravel(Travel travel) {
        this.travel = travel;
    }

    public KlavUser getKlavUser() {
        return klavUser;
    }

    public File klavUser(KlavUser klavUser) {
        this.klavUser = klavUser;
        return this;
    }

    public void setKlavUser(KlavUser klavUser) {
        this.klavUser = klavUser;
    }

    public TravelPackage getTravelPackage() {
        return travelPackage;
    }

    public File travelPackage(TravelPackage travelPackage) {
        this.travelPackage = travelPackage;
        return this;
    }

    public void setTravelPackage(TravelPackage travelPackage) {
        this.travelPackage = travelPackage;
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
        File file = (File) o;
        if (file.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), file.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "File{" +
            "id=" + getId() +
            ", fileURL='" + getFileURL() + "'" +
            ", name='" + getName() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", mimeType='" + getMimeType() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
