package tn.devMinds.controllers;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;

public class GoogleAuthenticatorUtil {

    private static final GoogleAuthenticator gAuth = new GoogleAuthenticator();

    public static String generateSecretKey() {
        GoogleAuthenticatorKey key = gAuth.createCredentials();
        return key.getKey();
    }

    public static boolean isCodeValid(String secretKey, int userCode) {
        return gAuth.authorize(secretKey, userCode);
    }
}

