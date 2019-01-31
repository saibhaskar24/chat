package com.example.bhaskar.chat;

public class Users {

    public String name;
    public String status;
    public String image;


    public Users() {}

    public Users(String name, String status, String status1) {
        this.name = name;
        this.status = status;
        this.status = status1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
