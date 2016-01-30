package com.aindong.restoko.models;

import com.aindong.restoko.library.Model;

public class Table extends Model {
    public int id;
    public String name;
    public String status;

    public Table() {
        // Do nothing
    }

    public Table(int id, String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }
}
