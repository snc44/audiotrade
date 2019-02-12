package ru.sfedu.musicapp.models;

import com.opencsv.CSVWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="user")
public class User {

    private static Logger log = LogManager.getLogger(User.class);

    @CsvBindByPosition(position = 0)
    private int id;

    @CsvBindByPosition(position = 1)
    private String login;

    @CsvBindByPosition(position = 2)
    private String email;

    @CsvBindByPosition(position = 3)
    private String userType;

    ArrayList<Account> accounts;
    private String password;

    public static String[] csvFields = {"id","login","email","userType"};

    public User(int id, String login, String email, String password, UserType userType){
        this.setId(id);
        this.setLogin(login);
        this.setEmail(email);
        this.setPassword(password);
        this.userType = userType.getUserRole();
    }

    public User(String login, String email, String password, UserType userType){
        this.setLogin(login);
        this.setEmail(email);
        this.setPassword(password);
        this.userType = userType.getUserRole();
    }

    public User(int id, String login, String email, String password, String usertype){
            this.setId(id);
            this.setLogin(login);
            this.setEmail(email);
            this.setPassword(password);
            this.userType = usertype;
    }

    public User(){

    }

    @Attribute(name="id")
    public int getId() {
        return id;
    }

    @Attribute(name="id")
    public void setId(int id) {
        this.id = id;
    }

    @Element(name="login")
    public String getLogin() {
        return login;
    }

    @Element(name="login")
    public void setLogin(String login) {
        this.login = login;
    }

    @Element(name="email")
    public String getEmail() {
        return email;
    }

    @Element(name="email")
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Element(name="type")
    public String getUsertype(){
        return this.userType;
    }

    @Element(name="type")
    public void setUsertype(String usertype){
            this.userType = usertype;
    }

    public void createAccount(int id, PayType payType, String cardNumber){
        Account account = new Account(id, payType, cardNumber, this.id);
        accounts.add(account);
        log.info("Account (%s) with number - %d successfully added", account.getPayType(), account.getCardNumber());
    }

    public void showCollection(){

    }

    public String[] getRecordForCSV(){
        return this.toString().split("/");
    }

    @Override
    public String toString(){
        return this.getId() + "/" + this.getLogin() + "/" + this.getEmail() + "/" + this.getUsertype();
    }
}
