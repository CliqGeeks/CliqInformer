import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
//import org.apache.oltu.oauth.client.*;
public class CliqInformer {
	public static void main(String args[]) {
		System.out.println("Calling Cliq...");
		HttpURLConnection connection;
		StringBuffer responseContent = new StringBuffer();
		try {
			connection = (HttpURLConnection) new URL(args[1] + "?zapikey=" + args[0]).openConnection();
			System.out.println(args[1] + "?zapikey=" + args[0]);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type","application/json");
			connection.setDoOutput(true);
			OutputStream os = connection.getOutputStream();
			String TextParams = "{\n\"text\":\"" + args[2] + "\"\n}";
			System.out.println(TextParams);
			os.write(TextParams.getBytes());
			os.flush();
			os.close();
			int status = connection.getResponseCode();
			System.out.println(status);
			if(status > 299) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
				String line;
				while((line = reader.readLine()) != null) {
					responseContent.append(line);
				}
				reader.close();
			}
			else
			{
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;
				while((line = reader.readLine()) != null) {
					responseContent.append(line);
				}
				reader.close();
			}
			System.out.println(responseContent.toString());
		}  catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Message Sent to Cliq");
	}
}
