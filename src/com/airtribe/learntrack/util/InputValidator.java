package com.airtribe.learntrack.util;

import com.airtribe.learntrack.exception.InvalidInputException;

public class InputValidator {

    private InputValidator() { }

    public static int parseInt(String input) throws InvalidInputException {
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            throw new InvalidInputException("'" + input + "' is not a valid number.");
        }
    }

    public static String requireNonEmpty(String input, String fieldName) throws InvalidInputException {
        if (input == null || input.trim().isEmpty()) {
            throw new InvalidInputException(fieldName + " cannot be empty.");
        }
        return input.trim();
    }

    public static boolean isValidEmail(String email) {
        return email != null && email.contains("@") && email.indexOf('@') > 0
                && email.indexOf('.', email.indexOf('@')) > email.indexOf('@') + 1;
    }
}
