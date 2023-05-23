package com.bezkoder.springjwt.models;

import javax.persistence.*;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
@Table(name = "files")
public class FileDB {
    @Id

    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idd;



    @GeneratedValue
    @GenericGenerator(name = "uuid", strategy = "uuid2")

    private String id;

    private String name;

    private String type;

    @Lob
    @Column(name = "imagedata",length = 50000000)
    private byte[] data;




    public FileDB() {
    }

    public FileDB(String name, String type, byte[] data) {
        this.name = name;
        this.type = type;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

}
