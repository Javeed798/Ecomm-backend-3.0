package com.sharif.ecomm.service;

import com.sharif.ecomm.exception.UserException;
import com.sharif.ecomm.model.User;
import jdk.jshell.spi.ExecutionControl;


public interface UserService {

    User findUserById(Long userId) throws UserException;

    User findUserProfileByJwt(String jwt) throws UserException;
}
