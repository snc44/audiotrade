package ru.sfedu.musicapp.ListEntities;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import ru.sfedu.musicapp.models.Profile;

import java.util.List;

@Root(name="Profiles")
public class Profiles {

    @ElementList(inline=true)
    private List<Profile> profiles;

    public List<Profile> getProfiles(){
        return this.profiles;
    }

    public void setProfiles(List<Profile> profiles){
        this.profiles = profiles;
    }

    public void addProfile(Profile profile){
        this.profiles.add(profile);
    }

    public void removeProfile(int id){
        Profile profForDel = profiles.stream()
                .filter(p -> id==p.getId())
                .findAny()
                .orElse(null);
        profiles.remove(profForDel);
    }
}
