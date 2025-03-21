package handlers;

public class LocationHandler {

    private double userLat;
    private double userLong;
    private double allowedLat;
    double allowedLong;
    private double allowedRad;

    public LocationHandler() {
        // default constructor
    }

    public LocationHandler(
            double userLat, double userLong, double allowedLat, double allowedLong, double allowedRad
    ) {
        this.userLat = userLat;
        this.userLong = userLong;
        this.allowedLat = allowedLat;
        this.allowedLong = allowedLong;
        this.allowedRad = allowedRad;
    }

    public double getUserLat() {
        return userLat;
    }

    public void setUserLat(double userLat) {
        this.userLat = userLat;
    }

    public double getUserLong() {
        return userLong;
    }

    public void setUserLong(double userLong) {
        this.userLong = userLong;
    }

    public double getAllowedLat() {
        return allowedLat;
    }

    public void setAllowedLat(double allowedLat) {
        this.allowedLat = allowedLat;
    }

    public double getAllowedLong() {
        return allowedLong;
    }

    public void setAllowedLong(double allowedLong) {
        this.allowedLong = allowedLong;
    }

    public double getAllowedRad() {
        return allowedRad;
    }

    public void setAllowedRad(double allowedRad) {
        this.allowedRad = allowedRad;
    }

    /*
    this method calculates if the user attempting to log in is within the required radius
     */
    public boolean checkLocation() {
        final int EARTH_RADIUS = 6371; // KM unit

        double latDistance = Math.toRadians(allowedLat - userLat);
        double lonDistance = Math.toRadians(allowedLong - userLong);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(Math.toRadians(userLat))
                * Math.cos(Math.toRadians(allowedLat)) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS * c;

        return distance <= allowedRad;
    }
}
