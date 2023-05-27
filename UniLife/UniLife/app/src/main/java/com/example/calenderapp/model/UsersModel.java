package com.example.calenderapp.model;

public class UsersModel {
    int userId;
    String imageUri, fName, lName, courseTitle, email, year, password;

    public UsersModel() {
    }

    public UsersModel(String imageUri, String fName, String lName, String courseTitle, String email, String year, String password) {
        this.imageUri = imageUri;
        this.fName = fName;
        this.lName = lName;
        this.courseTitle = courseTitle;
        this.email = email;
        this.year = year;
        this.password = password;
    }

    public UsersModel(int userId, String imageUri, String fName, String lName, String courseTitle, String email, String year, String password) {
        this.userId = userId;
        this.imageUri = imageUri;
        this.fName = fName;
        this.lName = lName;
        this.courseTitle = courseTitle;
        this.email = email;
        this.year = year;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
