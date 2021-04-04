import StringForTests.StringForTests;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.hamcrest.CoreMatchers;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("Get Text From Image test")
class GetTextFromImageTest {

    private GetTextFromImage getTextFromImage = new GetTextFromImage();

    @Before
    public void preTestSetup() {
        getTextFromImage = new GetTextFromImage();

    }

    @Test()
    void test_checkResponseCode() {
        int responseCode = 0;
        try {
            responseCode = getTextFromImage.getResponseCode(StringForTests.IMAGE_TEST_1);
        } catch (IOException e) {
            fail(String.format("%s, %s %s", IOException.class.getName(), StringForTests.SOMETHING_WENT_WRONG, e.getMessage()));
        }
        assertEquals(200, responseCode);
    }

    @Test()
    void test_checkURL() {
        try {
            URL url = getTextFromImage.getUrl(StringForTests.IMAGE_TEST_1);
            Assert.assertThat(url.toString(), CoreMatchers.containsString("870a01303588957"));
            Assert.assertThat(url.toString(), CoreMatchers.containsString("apikey="));
            Assert.assertThat(url.toString(), CoreMatchers.containsString(StringForTests.IMAGE_TEST_1));
        } catch (MalformedURLException e) {
            fail(String.format("%s, %s %s", MalformedURLException.class.getName(), StringForTests.SOMETHING_WENT_WRONG, e.getMessage()));
        }

    }

    @Test
    void test_compareGoodJson() throws IOException {
        getTextFromImage.getConnection(new URL(StringUtils.IMAGE_URL + StringForTests.IMAGE_TEST_2));
        JSONObject actualObject = getTextFromImage.getJsonObject();
        JsonParser jsonParser = new JsonParser();
        JSONObject expectedJson = returnJson("correctJson.json");
        JsonElement jsonElementActual = jsonParser.parse(actualObject.toString());
        JsonElement jsonElementExpected = jsonParser.parse(expectedJson.toString());
        Map<String, MapDifference.ValueDifference<Object>> diffMap = getMapDiffrence(jsonElementActual, jsonElementExpected);
        Assert.assertTrue(diffMap.size() == 1 || diffMap.size() == 0); //check if only the ProcessingTimeInMilliseconds is different
    }

    @Test
    void test_compareErrorJson() throws IOException {
        getTextFromImage.getConnection(new URL(StringUtils.IMAGE_URL + StringForTests.IMAGE_TEST_ERROR));
        JSONObject jsonObject = getTextFromImage.getJsonObject();
        JSONObject jsonObject1 = returnJson("errorJson.json");
        assertEquals(jsonObject1.toString(), jsonObject.toString());
    }


    private JSONObject parseJSONFile(String filename) throws JSONException, IOException {
        String content = new String(Files.readAllBytes(Paths.get(filename)));
        return new JSONObject(content);
    }

    private JSONObject returnJson(String path) throws IOException, JSONException {
        String filename = StringForTests.PATH + path;
        JSONObject jsonObject = parseJSONFile(filename);
        return jsonObject;
    }

    private Map<String, MapDifference.ValueDifference<Object>> getMapDiffrence(JsonElement jsonElement, JsonElement jsonElement1) {
        Type type = new TypeToken<Map<String, Object>>() {
        }.getType();
        Gson gson = new Gson();
        Map<String, Object> leftMap = gson.fromJson(jsonElement, type);
        Map<String, Object> rightMap = gson.fromJson(jsonElement1, type);
        MapDifference<String, Object> difference = Maps.difference(leftMap, rightMap);
        Map<String, MapDifference.ValueDifference<Object>> difMap = difference.entriesDiffering();
        return difMap;
    }

}