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
public class CliqInformer {
	public static void main(String args[]) {
		System.out.println("Calling Cliq...");
		HttpURLConnection connection;
		StringBuffer responseContent = new StringBuffer();
		try {
			String message;
			String CliqWebhookToken = args[0];
			String CliqChannelLink = args[1];
			if(!CliqChannelLink.contains("https://cliq.zoho.com/api/v2/channelsbyname/") || !CliqChannelLink.contains("message"))
        if(CliqChannelLink.matches("[a-z]+"))
          CliqChannelLink = "https://cliq.zoho.com/api/v2/channelsbyname/" + CliqChannelLink + "/message";			  
			String Event = args[2];
			String[] EventWords = Event.split("_");
			Event = new String();
			for(String s: EventWords)
			  Event += s.substring(0,1).toUpperCase() + s.substring(1) + " ";
			Event = Event.trim();
			String ServerURL = args[3];
			String Repository = args[4];
			String RepositoryURL = ServerURL + "/" + Repository;
			String Workflow = args[5];
			String Actor = args[6];
			String ActorURL = ServerURL + "/" + Actor;
			String RunId = args[7];
			String WorkflowURL = RepositoryURL + "/actions/runs/" + RunId;
			if(args[8].equals(""))
			  message = "A " + Event + " has been Triggered at " + RepositoryURL + " triggered by *[" + Workflow + "](" + WorkflowURL + ")* initiated by [" + Actor + "](" + ActorURL + ")";
			else
			  message = args[8];
			  message = message.replace("(me)","[" + Actor + "](" + ActorURL + ")");
			  message = message.replace("(workflow)","[" + Workflow + "](" + WorkflowURL + ")" );
			  message = message.replace("(repo)",RepositoryURL);
			String TextParams = "{\n\"text\":\"" + message + "\",\n\"bot\":\n{\n\"name\":\"CliqInformer\",\n\"image\":\"https://workdrive.zohoexternal.com/external/047d96f793983933bbdb59deb9c44f5443b83a7188e278736405d4d733923181/download?directDownload=true\"\n},\n" /*\"card\":{\n\"title\":\"CliqInformer\",\n\"icon\":\"https://github.githubassets.com/images/modules/logos_page/GitHub-Mark.png\",\"thumbnail\":\"https://workdrive.zoho.com/file/89g9q5f9198d8b7ae4b4bb5edd53bb7641488\",\n}*/ + "\"slides\":\n[\n{\n\"type\":\"label\",\n\"title\":\"CliqInformer Info\",\n\"buttons\":[\n{\n\"label\":\"View Repository\",\"action\":\n{\n\"type\":\"open.url\",\n\"data\":\n{\n\"web\":\"" + RepositoryURL + "\"}}},\n{\n\"label\":\"View Workflow\",\"action\":\n{\n\"type\":\"open.url\",\n\"data\":\n{\n\"web\":\"" + WorkflowURL + "\"}}}\n],\n\"data\":[\n{\n\"Github Event\":\"" + Event + "\",\n\"Github Repository\":\"[" + Repository + "](" + RepositoryURL + ")\",\n\"Github Workflow\":\"[" + Workflow + "](" + WorkflowURL + ")\",\n\"Github Actor\":\"[" + Actor + "](" + ActorURL + ")\"\n}\n]\n}\n]\n}";
			System.out.println(TextParams);
			connection = (HttpURLConnection) new URL(CliqChannelLink + "?zapikey=" + CliqWebhookToken).openConnection();
			System.out.println(CliqChannelLink + "?zapikey=" + CliqWebhookToken);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type","application/json");
			connection.setDoOutput(true);
			OutputStream os = connection.getOutputStream();
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
	    if(status == 204)
	      System.out.println("Message Sent to Cliq");
	    else
	      System.out.println("Error Occured");
			System.out.println(responseContent.toString());
		}  catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
