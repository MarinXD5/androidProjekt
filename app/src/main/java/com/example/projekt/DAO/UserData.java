package com.example.projekt.DAO;

public class UserData implements Comparable<UserData>{

    public UserData(){}

    public UserData(String email, int points){
        this.email = email;
        this.points = points;
    }

    private String email;
    private int points;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public int compareTo(UserData other) {
        return Integer.compare(this.points, other.points);
    }
}
