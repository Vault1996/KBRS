package by.epam.cinemarating.validation;

import net.tanesha.recaptcha.http.HttpLoader;
import net.tanesha.recaptcha.http.SimpleHttpLoader;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;

public class CaptchaValidator {
    private static final String PRIVATE_KEY = "6LdujQoUAAAAALn_uoA6c4AiRAHO4PiPuRFtmHgB";
    public boolean validate(String remoteAddr, String uresponse) {
        String postParameters = "secret=" + URLEncoder.encode(PRIVATE_KEY) + "&response=" + URLEncoder.encode(uresponse) + "&remoteip=" + URLEncoder.encode(remoteAddr);
        HttpLoader httpLoader = new SimpleHttpLoader();
        String message = httpLoader.httpPost("https://www.google.com/recaptcha/api/siteverify", postParameters);
        if (message == null) {
            return false;
        } else {
            try {
                JSONObject object = new JSONObject(message);
                return object.getBoolean("success");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
