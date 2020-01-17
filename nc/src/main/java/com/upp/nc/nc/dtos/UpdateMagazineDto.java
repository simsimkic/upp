package com.upp.nc.nc.dtos;

import java.io.Serializable;

public class UpdateMagazineDto implements Serializable {

    String title;
    String payment_method;

    public UpdateMagazineDto() {}

    public UpdateMagazineDto(String title, String payment_method) {
        this.title = title;
        this.payment_method = payment_method;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    @Override
    public String toString() {
        return "UpdateMagazineDto{" +
                "title='" + title + '\'' +
                ", payment_method='" + payment_method + '\'' +
                '}';
    }
}
