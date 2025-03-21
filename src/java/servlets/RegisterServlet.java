package servlets;

/*
the servlets the accept data from the JS file
i used the jakarta package, in case you are using javax, edit the imports
 */
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.BufferedReader;
import models.UserInfo;

public class RegisterServlet extends HttpServlet {

    private final double ALLOWED_LAT = 0.0; // set to desired latitude
    private final double ALLOWED_LON = 0.0; // set to desired longitude
    private final double ALLOWED_RAD = 0.0; // set to desired radius in KMs

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line;

        // read the data from the JSON file
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        Gson gson = new Gson();

        try {
            UserInfo userInfo = gson.fromJson(sb.toString(), UserInfo.class);

            if (userInfo == null) {
                System.out.println("No data found here.");
                return;
            }

            double longitude = userInfo.getLongitude();
            double latitude = userInfo.getLatitude();
            String email = userInfo.getEmail();
            String name = userInfo.getName();
            String password = userInfo.getPassword();
            String role = userInfo.getRole();

            boolean allowed = isWithinRadius(latitude, longitude, ALLOWED_LAT, ALLOWED_LON, ALLOWED_RAD);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            if (allowed) {
                response.getWriter().write("{\"canLogin\": " + allowed + "}");
            } else {
                response.getWriter().write("{\"canLogin\": " + allowed + "}");
            }
            /*
            or instead of sending the data back to the JS file, just redirect using RequestDispatcher
                 
            RequestDispatcher disp = request.getRequestDispatcher("login_success.jsp");
            disp.forward(request, response);
            
            get rid of the response.getWriter() to implement this because they conflict
            
            see the JS file for an alternative
            
            reminder: set attribute using the variables retrieved from the 
             */
        } catch (JsonSyntaxException | IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /*
    the method that calculates if a user is within the radius to log in
     */
    private boolean isWithinRadius(double lat1, double lon1, double lat2, double lon2, double radius) {
        final int EARTH_RADIUS = 6371;

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS * c;

        return distance <= radius;
    }
}
