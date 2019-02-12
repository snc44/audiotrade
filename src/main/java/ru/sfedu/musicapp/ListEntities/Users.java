package ru.sfedu.musicapp.ListEntities;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import ru.sfedu.musicapp.models.User;

import java.util.List;

@Root(name="Users")
public class Users {

    @ElementList(inline=true)
    private List<User> users;

    public List<User> getUsers(){
        return this.users;
    }

    public void setUsers(List<User> users){
        this.users = users;
    }

    public void addUser(User user){
        this.users.add(user);
    }

    public void removeUser(int id){
        User userForDel = users.stream()
                .filter(u -> id==u.getId())
                .findAny()
                .orElse(null);
        users.remove(userForDel);
    }
}
