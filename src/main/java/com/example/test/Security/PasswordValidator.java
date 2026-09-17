package com.example.test.Security;

import java.util.regex.Pattern;

/**
 * Utility class for enforcing enterprise password complexity constraints (NIST SP 800-63B / OWASP).
 */
public final class PasswordValidator {

    // Minimum 8 characters, at least one uppercase letter, one lowercase letter, one digit, and one special character
    private static final String PASSWORD_REGEX =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$";

    private static final Pattern PATTERN = Pattern.compile(PASSWORD_REGEX);

    private PasswordValidator() {}

    /**
     * Validates that the provided raw password meets security complexity requirements.
     *
     * @param rawPassword the plain-text password to validate
     * @throws IllegalArgumentException if the password does not meet complexity criteria
     */
    public static void validatePassword(String rawPassword) {
        if (rawPassword == null || !PATTERN.matcher(rawPassword).matches()) {
            throw new IllegalArgumentException(
                    "Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one number, and one special character."
            );
        }
    }
}
