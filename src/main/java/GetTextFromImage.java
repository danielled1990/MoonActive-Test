import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetTextFromImage {
    private URL url = null;
    private HttpsURLConnection con = null;

    public String getTextFromImageAPI(String imageURL) throws SomethingWrongExcepction {
        String lp = "";
        int responseCode=-1;
        try {
            Log.getInstance().WriteToLogFile("processing the plate number");
            responseCode = getResponseCode(imageURL);
            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                JSONObject jsonObject = new JSONObject(response.toString());
                try {
                    JSONArray res = jsonObject.getJSONArray("ParsedResults");
                    JSONObject jsonObjectInsideArray = new JSONObject(res.get(0).toString());
                    lp = jsonObjectInsideArray.getString("ParsedText");
                    Log.getInstance().WriteToLogFile("got the plate number" + lp.trim());
                    Log.getInstance().setlp(lp.trim());
                } catch (JSONException e) {
                    String error = "";
                    if (jsonObject.getBoolean("IsErroredOnProcessing")) {
                        JSONArray errorArr = jsonObject.getJSONArray("ErrorMessage");
                        for (int i = 0; i < errorArr.length(); i++) {
                            error += String.format("%s\n", errorArr.get(i).toString());

                        }
                        Log.getInstance().WriteToLogFile(error);
                        throw new SomethingWrongExcepction(StringUtils.SOMETHING_WENT_WRONG);
                    }

                }

            }
            else{
                Log.getInstance().WriteToLogFile(String.format("%s:%d",StringUtils.WRONG_RESPONSE_CODE,responseCode));
                throw new SomethingWrongExcepction(StringUtils.SOMETHING_WENT_WRONG);
            }
        } catch (IOException e) {
            Log.getInstance().WriteToLogFile(StringUtils.WRONG_OUTCOME);
            throw new SomethingWrongExcepction(StringUtils.TIME_OUT);
        }
        return lp;
    }

    public int getResponseCode(String imageURL) throws IOException {
        Log.getInstance().WriteToLogFile("processing the plate number");
        url = getUrl(imageURL);
        con = (HttpsURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        int responseCode = con.getResponseCode();
        return responseCode;
    }

    public URL getUrl(String imageURL) throws MalformedURLException {
        url = new URL(StringUtils.IMAGE_URL + imageURL);
        return url;
    }
}
