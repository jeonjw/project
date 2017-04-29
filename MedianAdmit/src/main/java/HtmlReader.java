import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HtmlReader {

    public String readHTML(String strUrl) {
        StringBuffer sb = new StringBuffer();
        BufferedReader in;


        try {
            URL url = new URL(strUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

            String str = null;
            while ((str = in.readLine()) != null)
                sb.append(str);

        } catch (IOException e) {
            e.printStackTrace();

        }


        return sb.toString();
    }
}
