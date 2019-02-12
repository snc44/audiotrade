package ru.sfedu.musicapp.models;

public class Tag {

    private int id;
    private String tagname;

    public Tag(int id, String tagname){
        this.setId(id);
        this.setTagname(tagname);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTagname() {
        return tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
    }

}
