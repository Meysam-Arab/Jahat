package com.fardan7eghlim.jahat;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by Amir on 1/15/2017.
 */

public class ChatObj implements Serializable {
    private String id;
    private String unique_id;
    private String name;
    private String link;
    private String score;
    private String numberOFmember;
    private String description;
    private String type;
    private String created_at;
    private Bitmap avatar;
    private boolean isAuth;

    public ChatObj() {
    }

    public ChatObj(String id, String unique_id, String name, String link, String numberOFmember, String score, String description, String type, String created_at, String updated_at, Bitmap avatar,boolean isAuth) {
        this.id = id;
        this.unique_id = unique_id;
        this.name = name;
        this.link = link;
        this.score = score;
        this.description = description;
        this.type = type;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.numberOFmember=numberOFmember;
        this.avatar=avatar;
        this.isAuth=isAuth;
    }

    public boolean isAuth() {
        return isAuth;
    }

    public void setAuth(boolean auth) {
        isAuth = auth;
    }

    public Bitmap getAvatar() {
        return avatar;
    }

    public void setAvatar(Bitmap avatar) {
        this.avatar = avatar;
    }

    public String getNumberOFmember() {
        return numberOFmember;
    }

    public void setNumberOFmember(String numberOFmember) {
        this.numberOFmember = numberOFmember;
    }
    private String updated_at;

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

}
