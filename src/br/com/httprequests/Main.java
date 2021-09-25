package br.com.httprequests;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Main {

    private static HttpURLConnection connection;

    public static void main(String[] args) {
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();


        try {
            URL url = new URL("https://jsonplaceholder.typicode.com/albums/");

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout( 5000 );
            connection.setReadTimeout(5000);

            if( connection.getResponseCode() == 200 ){
                reader = new BufferedReader( new InputStreamReader( connection.getInputStream()));

                while( (line = reader.readLine()) != null ){
                    responseContent.append( line );
                }

                reader.close();
            }else{
                reader = new BufferedReader( new InputStreamReader( connection.getErrorStream() ));

                while( (line = reader.readLine()) != null ){
                    responseContent.append( line );
                }

                reader.close();
            }
            parse( responseContent.toString() );
            //System.out.println(responseContent.toString());

        }catch (MalformedURLException | ProtocolException e )
        {
            System.out.println( e.getMessage() );
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            connection.disconnect();
        }
    }

    public static String parse( String responseBody ){
        JSONArray albums = new JSONArray( responseBody );

        for ( int i = 0; i < albums.length(); i++ )
        {
            JSONObject album = albums.getJSONObject( i );

            int id = album.getInt("id");
            int userId = album.getInt("userId");
            String title = album.getString("title");

            System.out.println( id + " " + title + " " + userId );
        }

        return null;
    }
}
