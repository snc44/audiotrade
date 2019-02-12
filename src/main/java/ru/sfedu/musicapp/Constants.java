package ru.sfedu.musicapp;

import ru.sfedu.musicapp.utils.ConfigurationUtil;

public class Constants {
    public static final String propertiesPath = "classes/musicapp.properties";
    public static final String usersPathXML = new ConfigurationUtil(propertiesPath).readConfig("ru.sfedu.musicapp.xmlPath.Users");
    public static final String accountsPathXML = new ConfigurationUtil(propertiesPath).readConfig("ru.sfedu.musicapp.xmlPath.Accounts");
    public static final String profilesPathXML = new ConfigurationUtil(propertiesPath).readConfig("ru.sfedu.musicapp.xmlPath.Profiles");
    public static final String paymentsPathXML = new ConfigurationUtil(propertiesPath).readConfig("ru.sfedu.musicapp.xmlPath.Payments");

    public static final String usersPathCSV = new ConfigurationUtil(propertiesPath).readConfig("ru.sfedu.musicapp.csvpath.Users");
    public static final String accountsPathCSV = new ConfigurationUtil(propertiesPath).readConfig("ru.sfedu.musicapp.csvpath.Accounts");
    public static final String paymentsPathCSV = new ConfigurationUtil(propertiesPath).readConfig("ru.sfedu.musicapp.csvpath.Payments");
    public static final String profilesPathCSV = new ConfigurationUtil(propertiesPath).readConfig("ru.sfedu.musicapp.csvpath.Profiles");
    public static final String soundtracksPathCSV = new ConfigurationUtil(propertiesPath).readConfig("ru.sfedu.musicapp.csvpath.Soundtracks");

    public static final String dbUser = new ConfigurationUtil(propertiesPath).readConfig("ru.sfedu.musicapp.dbUser");
    public static final String dbPassword = new ConfigurationUtil(propertiesPath).readConfig("ru.sfedu.musicapp.dbPassword");
    public static final String dbUrl = new ConfigurationUtil(propertiesPath).readConfig("ru.sfedu.musicapp.dbUrl");
}
