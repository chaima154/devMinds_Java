package tn.devMinds.controllers;

import java.util.Properties;

public class EmailConfig {
    private static final String USERNAME = "zinelabidinefatma1@gmail.com";
    private static final String PASSWORD = "thgy vskk cwfe wgtj";

    public static Properties getProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        return props;
    }

    public static String getUsername() {
        return USERNAME;
    }

    public static String getPassword() {
        return PASSWORD;
    }
}

