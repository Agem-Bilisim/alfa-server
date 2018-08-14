package tr.com.agem.alfa.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class RestClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// Apache Httpclient 
		
	String urlEducationReports = "http://localhost:8081/alfalms/apieducationreports";
	String urlEducations = "http://localhost:8081/alfalms/apieducations";
	String urlUsers = "http://localhost:8081/alfalms/apiusers";
	String text = "";
	
	try {
		String educationReports = getJsonData(urlEducationReports,text);
//		String educations = getJsonData(urlEducations,text);
//		String users = getJsonData(urlUsers,text);
		
	} catch (IOException e) {
	
		e.printStackTrace();
	}
		
	}
	
	public static String getJsonData(String url,String text) throws IOException {
		 StringBuffer result = new StringBuffer();  
	        CloseableHttpClient httpClient = HttpClientBuilder.create().build(); 
	        HttpPost postRequest = new HttpPost(url);  
	        try {  
	            StringEntity input = new StringEntity(text);  
	            input.setContentType("application/json");  
	            postRequest.setEntity(input);  
	            HttpResponse response = httpClient.execute(postRequest);  
	            if (response.getStatusLine().getStatusCode() != 200) {  
	                throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());  
	            }  
	            BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));  
	            String output;  
	            System.out.println("Output from Server .... \n");  
	            while ((output = br.readLine()) != null) {  
	                System.out.println(output);  
	                result.append(output);  
	            }  
	        } catch (MalformedURLException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        } finally {  
	            httpClient.close();  
	        }  
	        System.out.println(result.toString());  
	        return result.toString();
	}

}
