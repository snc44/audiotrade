package ru.sfedu.musicapp.api;
import java.io.*;

import com.opencsv.CSVWriter;

import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.musicapp.Constants;
import ru.sfedu.musicapp.ListEntities.Accounts;
import ru.sfedu.musicapp.ListEntities.Payments;
import ru.sfedu.musicapp.ListEntities.Profiles;
import ru.sfedu.musicapp.ListEntities.Users;
import ru.sfedu.musicapp.utils.ConfigurationUtil;
import ru.sfedu.musicapp.models.*;

public class CsvAPI implements IDataSourceAPI {

    private static Logger log = LogManager.getLogger(CsvAPI.class);

    public boolean addRecord(Object obj){
        if(obj instanceof User){
            writeToFile(Constants.usersPathCSV, ((User) obj).getRecordForCSV());
            return true;
        }
        if(obj instanceof Account){
            writeToFile(Constants.accountsPathCSV, ((Account) obj).getRecordForCSV());
            return true;
        }
        if(obj instanceof Payment){
            writeToFile(Constants.paymentsPathCSV, ((Payment) obj).getRecordForCSV());
            return true;
        }
        if(obj instanceof Profile){
            writeToFile(Constants.profilesPathCSV, ((Profile) obj).getRecordForCSV());
            return true;
        }
        if(obj instanceof Soundtrack){
            writeToFile(Constants.soundtracksPathCSV, ((Soundtrack) obj).getRecordForCSV());
            return true;
        }
        return false;
    }

    @Override
    public void removeRecordById(int id, Class T) throws Exception {
        if(Users.class.isAssignableFrom(T)){
            Users users = new Users();
            users.setUsers(readCsvToBean(User.class));
            users.removeUser(id);
            log.info("Object with id: " + id + " removed!");
            File file = new File(Constants.usersPathCSV);
            file.delete();
            for(User u : users.getUsers()){
                addRecord(u);
            }
        }
        if(Profiles.class.isAssignableFrom(T)){
            Profiles profiles = new Profiles();
            profiles.setProfiles(readCsvToBean(Profile.class));
            profiles.removeProfile(id);
            log.info("Object with id: " + id + " removed!");
            File file = new File(Constants.profilesPathCSV);
            file.delete();
            for(Profile u : profiles.getProfiles()){
                addRecord(u);
            }
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
        if(obj instanceof Account){
            Account newObj = (Account)obj;
            Account oldObj = getRecordById(id, BaseClasses.Accounts);
            newObj.setId(oldObj.getId());
            removeRecordById(id, Accounts.class);
            addRecord(newObj);
            log.info("record updated");
        }
        if(obj instanceof Payment){
            Payment newObj = (Payment)obj;
            Payment oldObj = getRecordById(id, BaseClasses.Payments);
            newObj.setId(oldObj.getId());
            removeRecordById(id, Payments.class);
            addRecord(newObj);
            log.info("record updated");
        }
    }

    private void writeToFile(String filepath, String[] rec){
        try
        {
            CSVWriter writer = new CSVWriter(new FileWriter(filepath,true));
            String[] record = rec;
            writer.writeNext(record);
            writer.close();
        }
        catch (IOException e)
        {
            log.error("Can't write row in file!");
            e.printStackTrace();
        }
    }

    public <T> List readCsvToBean(Class T) throws IOException{
        String path = "";
        String[] fields = {};
        if(User.class.isAssignableFrom(T)){
            path = Constants.usersPathCSV;
            fields = User.csvFields;
        }
        if(Account.class.isAssignableFrom(T)){
            path = Constants.accountsPathCSV;
            fields = Account.csvFields;
        }
        if(Payment.class.isAssignableFrom(T)){
            path = Constants.paymentsPathCSV;
            fields = Payment.csvFields;
        }
        if(Profile.class.isAssignableFrom(T)){
            path = Constants.profilesPathCSV;
            fields = Profile.csvFields;
        }
        if(Soundtrack.class.isAssignableFrom(T)){
            path = Constants.soundtracksPathCSV;
            fields = Soundtrack.csvFields;
        }

        try(BufferedReader br = Files.newBufferedReader(Paths.get(path))){
            ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
            strategy.setType(T);
            strategy.setColumnMapping(fields);

            CsvToBean csvToBean = new CsvToBeanBuilder(br)
                    .withType(T)
                    .withMappingStrategy(strategy)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<T> objects = csvToBean.parse();
            log.info("csv file has been read successfully!");
            return objects;
        }
        catch (RuntimeException rE){
            log.error(rE.getMessage());
        }
        catch (Exception e){
            log.error(e.getMessage());
        }
        return null;
    }

    public <T> T getRecordById(int id, BaseClasses clazz) throws Exception {
        switch (clazz){
            case Users:
                Users users = new Users();
                users.setUsers(readCsvToBean(User.class));
                User fUser = users.getUsers().stream()
                        .filter(u -> id==u.getId())
                        .findAny()
                        .orElse(null);
                T foundUser = (T)fUser;
                if(foundUser!=null){
                    log.info("record with id " + id + " was found");
                    return foundUser;
                }
                log.info("record with id " + id + " not found!");
                break;
            case Payments:
                Payments payments = new Payments();
                payments.setPayments(readCsvToBean(Payment.class));
                Payment fPay = payments.getPayments().stream()
                        .filter(u -> id==u.getId())
                        .findAny()
                        .orElse(null);
                T foundPay = (T)fPay;
                if(foundPay!=null){
                    log.info("record with id " + id + " was found");
                    return foundPay;
                }
                log.info("record with id " + id + " not found!");
                break;
        }
        return null;
    }

    public <T> List validate(List<T> objs){
        if(objs.get(0) instanceof User){
            List<User> users = new ArrayList<>();
            for(T u : objs){
                User user = (User)u;
                users.add(user);
            }
            List<User> toRemove = new ArrayList<>();
            for(User u : users){
                if(u.getId()==0){
                    toRemove.add(u);
                }
                if(u.getUsertype()==null){
                    log.warn("\tplease check row with id " + u.getId()+"\n");
                }
            }
            users.removeAll(toRemove);
            return users;
        }
        if(objs.get(0) instanceof Profile){
            List<Profile> profiles = new ArrayList<>();
            for(T u : objs){
                Profile profile = (Profile)u;
                profiles.add(profile);
            }
            for(Profile u : profiles){
                if(u.getId()==0){
                    profiles.remove(u);
                }
                if(u.getHeaderImgSrc()==null){
                    log.warn("\tplease check row with id " + u.getId()+"\n");
                }
            }
            return profiles;
        }
        return objs;
    }

    @Override
    public <T> T readData(BaseClasses clazz) throws Exception {
        // similarly with others
        switch(clazz){
            case Users:
                Users userslist = new Users();
                userslist.setUsers((validate(readCsvToBean(User.class))));
                userslist.getUsers().forEach((User u) ->
                        log.info("id: " + u.getId() + ", login: " + u.getLogin() + ", type: " + u.getUsertype()));
                return (T) userslist.getUsers();
        }
        return null;
    }
}
