package com.example.baksteen_13.dinopackopening;

public class cards {
    private String userId;
    private String name;
    private String image;
    public cards(String userId, String name){
        this.userId = userId;
        this.name = name;
    }

    public String getUserId(){
        return userId;
    }
    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
