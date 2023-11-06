package com.sharif.ecomm.service.impl;

import com.sharif.ecomm.config.JwtProvider;
import com.sharif.ecomm.exception.UserException;
import com.sharif.ecomm.model.User;
import com.sharif.ecomm.repository.UserRepository;
import com.sharif.ecomm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public User findUserById(Long userId) throws UserException {
        Optional<User> user =
                this.userRepository.findById(userId);
        if (user.isPresent()) {
            return user.get();
        }
        throw new UserException("User doesnt exists");
    }

    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {
        String email = jwtProvider.getEmailFromTokenString(jwt);
        User user = userRepository.findByEmail(email);
        if (user ==null){
            throw new UserException("User doesnt exists");
        }

        return user;
    }
}
