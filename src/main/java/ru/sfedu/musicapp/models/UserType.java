package ru.sfedu.musicapp.models;

public enum UserType {
    CUSTOMER("Customer"),PRODUCER("Producer");
    private String userRole;

    UserType(String role){
        userRole = role;
    }

    public String getUserRole(){
        return this.userRole;
    }
}
