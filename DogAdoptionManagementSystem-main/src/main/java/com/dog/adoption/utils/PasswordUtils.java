package com.dog.adoption.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordUtils {

    private static final Logger logger = LoggerFactory.getLogger(PasswordUtils.class);

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String hashPassword(String plainPassword) {
        logger.debug("Hashing password...");
        return passwordEncoder.encode(plainPassword);
    }

    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        logger.debug("Verifying password...");
        logger.debug("Entered Plain Password: {}", plainPassword);
        logger.debug("Encoded Plain Password: {}", passwordEncoder.encode(plainPassword));
        boolean matches = passwordEncoder.matches(plainPassword.trim(), hashedPassword.trim());
        if (!matches) {
            logger.warn("Password verification failed. Plain: {}, Hashed: {}", plainPassword, hashedPassword);
        }
        return matches;
    }
}

