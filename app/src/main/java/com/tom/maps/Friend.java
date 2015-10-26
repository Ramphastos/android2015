package com.tom.maps;

/**
 * Created by Administrator on 2015/10/26.
 */
public class Friend {
    String name;
    int avatarId;
    String phone;

    public Friend(String name, int avatarId, String phone) {
        this.name = name;
        this.avatarId = avatarId;
        this.phone = phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAvatarId(int avatarId) {
        this.avatarId = avatarId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {

        return name;
    }

    public int getAvatarId() {
        return avatarId;
    }

    public String getPhone() {
        return phone;
    }
}
