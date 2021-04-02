import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class GetTextFromImageTest {

    GetTextFromImage getTextFromImage = new GetTextFromImage();

    @Mock
    private URL url;

    @Before
    public void preTestSetup()
    {
        getTextFromImage = new GetTextFromImage();
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test()
    void checkResponseCode() throws IOException {
        GetTextFromImage getTextFromImage = new GetTextFromImage();
        int responseCode = getTextFromImage.getResponseCode("https://i.imgur.com/Atb2xiW.png");
        assertEquals(200, responseCode);
    }

}