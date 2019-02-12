package ru.sfedu.musicapp.models;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvBindByPosition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.FileWriter;
import java.io.IOException;

@Root(name="Account")
public class Account {

    private static Logger log = LogManager.getLogger(Account.class);

    @CsvBindByPosition(position = 0)
    private int id;

    @CsvBindByPosition(position = 1)
    private String payType;

    @CsvBindByPosition(position = 2)
    private String cardNumber;

    @CsvBindByPosition(position = 3)
    private double score = 0;

    @CsvBindByPosition(position = 4)
    private int ownerId;

    public static String[] csvFields = {"id","payType","cardNumber","score","ownerId"};

    public Account(int id, PayType payType, String cardNumber, int ownerId){
        this.id = id;
        this.payType = payType.getTitle();
        this.cardNumber = cardNumber;
        this.ownerId = ownerId;
    }

    public Account(){}

    @Attribute(name="id")
    public int getId() {
        return id;
    }

    @Attribute(name="id")
    public void setId(int id) {
        this.id = id;
    }

    @Attribute(name="ownerId")
    public int getOwnerId(){return ownerId;}

    @Attribute(name="ownerId")
    public void setOwnerId(int ownerId){this.ownerId = ownerId;}

    @Element(name="type")
    public String getPayType() {
        return this.payType;
    }

    @Element(name="type")
    public void setPayType(String payType) { this.payType = payType; }

    @Element(name="score")
    public double getScore(){
        return this.score;
    }

    @Element(name="score")
    public void setScore(double score){ this.score = score; }

    public void topUpBalance(double moneyCount){
        this.score += moneyCount;
    }

    public void writeOffBalance(double moneyCount){
        this.score -= moneyCount;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String[] getRecordForCSV(){
        return this.toString().split("/");
    }

    @Override
    public String toString(){
        return this.id + "/" + this.payType + "/" + this.cardNumber + "/" + this.score;
    }
}
