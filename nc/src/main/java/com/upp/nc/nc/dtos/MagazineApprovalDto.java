package com.upp.nc.nc.dtos;


public class MagazineApprovalDto {

    String title;
    String issn;
    String scientificFields;
    String payment_method;
    String chief_editor;
    String editors;
    String reviewers;

    public MagazineApprovalDto() {}

    public MagazineApprovalDto(String title, String issn, String scientificFields, String payment_method,
                               String chief_editor, String editors, String reviewers) {
        this.title = title;
        this.issn = issn;
        this.scientificFields = scientificFields;
        this.payment_method = payment_method;
        this.chief_editor = chief_editor;
        this.editors = editors;
        this.reviewers = reviewers;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public String getScientificFields() {
        return scientificFields;
    }

    public void setScientificFields(String scientificFields) {
        this.scientificFields = scientificFields;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getChief_editor() {
        return chief_editor;
    }

    public void setChief_editor(String chief_editor) {
        this.chief_editor = chief_editor;
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

    @Override
    public String toString() {
        return "MagazineApprovalDto{" +
                "title='" + title + '\'' +
                ", issn='" + issn + '\'' +
                ", scientificFields='" + scientificFields + '\'' +
                ", payment_method='" + payment_method + '\'' +
                ", chief_editor='" + chief_editor + '\'' +
                ", editors='" + editors + '\'' +
                ", reviewers='" + reviewers + '\'' +
                '}';
    }
}
