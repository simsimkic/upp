package com.upp.nc.nc.dataServices;

import com.upp.nc.nc.entities.Role;
import com.upp.nc.nc.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }
}
