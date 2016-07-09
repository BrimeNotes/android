package com.procleus.brime;

import java.sql.Timestamp;

/**
 * Created by mudit on 08/07/2016.
 */
public class textNote {
    public int id;
    public String note;
    public Timestamp created;
    public Timestamp edited;
    public int owner;

    public textNote(int i, String n, Timestamp c, Timestamp e, int o) {
        this.id = i;
        this.note = n;
        this.created = c;
        this.edited = e;
        this.owner = o;
    }
}
