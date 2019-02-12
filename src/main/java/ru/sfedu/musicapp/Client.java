package ru.sfedu.musicapp;

import org.apache.logging.log4j.*;
import ru.sfedu.musicapp.ListEntities.Profiles;
import ru.sfedu.musicapp.models.*;
import ru.sfedu.musicapp.api.*;

import java.io.IOException;
import java.util.ArrayList;

public class Client {
    private static Logger log = LogManager.getLogger(Client.class);
//  указывпю через
    public Client(){
        log.debug("ru.sfedu.musicapp.Client[0]: starting app...");
        log.info("1st arg - createUser/buyTrack/deleteProfile");
        log.info("2nd arg - data source type (xml,csv,database)");
        log.info("next args - input data\n");
    }
//
//    private static void logBasicSystemInfo(){
//        log.info("Launching the app..");
//        log.info(
//                "Operating System: " + System.getProperty("os.name") + " "
//                 + System.getProperty("os.version")
//        );
//        log.info("JRE: " + System.getProperty("java.version"));
//        log.info("Java Launched Form: " + System.getProperty("java.home"));
//        log.info("Java Class Path: " + System.getProperty("java.class.path"));
//        log.info("Library Path: " + System.getProperty("java.library.path"));
//        log.info("User Home Directory: " + System.getProperty("user.home"));
//        log.info("User Working Directory: " + System.getProperty("user.dir"));
//        log.info("test INFO logging");
//    }

    public static void main(String[] args) throws Exception {
//        logBasicSystemInfo();
        String method = args[0];
        String datasource = args[1];

        MysqlAPI db = new MysqlAPI();
        IDataSourceAPI xml = new XmlAPI();
        IDataSourceAPI csv = new CsvAPI();

        // customer and producer for tests
        Customer customer = new Customer(30,"testCustomer","dude1@gmail.com","21323ew");
        Producer producer = new Producer(40,"testProd","dude2@gmail.com","4e75yr");
        Profile profile = producer.createProfile("DefaultMusicMaker");
        profile.setOwner(producer);
        profile.setDescription("sample description");
        profile.setHeaderImgSrc("defaultimage.jpg");
        Soundtrack track;
        Payment payment;

        switch(method){
            case "createUser":
                int userId = Integer.parseInt(args[2]);
                String login = args[3];
                String email = args[4];
                String password = args[5];
                User user = new User();
                user.setId(userId);
                user.setLogin(login);
                user.setEmail(email);
                user.setPassword(password);
                user.setUsertype(args[6]);
                switch(datasource){
                    case "database":
                        db.addRecord(user);
                        break;
                    case "xml":
                        xml.addRecord(user);
                        break;
                    case "csv":
                        csv.addRecord(user);
                        break;
                }
                break;
            case "buyTrack":
                switch(datasource){
                    case "database":
                        db.addRecord(customer);
                        db.addRecord(producer);
                        db.addRecord(profile);
                        String searchString = args[3];

                        switch(args[2]){
                            case "category":
                                int categId = db.getRecordIdByName(searchString, BaseClasses.Categories);
                                track = db.getRecordById(categId, BaseClasses.Categories);
                                payment = customer.buyTrack(track, profile);
                                db.addRecord(payment);
                                break;
                            case "tags":
                                int tagId = db.getRecordIdByName(searchString, BaseClasses.TracksTags);
                                track = db.getRecordById(tagId,BaseClasses.TracksTags);
                                payment = customer.buyTrack(track, profile);
                                db.addRecord(payment);
                                break;
                        }
                        break;
                    case "xml":
                        searchString = args[3];
                        xml.addRecord(customer);
                        xml.addRecord(producer);
                        xml.addRecord(profile);

                        switch(args[2]){
                            case "category":
                                int categId = db.getRecordIdByName(searchString, BaseClasses.Categories);
                                track = db.getRecordById(categId, BaseClasses.Categories);
                                payment = customer.buyTrack(track, profile);
                                xml.addRecord(payment);
                                xml.addRecord(track);
                                break;
                            case "tags":
                                int tagId = db.getRecordIdByName(searchString, BaseClasses.TracksTags);
                                track = db.getRecordById(tagId,BaseClasses.TracksTags);
                                payment = customer.buyTrack(track, profile);
                                xml.addRecord(payment);
                                xml.addRecord(track);
                                break;
                        }
                        break;
                    case "csv":
                        searchString = args[3];
                        csv.addRecord(customer);
                        csv.addRecord(producer);
                        csv.addRecord(profile);
                        switch(args[2]){
                            case "category":
                                int categId = db.getRecordIdByName(searchString, BaseClasses.Categories);
                                track = db.getRecordById(categId, BaseClasses.Categories);
                                payment = customer.buyTrack(track, profile);
                                csv.addRecord(payment);
                                csv.addRecord(track);
                                break;
                            case "tags":
                                int tagId = db.getRecordIdByName(searchString, BaseClasses.TracksTags);
                                track = db.getRecordById(tagId,BaseClasses.TracksTags);
                                payment = customer.buyTrack(track, profile);
                                csv.addRecord(payment);
                                csv.addRecord(track);
                                break;
                        }
                        break;
                }
                break;
            case "deleteProfile":
                String strId = args[2];
                int id = Integer.parseInt(strId);
                switch(datasource){
                    case "database":
                        db.removeRecordById(id,Profile.class);
                        break;
                    case "xml":
                        xml.removeRecordById(id,Profiles.class);
                        break;
                    case "csv":
                        csv.removeRecordById(id,Profiles.class);
                        break;
                }
                break;
        }
//
//
//        customer.showCollection();
    }
}
