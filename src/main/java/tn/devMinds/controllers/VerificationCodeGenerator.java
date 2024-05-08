package tn.devMinds.controllers;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;

public class VerificationCodeGenerator {

    public static String generateVerificationCode(String userEmail) {
        GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();
        GoogleAuthenticatorKey key = googleAuthenticator.createCredentials();

        // Générer un code de vérification basé sur la clé secrète
        int verificationCode = googleAuthenticator.getTotpPassword(key.getKey());

        // Stocker temporairement la clé secrète et le code de vérification côté serveur (par exemple, dans une base de données)
        String secretKey = key.getKey();
        storeSecretKey(userEmail, secretKey);

        return String.valueOf(verificationCode);
    }

    private static void storeSecretKey(String userEmail, String secretKey) {
        // Implémentez le stockage de la clé secrète dans votre base de données ou tout autre système de stockage.
    }

    public static String generateQRCode(String userEmail) {
        GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();
        GoogleAuthenticatorKey key = googleAuthenticator.createCredentials();

        // Stocker temporairement la clé secrète côté serveur (par exemple, dans une base de données)
        storeSecretKey(userEmail, key.getKey());

        // Générer un code QR pour la configuration de Google Authenticator
        return GoogleAuthenticatorQRGenerator.getOtpAuthURL("E-frank", userEmail, key);
    }

}

