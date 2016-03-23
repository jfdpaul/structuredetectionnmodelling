import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by SONY on 3/20/2016.
 */
public class ServerConnect {
        private final String USER_AGENT = "Mozilla/5.0";

       /* public static void main(String[] args) throws Exception {

            ServerConnect http = new ServerConnect();

            System.out.println("Testing 1 - Send Http GET request");
            http.sendGet("");

            //System.out.println("\nTesting 2 - Send Http POST request");
            //http.sendPost();
        }*/

        // HTTP GET request
        public String sendGet(String ur) throws Exception {

           // String url = "http://localhost/test/form.php";
            ///String url = "http://192.168.173.8:8087";
            String url = ur;
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("GET");

            //add request header
            con.setRequestProperty("User-Agent", USER_AGENT);

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            return parseHtml(response.toString());

        }
    private String parseHtml(String code)
    {
        String s="";
        boolean tr=false;
        for(int i=0;i<code.length();i++)
        {
            s=s+code.charAt(i);
            if(code.charAt(i)=='<')
                tr=true;
            if(tr&&code.charAt(i)=='>')
            {
                tr=false;
                s=s+"\n";
            }
        }
        return s;
    }
/*
        // HTTP POST request
        private void sendPost() throws Exception {

            String url = "https://selfsolve.apple.com/wcResults.do";
            URL obj = new URL(url);
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

            //add reuqest header
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Post parameters : " + urlParameters);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            System.out.println(response.toString());
        }*/
    }
