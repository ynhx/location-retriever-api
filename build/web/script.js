function validate(event) {
    event.preventDefault();

    // retrieve form details
    const email = document.getElementById("email").value;
    const name = document.getElementById("name").value;
    const password = document.getElementById("password").value;
    const role = document.getElementById("role").value;

    /*
     * this module execute if and only if there has been a successful retrieval
     * of location data ("latitude" & "longitude")
     */
    const success = (position) => {
        // retrieve user coordinates
        const latitude = position.coords.latitude;
        const longitude = position.coords.longitude;

        // store and format user data in an array for parsing
        const userInfo = {
            latitude: latitude,
            longitude: longitude,
            email: email,
            name: name,
            password: password,
            role: role
        };

        /*
         * send the user information from the array to the servlet
         * it sends the data in JSON format
         */
        fetch('LocationHandlerServlet.do', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(userInfo)
        }).then(response => {
            if (!response.ok) {
                throw new Error("Error requesting information from the server.");
            }
            return response.json();
        }).then(data => {
            console.log(data);

            // test the boolean to see if a user can log in
            if (data.canLogin) {
                console.log("You can login.");
                /*
                 * you can also use window.location.href="###"
                 * to redirect to your desired page
                 * 
                 * but to implement this, you have to to send the other data here too
                 */
            } else {
                console.log("You can't log in.");
            }
        });
    };

    // executes when user location retrieval fails
    const error = () => {
        console.log("Unable to retrieve your location.");
    };
    navigator.geolocation.getCurrentPosition(success, error);
}