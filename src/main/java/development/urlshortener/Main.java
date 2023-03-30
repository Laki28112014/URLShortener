package development.urlshortener;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
     getURLData();
    }


    public static void getURLData() throws IOException {

        //region Scanner
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.print("Geben Sie eine URL ein: ");
        String urlInput = myObj.nextLine();  // Read user input
        myObj.close();
        //endregion

        //region Connection
        URL url = new URL("https://csclub.uwaterloo.ca/~phthakka/1pt-express/addURL?long=" + urlInput);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        //endregion


        //region Short the link...
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            JSONObject jsonObject = new JSONObject(response.toString());
            System.out.println("https://1pt.co/" + jsonObject.get("short"));

        }
        //endregion
    }
}