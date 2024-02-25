package com.manica.usermanagement.user;

import com.manica.usermanagement.config.JwtService;
import com.manica.usermanagement.exceptions.TokenAuthenticationException;
import com.manica.usermanagement.token.TokenService;
import com.manica.usermanagement.user.dtos.AuthenticationUser;
import com.manica.usermanagement.user.dtos.ValidUser;
import com.manica.usermanagement.user.dtos.ValidateTokenRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;
    private final TokenService tokenService;

    private  final  JwtService jwtService;

    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        // update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // save the new password
        userRepository.save(user);
    }


    public ValidUser validateToken(ValidateTokenRequest request) {


      final  String  userEmail = jwtService.extractUsername(request.accessToken());

        log.info("The token being validated "+ request.accessToken());
            if (userEmail != null ) {

                var userDetails = userRepository.findByEmail(userEmail)
                        .orElseThrow(()-> new UsernameNotFoundException("User not found : "+ userEmail));

                var isTokenValid = tokenService.findOptionalToken(request.accessToken())
                        .map(t -> !t.isExpired() && !t.isRevoked())
                        .orElse(false);
                if (jwtService.isTokenValid(request.accessToken(), userDetails) && Boolean.TRUE.equals(isTokenValid)){
                    log.info("Token validated : "+ userEmail);
                   return new ValidUser(userDetails.getFirstname(), userDetails.getLastname(), userDetails.getEmail(), userDetails.getRole());
                }
            }

        throw new TokenAuthenticationException("User token is invalid");

    }

    public AuthenticationUser findByUserName(String username){

        return userRepository.findByEmail(username)
                .map(user -> AuthenticationUser.builder()
                        .firstname(user.getFirstname())
                        .lastname(user.getLastname())
                        .role(user.getRole())
                        .email(user.getEmail())
                        .build())
                .orElseThrow(()-> new UsernameNotFoundException("User not found : "+username ));
    }


    public ValidUser findMe(HttpServletRequest request){

        final String authHeader = request.getHeader("Authorization");
        String  jwt = authHeader.substring(7);

        final  String  userEmail = jwtService.extractUsername(jwt);

        log.info("Checking me : "+ userEmail);

        return userRepository.findByEmail(userEmail)
                .map(this::map)
                .orElseThrow(()-> new UsernameNotFoundException("We dont know m"));
    }


    public ValidUser map(User userDetails){
        return new ValidUser(userDetails.getFirstname(), userDetails.getLastname(), userDetails.getEmail(), userDetails.getRole());
    }

}
