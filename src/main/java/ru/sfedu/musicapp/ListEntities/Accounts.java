package ru.sfedu.musicapp.ListEntities;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import ru.sfedu.musicapp.models.Account;

import java.util.List;

@Root(name="Accounts")
public class Accounts {

    @ElementList(inline=true)
    private List<Account> accounts;

    public List<Account> getAccounts(){
        return this.accounts;
    }

    public void setAccounts(List<Account> accounts){
        this.accounts = accounts;
    }

    public void addAccount(Account account){
        this.accounts.add(account);
    }

    public void removeAccount(int id){
        Account accForDel = accounts.stream()
                .filter(a -> id==a.getId())
                .findAny()
                .orElse(null);
        accounts.remove(accForDel);
    }
}
