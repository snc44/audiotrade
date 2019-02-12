package ru.sfedu.musicapp.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.musicapp.api.IDataSourceAPI;
import ru.sfedu.musicapp.api.MysqlAPI;

import java.util.ArrayList;
import java.util.Date;

public class Customer extends User {

    private static Logger log = LogManager.getLogger(Customer.class);

    ArrayList<Soundtrack> purchasedTracks = new ArrayList<>();

    public Customer(int id, String login, String email, String password) {
        super(id, login, email, password, UserType.CUSTOMER);
    }

    public Soundtrack findTrackByTag(Profile profile, Tag tag){
        return profile.getTrackByTag(tag);
    }

    public Soundtrack findTrackByCategory(Profile profile, Category category){
        return profile.getTrackByCategory(category);
    }

    public Payment buyTrack(Soundtrack track, Profile profile){
        purchasedTracks.add(track);
        ArrayList<User> users = new ArrayList<>();
        users.add(this);
        users.add(profile.getOwner());
        return new Payment(60,users,track);
    }

    @Override
    public void showCollection(){
        purchasedTracks.forEach((Soundtrack s) ->
                log.info("Title: " + s.getTitle() + " ,price: " + s.getPrice()));
    }

    public ArrayList<Soundtrack> findTracksByCategory(Category category){
        ArrayList<Soundtrack> tracks = new ArrayList<>();
        IDataSourceAPI db = new MysqlAPI();
        tracks.forEach((Soundtrack s) -> {
            try {
                s = db.getRecordById(category.getId(), BaseClasses.Categories);
                tracks.add(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return tracks;
    }

    public ArrayList<Soundtrack> findTracksByTags(ArrayList<Tag> tags){
        ArrayList<Soundtrack> tracks = new ArrayList<>();
        IDataSourceAPI db = new MysqlAPI();
        for(Tag t : tags){
            tracks.forEach((Soundtrack s) -> {
                try {
                    s = db.getRecordById(t.getId(), BaseClasses.TracksTags);
                    tracks.add(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        return tracks;
    }

}
