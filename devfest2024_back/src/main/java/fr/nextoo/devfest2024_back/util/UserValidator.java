package fr.nextoo.devfest2024_back.util;

import fr.nextoo.devfest2024_back.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class UserValidator {

    public boolean validateUser(UserEntity userEntity) {
        if (userEntity.getPhone() == null || userEntity.getPhone().isEmpty()) {
            return false;
        }
        if (userEntity.getEmail().equals("")) {
            return false;
        }
        if (userEntity.getFirstname().length() < 3) {
            return false;
        }
        if (userEntity.getLastname().length() == 0) {
            return false;
        }
        return true;
    }
}
