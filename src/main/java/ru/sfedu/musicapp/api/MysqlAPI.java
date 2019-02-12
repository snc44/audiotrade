package ru.sfedu.musicapp.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.musicapp.Constants;
import ru.sfedu.musicapp.utils.ConfigurationUtil;
import ru.sfedu.musicapp.models.*;

import java.io.IOException;
import java.sql.*;

public class MysqlAPI implements IDataSourceAPI {

    private static Logger log = LogManager.getLogger(MysqlAPI.class);

    @Override
    public boolean addRecord(Object obj) {
        if(obj instanceof User){
            User user = (User)obj;
            String query = "INSERT INTO Users(login,email,type,password) " +
                    "VALUES ('" + user.getLogin() + "','" +
                    user.getEmail() + "','" + user.getUsertype() + "','" +
                    user.getPassword() + "')";
            executeQuery(query);
            return true;
        }
        if(obj instanceof Profile){
            Profile profile = (Profile)obj;
            String query = "INSERT INTO `Profiles`(screenName,description,headerImageSrc,owner_id) " +
                    "VALUES ('" + profile.getScreenName() + "','" +
                    profile.getDescription() + "','" + profile.getHeaderImgSrc() + "','" +
                    profile.getOwner().getId() + "')";
            executeQuery(query);
            return true;
        }
        if(obj instanceof Category){
            Category category = (Category) obj;
            String query = "INSERT INTO Categories (title) " +
                    "VALUES ('" + category.getTitle() + "')";
            executeQuery(query);
            return true;
        }
        if(obj instanceof Payment){
            Payment payment = (Payment) obj;
            String query = "INSERT INTO Payments (payDate,track_id,customer_id,producer_id) " +
                    "VALUES ('" + payment.getPayDate() + "'," + payment.getTrackId() + "," +
                    payment.getUsers().get(0).getId() + "," +
                    payment.getUsers().get(1).getId() + ")";
            executeQuery(query);
            return true;
        }
        return false;
    }

    @Override
    public void removeRecordById(int id, Class T) throws Exception {
        if(User.class.isAssignableFrom(T)){
            String query = "DELETE FROM Users WHERE id=" + id;
            executeQuery(query);
            log.info("record was removed from database");
        }
        if(Profile.class.isAssignableFrom(T)){
            String query = "DELETE FROM `Profiles` WHERE id=" + id;
            executeQuery(query);
            log.info("record was removed from database");
        }
    }

    @Override
    public void updateRecord(int id, Object obj) {

    }

    private Connection getConnection() throws IOException, SQLException {
        String dbUrl = Constants.dbUrl;
        String dbUser = Constants.dbUser;
        String dbPassword = Constants.dbPassword;
        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    private void executeQuery(String query){
        try (Connection conn = getConnection()) {
            Statement statement = conn.createStatement();
            int rows = statement.executeUpdate(query);
            log.info(rows + " rows was modified");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T> T getRecordById(int id, BaseClasses clazz) throws Exception{
        try (Connection conn = getConnection()) {
            Statement statement = conn.createStatement();
            switch (clazz){
                case Users:
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM Users WHERE id="+id);
                    while(resultSet.next()){
                        int userId = resultSet.getInt("id");
                        String login = resultSet.getString("login");
                        String email = resultSet.getString("email");
                        String type = resultSet.getString("type");
                        String password = resultSet.getString("password");
                        User user = new User();
                        user.setId(userId);
                        user.setLogin(login);
                        user.setUsertype(type);
                        user.setPassword(password);
                        T us = (T)user;
                        return us;
                    }
                    break;
                case TracksTags:
                    ResultSet resultTag = statement.executeQuery("SELECT track_id FROM TracksTags WHERE tag_id="+id);
                    while(resultTag.next()){
                        int trackId = resultTag.getInt("track_id");
                        Soundtrack track = new Soundtrack();
                        track.setId(trackId);
                        T tr = (T)track;
                        return tr;
                    }
                    break;
                case Categories:
                    ResultSet resultCateg = statement.executeQuery("SELECT id FROM Soundtracks WHERE category_id="+id);
                    while(resultCateg.next()){
                        int trackId = resultCateg.getInt("id");
                        Soundtrack track = new Soundtrack();
                        track.setId(trackId);
                        T tr = (T)track;
                        return tr;
                    }
                    break;
            }
        }
        return null;
    }

    public int getRecordIdByName(String name, BaseClasses clazz) throws Exception{
        try (Connection conn = getConnection()) {
            Statement statement = conn.createStatement();
            switch (clazz){
                case TracksTags:
                    ResultSet resultTag = statement.executeQuery("SELECT id FROM Tags WHERE title='"+name+"'");
                    while(resultTag.next()){
                        int tagId = resultTag.getInt("id");
                        return tagId;
                    }
                    break;
                case Categories:
                    ResultSet resultCateg = statement.executeQuery("SELECT id FROM Categories WHERE title='"+name+"'");
                    while(resultCateg.next()){
                        int categId = resultCateg.getInt("id");
                        return categId;
                    }
                    break;
            }
        }
        return 0;
    }

    @Override
    public <T> T readData(BaseClasses clazz) throws Exception {
        return null;
    }

}
