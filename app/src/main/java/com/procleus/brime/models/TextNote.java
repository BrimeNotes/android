package com.procleus.brime.models;

import java.sql.Timestamp;

public class TextNote {
    public int id;
    public String note;
    public String title;
    public Timestamp created;
    public Timestamp edited;
    public int owner;

    public TextNote(int i, String n, String t, Timestamp c, Timestamp e, int o) {
        this.id = i;
        this.note = n;
        this.title = t;
        this.created = c;
        this.edited = e;
        this.owner = o;
    }
}
