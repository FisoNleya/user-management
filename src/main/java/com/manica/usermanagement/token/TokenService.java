package com.manica.usermanagement.token;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService {


    private final TokenRepository tokenRepository;

    public Optional<Token> findOptionalToken(String tokenValue){
        return tokenRepository.findByToken(tokenValue);
    }

}
