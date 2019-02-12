package ru.sfedu.musicapp.models;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvBindByPosition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Soundtrack {

    private static Logger log = LogManager.getLogger(Soundtrack.class);

    @CsvBindByPosition(position = 0)
    private int id;

    @CsvBindByPosition(position = 1)
    private String title;

    @CsvBindByPosition(position = 2)
    private String description;

    @CsvBindByPosition(position = 3)
    private String publicationDate;

    @CsvBindByPosition(position = 4)
    private double price;

    private String fileSrc;
    private String imgSrc;
    private Category category;

    private ArrayList<Tag> tags;

    public static String[] csvFields = {"id","title","description","publicationDate","price"};

    public Soundtrack(int id, String title, String fileSrc, String imgSrc, ArrayList<Tag> tags, String description, String publicationDate, double price, Category c){
        this.id = id;
        this.title = title;
        this.fileSrc = fileSrc;
        this.imgSrc = imgSrc;
        this.tags = tags;
        this.description = description;
        this.publicationDate = publicationDate;
        this.price = price;
        this.category = c;
    }

    public Soundtrack(int id, String title, String fileSrc, String imgSrc, ArrayList<Tag> tags, String description, String publicationDate, double price){
        this.id = id;
        this.title = title;
        this.fileSrc = fileSrc;
        this.imgSrc = imgSrc;
        this.tags = tags;
        this.description = description;
        this.publicationDate = publicationDate;
        this.price = price;
    }

    public Soundtrack(){}

    public int getId() {
        return id;
    }

    public Category getCategory() {return category;}

    public void setCategory(Category c){this.category = c;}

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFileSrc() {
        return fileSrc;
    }

    public void setFileSrc(String fileSrc) {
        this.fileSrc = fileSrc;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String[] getRecordForCSV(){
        return this.toString().split("/");
    }

    public ArrayList<Tag> getTags(){return this.tags;}

    @Override
    public String toString(){
        return this.id + "/" + this.title + "/" + this.description + "/" + this.publicationDate + "/" + this.price;
    }

}
