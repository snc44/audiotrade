package ru.sfedu.musicapp.models;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvBindByPosition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Root(name="payment")
public class Payment {

    private static Logger log = LogManager.getLogger(Payment.class);

    @CsvBindByPosition(position = 0)
    private int id;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy HH:mm:ss");

    @CsvBindByPosition(position = 1)
    private String payDate;

    private long payDateMillis;

    @CsvBindByPosition(position = 2)
    private int trackId;
    @CsvBindByPosition(position = 3)
    private int customerId;
    @CsvBindByPosition(position = 4)
    private int producerId;

    private Soundtrack track;

    @ElementList(name="users")
    private ArrayList<User> users;

    public Payment(int id, ArrayList<User> users, Soundtrack track){
        Date date = new Date();
        this.id = id;
        this.users = users;
        this.customerId = users.get(0).getId();
        this.producerId = users.get(1).getId();
        this.track = track;
        this.trackId = track.getId();
        this.payDateMillis = date.getTime();
        this.payDate = dateFormat.format(payDateMillis);
    }

    public static String[] csvFields = {"id","payDate","trackId","customerId","producerId"};

    public Payment(){}

    @Attribute(name="id")
    public int getId() {
        return id;
    }

    @Attribute(name="id")
    public void setId(int id) {
        this.id = id;
    }

    @Element(name="customerId")
    public int getCustomerId(){return customerId;}

    @Element(name="producer")
    public int getProducerId(){return producerId;}

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public int getTrackId() {
        return track.getId();
    }

    public void setTrack(Soundtrack track) {
        this.track = track;
        this.trackId = track.getId();
    }

    @Attribute(name="date")
    public String getPayDate() {
        return dateFormat.format(payDateMillis);
    }

    @Attribute(name="date")
    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String[] getRecordForCSV(){
        return this.toString().split("/");
    }

    @Override
    public String toString(){
        return this.getId() + "/" + payDate + "/" + trackId + "/" + customerId + "/" + producerId;
    }

}
