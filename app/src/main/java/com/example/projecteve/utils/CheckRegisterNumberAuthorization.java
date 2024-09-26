package com.example.projecteve.utils;

public class CheckRegisterNumberAuthorization {

    // Private constructor to prevent instantiation
    private CheckRegisterNumberAuthorization() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static boolean checkRegisterNumberAuthorization(Integer userRegisterNumberAuthorization) {
        final Integer registerNumberAuthorization = 852741;
        return registerNumberAuthorization.equals(userRegisterNumberAuthorization);
    }
}
