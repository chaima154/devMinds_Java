package tn.devMinds.controllers;

public class PasswordComplexityCalculator {

    public static int calculateComplexity(String password) {
        int complexity = 0;

        // Vérifier la longueur du mot de passe
        if (password.length() >= 8) {
            complexity++;
        }

        // Vérifier la présence de lettres majuscules
        if (password.matches(".*[A-Z].*")) {
            complexity++;
        }

        // Vérifier la présence de lettres minuscules
        if (password.matches(".*[a-z].*")) {
            complexity++;
        }

        // Vérifier la présence de chiffres
        if (password.matches(".*\\d.*")) {
            complexity++;
        }

        // Vérifier la présence de caractères spéciaux
        if (password.matches(".*[!@#$%^&*()-+=_].*")) {
            complexity++;
        }

        return complexity;
    }
}

