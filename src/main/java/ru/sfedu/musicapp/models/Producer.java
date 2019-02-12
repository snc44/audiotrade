package ru.sfedu.musicapp.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name="owner")
public class Producer extends User {

    private static Logger log = LogManager.getLogger(Producer.class);

    private Profile profile;

    public Producer(int id, String login, String email, String password) {
        super(id, login, email, password, UserType.PRODUCER);
    }

    public Producer(){}

    @Attribute(name="id")
    public int getId(){ return super.getId(); }

    @Attribute(name="id")
    public void setId(int id){ super.setId(id); }

    public Profile createProfile(String screenName){
        if(this.profile != null){
            log.info("Profile exist!");
        }
        else{
            Profile newProfile = new Profile();
            newProfile.setScreenName(screenName);
            newProfile.setOwner(this);
            this.profile = newProfile;
        }
        return this.profile;
    }

    public Profile getProfile(){
        return this.profile;
    }

    @Override
    public void showCollection(){
        profile.getTracks().forEach((Soundtrack t) ->
                log.info("title: " + t.getTitle() + ", price: " + t.getPrice()));
    }

}
