package com.application.jrl_technical_test.Security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class ClientPasswordEncoder implements PasswordEncoder {

    private static final int STRENGTH = 10;

    private final PasswordEncoder encoder = new BCryptPasswordEncoder(STRENGTH);

    @Override
    public String encode(CharSequence rawPassword){
        return encoder.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword){
        return encoder.matches(rawPassword, encodedPassword);
    }
}
