package ro.iss.ApiBaseMonitor.classes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * This class consists of methods that operate HTTP GET/POST requests
 *
 * @author Dorin Stan
 *
 */
@SuppressWarnings("deprecation")
public class HttpHelper
{

	@SuppressWarnings(
	{ "resource" })
	public static String post(String uri, HashMap<String, String> parameters) throws Exception
	{

		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(uri);

		ArrayList<NameValuePair> postParameters;
		postParameters = new ArrayList<NameValuePair>();

		// process parameters if we have them
		if (parameters.size() != 0)
		{
			// get map iterator
			Iterator<Entry<String, String>> it = parameters.entrySet().iterator();

			// iterate map values
			while (it.hasNext())
			{
				// get map pair
				Map.Entry<String, String> pair = it.next();
				postParameters.add(new BasicNameValuePair("\"" + pair.getKey() + "\"", "\"" + pair.getValue() + "\""));

				it.remove();
			}
			post.setEntity(new UrlEncodedFormEntity(postParameters, "UTF-8"));
		}

		// get the http response
		HttpResponse response = client.execute(post);
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line;
		while ((line = rd.readLine()) != null)
		{
			result.append(line);
		}

		System.out.println("POST response string:: " + result.toString());
		// return
		return result.toString();

	}

	@SuppressWarnings(
	{ "resource" })
	public static String get(String uri) throws Exception
	{

		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(uri);

		// execute GET request
		HttpResponse response = client.execute(get);

		// process the response
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line;
		while ((line = rd.readLine()) != null)
		{
			result.append(line);
		}

		System.out.println("GET response string:: " + result.toString());

		return result.toString();
	}

	public static HashMap<String, String> jsonToMap(String text)
	{

		String jsonString = text;
		HashMap<String, String> map = new Gson().fromJson(jsonString, new TypeToken<HashMap<String, String>>()
		{
		}.getType());
		return map;
	}

}
