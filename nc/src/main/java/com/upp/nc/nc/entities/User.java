package com.upp.nc.nc.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class User {

    @Id
    private String username;

    private String password;
    private String name;
    private String surname;
    private String email;
    private String title;
    private String city;
    private String country;
    private String scientificFields;
    private Integer role;
    private Boolean reviewer;
    private String magazines;
    private Boolean active;
    private String link;
    private Date linkExpiration;

    public User() {
        this.reviewer = false;
        this.active = false;
    }

    public User(String username, String password, String name, String surname, String email, String title, String city,
                String country, String scientificFields, Integer role, Boolean reviewer, String magazines,
                Boolean active, String link, Date linkExpiration) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.title = title;
        this.city = city;
        this.country = country;
        this.scientificFields = scientificFields;
        this.role = role;
        this.reviewer = reviewer;
        this.magazines = magazines;
        this.active = active;
        this.link = link;
        this.linkExpiration = linkExpiration;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getScientificFields() {
        return scientificFields;
    }

    public void setScientificFields(String scientificFields) {
        this.scientificFields = scientificFields;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Boolean getReviewer() {
        return reviewer;
    }

    public void setReviewer(Boolean reviewer) {
        this.reviewer = reviewer;
    }

    public String getMagazines() {
        return magazines;
    }

    public void setMagazines(String magazines) {
        this.magazines = magazines;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getLinkExpiration() {
        return linkExpiration;
    }

    public void setLinkExpiration(Date linkExpiration) {
        this.linkExpiration = linkExpiration;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", title='" + title + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", scientificFields=" + scientificFields +
                ", role=" + role +
                ", reviewer=" + reviewer +
                ", magazines=" + magazines +
                ", active=" + active +
                ", link='" + link + '\'' +
                ", linkExpiration=" + linkExpiration +
                '}';
    }
}
