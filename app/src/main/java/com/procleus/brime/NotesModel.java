package com.procleus.brime;

/**
 * Created by syedaamir on 11-07-2016.
 */

public class NotesModel {
    private int id;
    private String title;
    private String desc;
    private String date;
    private String time;
    private String access_type;
    private int isDeleted;
    private String Label;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAccess_type() {
        return access_type;
    }

    public void setAccess_type(String access_type) {
        this.access_type = access_type;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public void setLable(String label) {
        this.Label=label;
    }
    public String getLabel(){
        return this.Label;
    }
}