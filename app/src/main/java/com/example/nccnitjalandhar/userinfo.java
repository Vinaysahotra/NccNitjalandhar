package com.example.nccnitjalandhar;


public class userinfo {
    String email;
    String username;
    String profilepic;
   private String status;
String id;
    public userinfo() {
    }


    public userinfo(String email, String username, String profilepic,String id,String status) {
        this.email = email;
        this.username = username;
        this.profilepic = profilepic;
        this.id=id;
        this.status=status;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilepic() {
        return profilepic;
    }
    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
}
