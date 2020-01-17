package com.upp.nc.nc.dataServices;

import com.upp.nc.nc.dtos.FieldAndEditorsDto;
import com.upp.nc.nc.dtos.LoginDto;
import com.upp.nc.nc.entities.ScientificField;
import com.upp.nc.nc.entities.User;
import com.upp.nc.nc.entities.VerificationToken;
import com.upp.nc.nc.repositories.ScientificFieldRepository;
import com.upp.nc.nc.repositories.UserRepository;
import com.upp.nc.nc.repositories.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    ScientificFieldRepository scientificFieldRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User saveUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(user);
    }

    public void delete(String username) {
        userRepository.deleteById(username);
    }

    public void createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }

    public VerificationToken getVerificationToken(String token) {
        return tokenRepository.findByToken(token);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User login(LoginDto dto) {
        User user = findByUsername(dto.getUsername());
        if (user != null && user.getActive()) {
            if (passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    public List<FieldAndEditorsDto> getEditors(String fields) {
        List<User> editors = userRepository.findByRole(3);
        List<FieldAndEditorsDto> result = new ArrayList<>();

//        if(!fields.contains(",")){
//            FieldAndEditorsDto dto = new FieldAndEditorsDto();
//            ScientificField field = scientificFieldRepository.findByCode(fields);
//            dto.setScientificField(field.getName());
//
//            for (User e : editors) {
//                if (e.getMagazines() == null && e.getScientificFields().contains(fields)) {
//                    dto.addEditor(e);
//                }
//            }
//            result.add(dto);
//        } else {


            for (String f : fields.split(",")) {
                FieldAndEditorsDto dto = new FieldAndEditorsDto();
                ScientificField field = scientificFieldRepository.findByCode(f);
                dto.setScientificField(field.getName());

                for (User e : editors) {
                    if (e.getMagazines() == null && e.getScientificFields().contains(f)) {
                        dto.addEditor(e);
                    }
                }

                result.add(dto);
            }
//        }
        return result;
    }

    public List<User> getReviewers(String fields) {
        return userRepository.findByReviewer(true);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
