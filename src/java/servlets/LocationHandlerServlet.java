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
import handlers.LocationHandler;
import java.io.BufferedReader;
import models.UserInfo;

public class LocationHandlerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        double allowedLatitude = 0.0; // set to desired latitude
        double allowedLongitude = 0.0; // set to desired longitude
        double allowedRadius = 0.0; // set to desired radius in KM

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

            double userLatitude = userInfo.getLatitude();
            double userLongitude = userInfo.getLongitude();
            String email = userInfo.getEmail();
            String name = userInfo.getName();
            String password = userInfo.getPassword();
            String role = userInfo.getRole();

            LocationHandler locationHandler = new LocationHandler(
                    userLatitude, userLongitude, allowedLatitude, allowedLongitude, allowedRadius
            );

            boolean allowed = locationHandler.isWithinRadius();

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
}
