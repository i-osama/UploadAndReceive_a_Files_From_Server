package com.example.uploadfilestoserver.ServerModel;

public class ServerModel {
    String name, info, img;

    public ServerModel() {
    }

    public ServerModel(String name, String info, String img) {
        this.name = name;
        this.info = info;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
