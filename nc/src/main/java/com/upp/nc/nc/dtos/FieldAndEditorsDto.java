package com.upp.nc.nc.dtos;

import com.upp.nc.nc.entities.User;

import java.util.ArrayList;
import java.util.List;

public class FieldAndEditorsDto {

    private String scientificField;
    private List<User> editors;

    public FieldAndEditorsDto() {
        this.editors = new ArrayList<>();
    }

    public FieldAndEditorsDto(String scientificField, List<User> editors) {
        this.scientificField = scientificField;
        this.editors = editors;
    }

    public String getScientificField() {
        return scientificField;
    }

    public void setScientificField(String scientificField) {
        this.scientificField = scientificField;
    }

    public List<User> getEditors() {
        return editors;
    }

    public void setEditors(List<User> editors) {
        this.editors = editors;
    }

    public void addEditor(User editor) {
        this.editors.add(editor);
    }

    @Override
    public String toString() {
        return "FieldAndEditorsDto{" +
                "scientificField='" + scientificField + '\'' +
                ", editors='" + editors + '\'' +
                '}';
    }
}
