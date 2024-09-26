package com.example.projecteve.utils;

public class CheckRegisterNumberAuthorization {

    // Constant for the registered number
    private static final int REGISTER_NUMBER_AUTHORIZATION = 852741;

    // Private constructor to prevent instantiation
    private CheckRegisterNumberAuthorization() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static boolean checkRegisterNumberAuthorization(Integer userRegisterNumberAuthorization) {
        return REGISTER_NUMBER_AUTHORIZATION == userRegisterNumberAuthorization;
    }
}
