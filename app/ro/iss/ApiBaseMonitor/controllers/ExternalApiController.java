package ro.iss.ApiBaseMonitor.controllers;

import java.net.UnknownHostException;
import java.util.List;

import org.mongodb.morphia.query.FindOptions;
import org.mongodb.morphia.query.Query;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;
import ro.iss.ApiBaseMonitor.classes.DatabaseHelper;
import ro.iss.ApiBaseMonitor.classes.CustomResponse;

/**
 * This class consists of methods to help process external data storage requests
 * @author Dorin Stan
 *
 */
public class ExternalApiController extends Controller
{

	public Result requestId(int requestId) throws UnknownHostException {

		Logger.debug("GET record from database for requestId:" + requestId);

		DatabaseHelper databaseHelper = new DatabaseHelper();
		// find options - one record only
		FindOptions findOptions = new FindOptions();
		findOptions.skip(0);
		findOptions.limit(1);

		// query with the result
		Query<CustomResponse> customResponseQ = databaseHelper.getMongoDataStore().createQuery(CustomResponse.class);
		List<CustomResponse> customResponse = customResponseQ.field("requestId").contains(Integer.toString(requestId)).asList(findOptions);

		// string to the returned
		String jsonInString = "";

		if (customResponse == null) {
			jsonInString = "{}";
		}
		else {
			// response to string
			ObjectMapper mapper = new ObjectMapper();
			try {
				jsonInString = mapper.writeValueAsString(customResponse);
			}
			catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		// return
		return ok(jsonInString);
	}
}
