package com.upp.nc.nc.controllers;

import com.upp.nc.nc.dataServices.RoleService;
import com.upp.nc.nc.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class RoleController {

    @Autowired
    RoleService roleService;

    public ResponseEntity<Role> findByName(String name) {
        Role role = roleService.findByName(name);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }
}
