package com.sharif.ecomm.controller;

import com.sharif.ecomm.config.JwtProvider;
import com.sharif.ecomm.exception.UserException;
import com.sharif.ecomm.model.Cart;
import com.sharif.ecomm.model.User;
import com.sharif.ecomm.repository.UserRepository;
import com.sharif.ecomm.service.CartService;
import com.sharif.ecomm.service.CustomUserServiceImplementation;
import com.sharif.ecomm.utils.AuthResponse;
import com.sharif.ecomm.utils.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private CartService cartService;

    @Autowired
    private CustomUserServiceImplementation customUserDetails;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException {
        String email = user.getEmail();
        String password = user.getPassword();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        User isEmailExist = userRepository.findByEmail(email);
        if (isEmailExist != null) {
            throw new UserException("Email is already used with another account");
        }
        User createdUser = new User();
        createdUser.setEmail(email);
        createdUser.setPassword(this.passwordEncoder.encode(password));
        createdUser.setFirstName(firstName);
        createdUser.setLastName(lastName);
        createdUser.setRole("user");
        User save = this.userRepository.save(createdUser);

//        create cart instantly when user is created
        Cart cart = cartService.createCart(save);

        Authentication authentication=new UsernamePasswordAuthenticationToken(save.getEmail(), save.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token=jwtProvider.generateToken(authentication);
        AuthResponse res =new AuthResponse(token,"Signup Successful");
        return new ResponseEntity<AuthResponse>(res, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> loginUserHandler(@RequestBody LoginRequest loginRequest){
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        Authentication authentication = authenticate(email,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token=jwtProvider.generateToken(authentication);
        AuthResponse res =new AuthResponse(token,"Login Successful");
        return new ResponseEntity<AuthResponse>(res,HttpStatus.ACCEPTED);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails=customUserDetails.loadUserByUsername(username);
        if(userDetails==null){
            throw new BadCredentialsException("Invalid username...");

        }if(!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid username...");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}
