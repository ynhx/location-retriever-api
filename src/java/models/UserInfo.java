package models;

/*
this class is used to read and store data from the JS file
it's important in reading the data using Gson
 */
public class UserInfo {

    private double latitude;
    private double longitude;
    private String email;
    private String name;
    private String password;
    private String role;

    public UserInfo(double latitude, double longitude, String email, String name, String password, String role) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public UserInfo() {
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
