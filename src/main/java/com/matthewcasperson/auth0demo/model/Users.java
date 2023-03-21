package com.matthewcasperson.auth0demo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name="user_name")
    private String userName;
    @Column(name="email")
    private String email;

    public Users(String userName, String email){
        this.userName = userName;
        this.email = email;
    }

    public int getId(){
        return this.id;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public String getEmail(){
        return this.email;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
}
