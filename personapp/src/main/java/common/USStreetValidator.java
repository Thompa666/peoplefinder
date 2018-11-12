package common;

import android.graphics.Region;
import android.net.NetworkInfo;
import android.os.StrictMode;

import com.smartystreets.api.Credentials;
import com.smartystreets.api.SharedCredentials;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.us_street.Candidate;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by paul on 1/4/17.
 */


        import com.smartystreets.api.Credentials;
        import com.smartystreets.api.SharedCredentials;
        import com.smartystreets.api.exceptions.SmartyException;
        import com.smartystreets.api.us_street.Candidate;
        import com.smartystreets.api.us_street.Client;
        import com.smartystreets.api.us_street.ClientBuilder;
        import com.smartystreets.api.us_street.Lookup;

        import java.io.IOException;
        import java.util.ArrayList;

public class USStreetValidator {

    public static String Run(String street, String city, String region,String zip) {
        Credentials mobile = new SharedCredentials("82971481910996", "com.xware.peoplefinder");
        Client client = new ClientBuilder(mobile).build();

        Lookup lookup = new Lookup();
        lookup.setStreet(street);
        lookup.setCity(city);
        lookup.setState(region);
        lookup.setZipCode(zip);

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            client.send(lookup);
        }
        catch (SmartyException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        ArrayList<Candidate> results = lookup.getResult();
        StringBuilder output = new StringBuilder();

        if (results.isEmpty()) {
            output.append("Error. Address is not valid.");
            output.append("Leave Address blank or use the format : 'street,city,state,zip' ");
            return output.toString();
        }

        Candidate firstCandidate = results.get(0);

        output.append("Address is valid. (There is at least one candidate)\n\n");
        output.append("ZIP Code: " + firstCandidate.getComponents().getZipCode() + "\n");
        output.append("County: " + firstCandidate.getMetadata().getCountyName() + "\n");
        output.append("Latitude: " + firstCandidate.getMetadata().getLatitude() + "\n");
        output.append("Longitude: " + firstCandidate.getMetadata().getLongitude() + "\n");
        return "good";
    //    return output.toString();
    }
}