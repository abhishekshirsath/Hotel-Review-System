package com.example.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User {

    @Id
    private String userId;
    private String name;
    private String email;
    private String about;

    @Transient
    private List<Review> reviews = new ArrayList<>();

    public User(){

    }

    private User(UserBuilder builder){
        this.userId = builder.userId;
        this.name = builder.name;
        this.email = builder.email;
        this.about = builder.about;
    }

    public static UserBuilder builder(){
        return new UserBuilder();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public static class UserBuilder{
        private String userId;
        private String name;
        private String email;
        private String about;
        private List<Review> reviews = new ArrayList<>();

        public UserBuilder userId(String userId){
            this.userId = userId;
            return this;
        }
        public UserBuilder name(String name){
            this.name = name;
            return this;
        }
        public UserBuilder email(String email){
            this.email = email;
            return this;
        }
        public UserBuilder about(String about){
            this.about = about;
            return this;
        }
        public UserBuilder reviews(List<Review> reviews) {
            this.reviews = reviews;
            return this;
        }

        public User build(){
            return new User(this);
        }
    }
}
