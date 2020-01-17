package com.upp.nc.nc.dtos;

public class EditorsAndReviewersDto {

    private String editors;
    private String reviewers;

    public EditorsAndReviewersDto() {}

    public EditorsAndReviewersDto(String editors, String reviewers) {
        this.editors = editors;
        this.reviewers = reviewers;
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
        return "EditorsAndReviewersDto{" +
                "editors='" + editors + '\'' +
                ", reviewers='" + reviewers + '\'' +
                '}';
    }
}
