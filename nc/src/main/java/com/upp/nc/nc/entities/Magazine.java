package com.upp.nc.nc.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Magazine {

    @Id
    private String issn;
    private String title;
    private String scientificFields;
    private String chiefEditor;
    private String editors;
    private String reviewers;
    private Boolean active;
    private String paymentType;

    public Magazine() {
        this.active = false;
    }

    public Magazine(String issn, String title, String scientificFields, String chiefEditor, String editors, String reviewers, Boolean active, String paymentType) {
        this.issn = issn;
        this.title = title;
        this.scientificFields = scientificFields;
        this.chiefEditor = chiefEditor;
        this.editors = editors;
        this.reviewers = reviewers;
        this.active = active;
        this.paymentType = paymentType;
    }

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getScientificFields() {
        return scientificFields;
    }

    public void setScientificFields(String scientificFields) {
        this.scientificFields = scientificFields;
    }

    public String getChiefEditor() {
        return chiefEditor;
    }

    public void setChiefEditor(String chiefEditor) {
        this.chiefEditor = chiefEditor;
    }

    public String getEditors() {
        return editors;
    }

    public void setEditors(String editors) {
        this.editors = editors;
    }

    public String getReviewers() {
        return reviewers;
    }

    public void setReviewers(String reviewers) {
        this.reviewers = reviewers;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    @Override
    public String toString() {
        return "Magazine{" +
                "issn='" + issn + '\'' +
                ", title='" + title + '\'' +
                ", scientificFields='" + scientificFields + '\'' +
                ", chiefEditor='" + chiefEditor + '\'' +
                ", editors='" + editors + '\'' +
                ", reviewers='" + reviewers + '\'' +
                ", active=" + active +
                ", paymentType=" + paymentType +
                '}';
    }
}
