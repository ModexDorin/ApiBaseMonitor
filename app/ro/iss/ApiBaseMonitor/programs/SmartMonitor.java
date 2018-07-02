package ro.iss.ApiBaseMonitor.programs;

import java.io.IOException;
import java.math.BigInteger;
import java.net.UnknownHostException;
import java.util.Arrays;

import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;

import com.google.gson.JsonSyntaxException;

import play.Logger;
import ro.iss.ApiBaseMonitor.classes.ApiBase;
import ro.iss.ApiBaseMonitor.classes.CustomResponse;
import ro.iss.ApiBaseMonitor.classes.DatabaseHelper;
import ro.iss.ApiBaseMonitor.classes.HttpHelper;
import rx.Observable;
import rx.Subscription;

public class SmartMonitor
{
	public static void main(String[] args) throws InterruptedException, IOException
	{
		// USE THIS TO CREATE THE CONTRACT CLASS: solc ApiBase.sol --bin --abi --optimize -o build/ 
		// /Users/dorin/Documents/web3j-3.4.0/bin/web3j solidity generate /Users/dorin/Documents/ApiBaseCurrency/app/ro/iss/ApiBaseCurrency/contracts/build/Apibase.bin /Users/dorin/Documents/ApiBaseCurrency/app/ro/iss/ApiBaseCurrency/contracts/build/ApiBase.abi -p ro.iss.ApiBaseCurrency.classes -o /Users/dorin/Documents/ApiBaseCurrency/app/ro/iss/ApiBaseCurrency/classes
		
		DatabaseHelper databaseHelper = new DatabaseHelper();

		// contract address
		final String contractAddress = "0xd217bac159d76fccceac9be277edc15b72ffdee3"; 
		
		// define rpc connection and blockchain identifier
		Web3j web3j = Web3j.build(new HttpService("http://127.0.0.1:8545"));
		Logger.debug("Connected to Ethereum client version: " + web3j.web3ClientVersion().send().getWeb3ClientVersion());
		
		// load main account based on private key
		Credentials credentials = Credentials.create("33694b12b462a9364423aed6b88b6677cb24d7ed0b7e602ea2fa841d058b613d");
		Logger.debug("[ETH-INFO] Credentials: " + credentials.getAddress());

		// load existing smart contract
		ApiBase contract = ApiBase.load(contractAddress, web3j, credentials, Contract.GAS_PRICE, Contract.GAS_LIMIT);
		Logger.debug("[ETH-INFO] Loading contract: " + contract.getContractAddress());

		Event REQUESTIDEVENT_EVENT = new Event("RequestIdEvent", 
	            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}),
	            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));

		EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST, contract.getContractAddress()); //.substring(2)
		System.out.println("address: " + filter.getAddress().toString());
		filter.addSingleTopic(EventEncoder.encode(REQUESTIDEVENT_EVENT));
		Observable<ApiBase.RequestIdEventEventResponse> callbackEvent = contract.requestIdEventEventObservable(filter);

		// make a subscription using the observable, monitoring the logs
		Subscription subscription = callbackEvent.subscribe(log ->
		{

			String httpResponse = "";
			CustomResponse customResponse = new CustomResponse();

			Logger.debug("[ETH-EVENT-INFO] RequestId:: " + log.requestId);
			Logger.debug("[ETH-EVENT-INFO] _method:: " + log._method);
			Logger.debug("[ETH-EVENT-INFO] URL:: " + log._url);

			// 1,"GET"
			if (log._method.equals("GET"))
			{

				try
				{
					// GET request to the URL
					httpResponse = HttpHelper.get(log._url);
					customResponse.setRequestId(log.requestId.toString());
					customResponse.setResponse(HttpHelper.get(log._url));
				} catch (JsonSyntaxException e)
				{
					e.printStackTrace();
				} catch (Exception e)
				{
					e.printStackTrace();
				}

			}
			// 1,"POST","http://localhost:9000/postdata","{}"
			else
			{
				System.out.println("");
				try
				{
					// POST request to URL
					customResponse.setRequestId(log.requestId.toString());
					customResponse.setResponse(HttpHelper.post(log._url, HttpHelper.jsonToMap(log._params)).toString());
				} catch (JsonSyntaxException e)
				{
					e.printStackTrace();
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}

			Logger.debug("HttpResponse:: " + httpResponse);
			Logger.debug("Log._queryType::" + log._queryType);

			// internal hook
			if (log._queryType.toString().equals("0"))
			{
				// set values requestId and response to contract ApiBase method
				org.web3j.protocol.core.RemoteCall<TransactionReceipt> txReceipt = contract.updateApiResponse(BigInteger.valueOf(log.requestId.intValue()), customResponse.getResponse());
				TransactionReceipt tx = null;
				try
				{
					tx = txReceipt.send();
				} catch (Exception e)
				{
					e.printStackTrace();
				}

				Logger.debug("[ETH-INFO] Executed __internal_hook " + tx.getTransactionHash() + "; Gas used: " + tx.getGasUsed().toString());
			} else

			// external hook
			{
				try {
					databaseHelper.getMongoDataStore().save(customResponse);
				} catch (UnknownHostException e1) {
					// e1.printStackTrace();
					Logger.error("Connection to MongoDb problem!");
				}

				// call to updateApiResponse contract method
				org.web3j.protocol.core.RemoteCall<TransactionReceipt> txReceipt = contract.updateApiResponse(BigInteger.valueOf(log.requestId.intValue()), "http://localhost:9000/" + log.requestId.toString());
				TransactionReceipt tx = null;
				try
				{
					tx = txReceipt.send();
				} catch (Exception e)
				{
					e.printStackTrace();
				}

				Logger.debug("[ETH-INFO] Executed __external_hook " + tx.getTransactionHash());
			}

		} //, 
			// Throwable::printStackTrace
		);

		// set minutes for Observable to be active and then unsubscribe
//		TimeUnit.MINUTES.sleep(10);
//		subscription.unsubscribe();

		Logger.debug("TEST FINISHED");
	}

}
