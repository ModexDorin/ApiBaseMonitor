package ro.iss.ApiBaseMonitor.classes;

import org.mongodb.morphia.annotations.Entity;

/**
 * The API response model
 */
@Entity(value = "requests", noClassnameStored = true)
public class CustomResponse
{
	// the requestId corresponding to the smart contract requestId
	private String	requestId;

	// the response from the API
	private String	response;

	public String getRequestId()
	{

		return requestId;
	}

	public void setRequestId(String requestId)
	{

		this.requestId = requestId;
	}

	public String getResponse()
	{

		return response;
	}

	public void setResponse(String response)
	{

		this.response = response;
	}

}
