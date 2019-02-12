package ru.sfedu.musicapp.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import ru.sfedu.musicapp.Constants;
import ru.sfedu.musicapp.utils.ConfigurationUtil;
import ru.sfedu.musicapp.models.BaseClasses;
import ru.sfedu.musicapp.models.Profile;
import ru.sfedu.musicapp.models.User;
import ru.sfedu.musicapp.ListEntities.Profiles;
import ru.sfedu.musicapp.ListEntities.Users;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class XmlAPI implements IDataSourceAPI {

    private File file;
    private static Logger log = LogManager.getLogger(XmlAPI.class);

    @Override
    public boolean addRecord(Object obj) throws IOException {
        if(obj instanceof User){
            User user = (User)obj;
            if(new File(Constants.usersPathXML).length() > 0){
                try{
                    Users users = readData(BaseClasses.Users);
                    users.addUser(user);
                    serializeToXML(users);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            else{
                Users firstUser = new Users();
                List<User> us = new ArrayList<>();
                us.add(user);
                firstUser.setUsers(us);
                serializeToXML(firstUser);
            }
            return true;
        }
        if(obj instanceof Profile){
            Profile profile = (Profile)obj;
            if(new File(Constants.profilesPathXML).length() > 0){
                try{
                    Profiles profiles = readData(BaseClasses.Profiles);
                    profiles.addProfile(profile);
                    serializeToXML(profiles);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            else{
                Profiles firstProfile = new Profiles();
                List<Profile> pr = new ArrayList<>();
                pr.add(profile);
                firstProfile.setProfiles(pr);
                serializeToXML(firstProfile);
            }
            return true;
        }
        return false;
    }

    public void xmlWrite(Object obj, String xmlpath) throws IOException {
        Path path = Paths.get(xmlpath);
        if (!Files.exists(path, LinkOption.NOFOLLOW_LINKS))
            Files.createFile(path);
        try (FileWriter writer = new FileWriter(xmlpath, false)) {
            Serializer serializer = new Persister();
            try {
                serializer.write(obj, writer);
                String xml = writer.toString();
                log.info("xml file was changed");
            } catch (Exception e) {
                log.error(e.getMessage());
                log.error("cant write to xml file");
            }
        }
    }

    @Override
    public void removeRecordById(int id, Class T) throws Exception {
        if(Users.class.isAssignableFrom(T)){
            Users users = readData(BaseClasses.Users);
            users.removeUser(id);
            File file = new File(Constants.usersPathXML);
            file.delete();
            serializeToXML(users);
        }
        if(Profiles.class.isAssignableFrom(T)){
            Profiles profiles = readData(BaseClasses.Profiles);
            profiles.removeProfile(id);
            File file = new File(Constants.profilesPathXML);
            file.delete();
            serializeToXML(profiles);
            log.info("profile with id: " + id + " removed!");
        }
    }

    @Override
    public void updateRecord(int id, Object obj) throws Exception {
        if(obj instanceof User){
            User userNew = (User)obj;
            User userOld = getRecordById(id, BaseClasses.Users);
            userNew.setId(userOld.getId());
            removeRecordById(id, Users.class);
            addRecord(userNew);
            log.info("record updated");
        }
        if(obj instanceof Profile){
            Profile profileNew = (Profile)obj;
            Profile profileOld = getRecordById(id, BaseClasses.Profiles);
            profileNew.setId(profileOld.getId());
            removeRecordById(id, Profiles.class);
            addRecord(profileNew);
            log.info("record updated");
        }
    }

    public void serializeToXML(Object obj) throws IOException {
        if(obj instanceof Users){
            Users users = (Users)obj;
            xmlWrite(users, Constants.usersPathXML);
        }
        if(obj instanceof Profiles){
            Profiles profiles = (Profiles)obj;
            xmlWrite(profiles, Constants.profilesPathXML);
        }
    }

    @Override
    public <T> T getRecordById(int id, BaseClasses clazz) throws Exception {
        switch (clazz){
            case Users:
                Users users = readData(BaseClasses.Users);
                for(User user : users.getUsers()){
                    if(user.getId() == id){
                        T foundUser = (T) user;
                        return foundUser;
                    }
                }
                log.info("record with id " + id + " not found!");
                break;
            case Profiles:
                Profiles profiles = readData(BaseClasses.Profiles);
                for(Profile profile : profiles.getProfiles()){
                    if(profile.getId() == id){
                        T foundProfile = (T) profile;
                        return foundProfile;
                    }
                }
                log.info("record with id " + id + " not found!");
                break;
        }
        return null;
    }

    @Override
    public <T> T readData(BaseClasses clazz) throws Exception {
        Serializer serializer = new Persister();
        switch (clazz){
            case Users:
                file = new File(Constants.usersPathXML);
                try {
                    T users = (T) serializer.read(Users.class, file);
                    return users;
                } catch (XMLStreamException xmlE) {
                    log.error(xmlE.getMessage());
                    log.info(xmlE.getLocation());
                } catch (NumberFormatException numE){
                    log.error(numE.getMessage());
                    log.info("Element must contains number! Find string from ERROR and change it");
                } catch (Exception e){
                    log.error(e.getMessage());
                }
                break;
            case Profiles:
                file = new File(Constants.profilesPathXML);
                try {
                    T profiles = (T) serializer.read(Profiles.class, file);
                    return profiles;
                } catch (XMLStreamException xmlE) {
                    log.error(xmlE.getMessage());
                    log.info(xmlE.getLocation());
                } catch (NumberFormatException numE){
                    log.error(numE.getMessage());
                    log.info("Element must contains number! Find string from ERROR and change it");
                } catch (Exception e){
                    log.error(e.getMessage());
                }
                break;
        }
        return null;
    }
}
