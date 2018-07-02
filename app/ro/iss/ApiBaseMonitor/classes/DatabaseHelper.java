package ro.iss.ApiBaseMonitor.classes;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import play.Logger;

public class DatabaseHelper
{

	public Datastore getMongoDataStore() throws UnknownHostException
	{

		// Sets the server address and port
		String serverAddress = "localhost:27017";
		// Sets the database name
		String dbName = "dbName";
		// Sets the database user
		String dbUser = "dbUser";
		// Sets the database password
		String dbPassword = "dbPassword";

		// Morphia instance
		Morphia morphia = new Morphia();

		ServerAddress addr = new ServerAddress(serverAddress);
		List<MongoCredential> credentialsList = new ArrayList<MongoCredential>();
		MongoCredential credentia = MongoCredential.createCredential(dbUser, dbName, dbPassword.toCharArray());

		// Sets the credentials
		credentialsList.add(credentia);
		// Instance of MongoClient
		MongoClient client = new MongoClient(addr);

		Datastore datastore = null;
		try
		{
			datastore = morphia.createDatastore(client, dbName);
		} catch (Exception e)
		{
			Logger.error("Connection to database is unsuccessful");
		}

		return datastore;

	}
}
