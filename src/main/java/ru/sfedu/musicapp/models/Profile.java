package ru.sfedu.musicapp.models;

import com.opencsv.bean.CsvBindByPosition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import ru.sfedu.musicapp.api.IDataSourceAPI;
import ru.sfedu.musicapp.api.MysqlAPI;

import java.util.ArrayList;
import java.util.List;

@Root(name="profile")
public class Profile {

    private static Logger log = LogManager.getLogger(Profile.class);

    @CsvBindByPosition(position = 0)
    private int id;

    @CsvBindByPosition(position = 1)
    private String screenName;

    @CsvBindByPosition(position = 2)
    private String description;

    private List<Soundtrack> tracks = new ArrayList<>();

    private ArrayList<SocialNetwork> socialLinks;
    private Producer owner;

    @CsvBindByPosition(position = 3)
    private String headerImgSrc;

    public static String[] csvFields = {"id","screenName","description","headerImgSrc"};

    public Profile(int id, String screenName, String description, Producer owner){
        this.id = id;
        this.screenName = screenName;
        this.description = description;
        this.owner = owner;
    }

    public Profile(){}

    @Attribute(name="id")
    public int getId() {
        return id;
    }

    @Attribute(name="id")
    public void setId(int id) {
        this.id = id;
    }

    @Element(name="headerImgSrc")
    public String getHeaderImgSrc() {
        return headerImgSrc;
    }

    @Element(name="headerImgSrc")
    public void setHeaderImgSrc(String headerImgSrc) {
        this.headerImgSrc = headerImgSrc;
    }

    @Element(name="description")
    public String getDescription() {
        return description;
    }

    @Element(name="description")
    public void setDescription(String description) {
        this.description = description;
    }

    @Element(name="screenName")
    public String getScreenName() {
        return screenName;
    }

    @Element(name="screenName")
    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public void uploadTrack(int id, String title, String fileSrc, String imgSrc, ArrayList<Tag> tags, String description, String publicationDate, double price, Category c){
        Soundtrack track = new Soundtrack(id, title, fileSrc, imgSrc, tags, description, publicationDate, price, c);
        tracks.add(track);
    }

    @Element(name="owner")
    public Producer getOwner(){
        return this.owner;
    }

    @Element(name="owner")
    public void setOwner(Producer producer){ this.owner = producer; }

    public int getOwnerId(){ return this.owner.getId(); }

    public Soundtrack getTrackByTag(Tag tag){
        for(Soundtrack s : tracks){
            if(s.getTags().contains(tag)){
                return s;
            }
            else{
                log.info("tracks with this tag not found!");
            }
        }
        return null;
    }

    public Soundtrack getTrackByCategory(Category category){
        for(Soundtrack s : tracks){
            if(s.getCategory().getTitle() == category.getTitle()){
                return s;
            }
            else{
                log.info("tracks with this tag not found!");
            }
        }
        return null;
    }

    public List<Soundtrack> getTracks(){ return this.tracks; }
    public void setTracks(List<Soundtrack> tracks){ this.tracks = tracks; }

    public String[] getRecordForCSV(){
        return this.toString().split("/");
    }

    public void showStats(){
//        IDataSourceAPI db = new MysqlAPI();

    }

    @Override
    public String toString(){
        return this.getId() + "/" + this.screenName + "/" + this.description + "/" + this.headerImgSrc;
    }

}
