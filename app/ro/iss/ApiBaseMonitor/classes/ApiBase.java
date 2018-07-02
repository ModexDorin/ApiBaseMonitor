package ro.iss.ApiBaseMonitor.classes;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import rx.Observable;
import rx.functions.Func1;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.4.0.
 */
public class ApiBase extends Contract {
    private static final String BINARY = "6080604052600060065534801561001557600080fd5b50600054600160a060020a03161561002c57600080fd5b60008054600160a060020a0319163317808255600160a060020a03168152600360205260409020805460ff191660011790556109cb8061006d6000396000f3006080604052600436106100a35763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166302c94bc081146100a857806312637911146101085780632684ba4e1461012957806357f0095f1461015e5780638da5cb5b14610188578063995e4339146101b9578063ac4ce2c614610246578063c51910751461026a578063f0ab631a1461034f578063fafd8a1014610370575b600080fd5b3480156100b457600080fd5b5060408051602060046024803582810135601f81018590048502860185019096528585526101069583359536956044949193909101919081908401838280828437509497506103889650505050505050565b005b34801561011457600080fd5b50610106600160a060020a03600435166104c4565b34801561013557600080fd5b5061014a600160a060020a03600435166104fa565b604080519115158252519081900360200190f35b34801561016a57600080fd5b50610176600435610518565b60408051918252519081900360200190f35b34801561019457600080fd5b5061019d61052a565b60408051600160a060020a039092168252519081900360200190f35b3480156101c557600080fd5b506101d1600435610539565b6040805160208082528351818301528351919283929083019185019080838360005b8381101561020b5781810151838201526020016101f3565b50505050905090810190601f1680156102385780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561025257600080fd5b50610106600435600160a060020a03602435166105d8565b34801561027657600080fd5b50604080516020600460443581810135601f8101849004840285018401909552848452610176948235600160a060020a031694602480359536959460649492019190819084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a99988101979196509182019450925082915084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a9998810197919650918201945092508291508401838280828437509497506106139650505050505050565b34801561035b57600080fd5b50610106600160a060020a036004351661080d565b34801561037c57600080fd5b50610106600435610846565b6000828152600260209081526040822083516103a692850190610904565b505060008281526005602090815260408083205490517f348c4a1f00000000000000000000000000000000000000000000000000000000815260048101838152855160248301528551600160a060020a0390931694859463348c4a1f9488948493604490910192860191908190849084905b83811015610430578181015183820152602001610418565b50505050905090810190601f16801561045d5780820380516001836020036101000a031916815260200191505b5092505050600060405180830381600087803b15801561047c57600080fd5b505af1158015610490573d6000803e3d6000fd5b50506040518592507fc47937eb80e67ae2cdf2f27f3ea9dfe94458895bb48244e3af481caf68d616ca9150600090a2505050565b600160a060020a03811615156104d957600080fd5b600160a060020a03166000908152600360205260409020805460ff19169055565b600160a060020a031660009081526003602052604090205460ff1690565b60009081526001602052604090205490565b600054600160a060020a031681565b600081815260026020818152604092839020805484516001821615610100026000190190911693909304601f810183900483028401830190945283835260609390918301828280156105cc5780601f106105a1576101008083540402835291602001916105cc565b820191906000526020600020905b8154815290600101906020018083116105af57829003601f168201915b50505050509050919050565b600091825260056020526040909120805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a03909216919091179055565b60008415806106225750846001145b151561062d57600080fd5b600680546001908101808355600090815260209182526040808220899055835482526004835280822080543373ffffffffffffffffffffffffffffffffffffffff199182161790915584548352600584528183208054909116600160a060020a038c1617905592548351606080825289519082015288518a9592947fa57c16f3d8a914f655401f8d988b0c8cb6ac307da12bd6913fa037e25415c518948b948b948b94909384938482019385019260808601928a01918190849084905b838110156107025781810151838201526020016106ea565b50505050905090810190601f16801561072f5780820380516001836020036101000a031916815260200191505b50848103835286518152865160209182019188019080838360005b8381101561076257818101518382015260200161074a565b50505050905090810190601f16801561078f5780820380516001836020036101000a031916815260200191505b50848103825285518152855160209182019187019080838360005b838110156107c25781810151838201526020016107aa565b50505050905090810190601f1680156107ef5780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390a35060065495945050505050565b600160a060020a038116151561082257600080fd5b600160a060020a03166000908152600360205260409020805460ff19166001179055565b60008181526005602081815260408084205481517f348c4a1f000000000000000000000000000000000000000000000000000000008152600481019390935260248301939093527f646f72696e000000000000000000000000000000000000000000000000000000604483015251600160a060020a03909216928392839263348c4a1f92606480830193919282900301818387803b1580156108e757600080fd5b505af11580156108fb573d6000803e3d6000fd5b50505050505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061094557805160ff1916838001178555610972565b82800160010185558215610972579182015b82811115610972578251825591602001919060010190610957565b5061097e929150610982565b5090565b61099c91905b8082111561097e5760008155600101610988565b905600a165627a7a72305820329b8633634f33046a4ada6f0ea868465a6fa2c086205a56bb265887d23c921d0029";

    public static final String FUNC_UPDATEAPIRESPONSE = "updateApiResponse";

    public static final String FUNC_DISABLEOWNERMEMBER = "disableOwnerMember";

    public static final String FUNC_GETALLOWEDOWNERMEMBERS = "getAllowedOwnerMembers";

    public static final String FUNC_GETREQUESTTYPE = "getRequestType";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_GETRESULT = "getResult";

    public static final String FUNC_SETADDRESS = "setAddress";

    public static final String FUNC_QUERYAPIBASE = "queryApiBase";

    public static final String FUNC_ADDOWNERMEMBER = "addOwnerMember";

    public static final String FUNC_UPDATECUSTOM = "updateCustom";

    public static final Event REQUESTIDEVENT_EVENT = new Event("RequestIdEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}),
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event APICOMPLETED_EVENT = new Event("ApiCompleted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}),
            Arrays.<TypeReference<?>>asList());
    ;

    public static final Event OWNERSHIPRENOUNCED_EVENT = new Event("OwnershipRenounced", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
            Arrays.<TypeReference<?>>asList());
    ;

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}),
            Arrays.<TypeReference<?>>asList());
    ;

    protected ApiBase(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ApiBase(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<TransactionReceipt> updateApiResponse(BigInteger _requestId, String _response) {
        final Function function = new Function(
                FUNC_UPDATEAPIRESPONSE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_requestId), 
                new org.web3j.abi.datatypes.Utf8String(_response)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> disableOwnerMember(String _memberAddress) {
        final Function function = new Function(
                FUNC_DISABLEOWNERMEMBER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_memberAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Boolean> getAllowedOwnerMembers(String _member) {
        final Function function = new Function(FUNC_GETALLOWEDOWNERMEMBERS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_member)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<BigInteger> getRequestType(BigInteger _requestId) {
        final Function function = new Function(FUNC_GETREQUESTTYPE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_requestId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> getResult(BigInteger _requestId) {
        final Function function = new Function(FUNC_GETRESULT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_requestId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> setAddress(BigInteger _requestId, String _address) {
        final Function function = new Function(
                FUNC_SETADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_requestId), 
                new org.web3j.abi.datatypes.Address(_address)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> queryApiBase(String _callingContract, BigInteger _type, String _method, String _url, String _params) {
        final Function function = new Function(
                FUNC_QUERYAPIBASE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_callingContract), 
                new org.web3j.abi.datatypes.generated.Uint256(_type), 
                new org.web3j.abi.datatypes.Utf8String(_method), 
                new org.web3j.abi.datatypes.Utf8String(_url), 
                new org.web3j.abi.datatypes.Utf8String(_params)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> addOwnerMember(String _memberAddress) {
        final Function function = new Function(
                FUNC_ADDOWNERMEMBER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_memberAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> updateCustom(BigInteger _requestId) {
        final Function function = new Function(
                FUNC_UPDATECUSTOM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_requestId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static RemoteCall<ApiBase> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ApiBase.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<ApiBase> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ApiBase.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public List<RequestIdEventEventResponse> getRequestIdEventEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(REQUESTIDEVENT_EVENT, transactionReceipt);
        ArrayList<RequestIdEventEventResponse> responses = new ArrayList<RequestIdEventEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RequestIdEventEventResponse typedResponse = new RequestIdEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.requestId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._queryType = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
            typedResponse._method = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._url = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse._params = (String) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<RequestIdEventEventResponse> requestIdEventEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, RequestIdEventEventResponse>() {
            @Override
            public RequestIdEventEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(REQUESTIDEVENT_EVENT, log);
                RequestIdEventEventResponse typedResponse = new RequestIdEventEventResponse();
                typedResponse.log = log;
                typedResponse.requestId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._queryType = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
                typedResponse._method = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._url = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse._params = (String) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<RequestIdEventEventResponse> requestIdEventEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(REQUESTIDEVENT_EVENT));
        return requestIdEventEventObservable(filter);
    }

    public List<ApiCompletedEventResponse> getApiCompletedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(APICOMPLETED_EVENT, transactionReceipt);
        ArrayList<ApiCompletedEventResponse> responses = new ArrayList<ApiCompletedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ApiCompletedEventResponse typedResponse = new ApiCompletedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._requestId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ApiCompletedEventResponse> apiCompletedEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, ApiCompletedEventResponse>() {
            @Override
            public ApiCompletedEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(APICOMPLETED_EVENT, log);
                ApiCompletedEventResponse typedResponse = new ApiCompletedEventResponse();
                typedResponse.log = log;
                typedResponse._requestId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<ApiCompletedEventResponse> apiCompletedEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(APICOMPLETED_EVENT));
        return apiCompletedEventObservable(filter);
    }

    public List<OwnershipRenouncedEventResponse> getOwnershipRenouncedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(OWNERSHIPRENOUNCED_EVENT, transactionReceipt);
        ArrayList<OwnershipRenouncedEventResponse> responses = new ArrayList<OwnershipRenouncedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OwnershipRenouncedEventResponse typedResponse = new OwnershipRenouncedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<OwnershipRenouncedEventResponse> ownershipRenouncedEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, OwnershipRenouncedEventResponse>() {
            @Override
            public OwnershipRenouncedEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(OWNERSHIPRENOUNCED_EVENT, log);
                OwnershipRenouncedEventResponse typedResponse = new OwnershipRenouncedEventResponse();
                typedResponse.log = log;
                typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<OwnershipRenouncedEventResponse> ownershipRenouncedEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OWNERSHIPRENOUNCED_EVENT));
        return ownershipRenouncedEventObservable(filter);
    }

    public List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, transactionReceipt);
        ArrayList<OwnershipTransferredEventResponse> responses = new ArrayList<OwnershipTransferredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<OwnershipTransferredEventResponse> ownershipTransferredEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, OwnershipTransferredEventResponse>() {
            @Override
            public OwnershipTransferredEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, log);
                OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
                typedResponse.log = log;
                typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<OwnershipTransferredEventResponse> ownershipTransferredEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OWNERSHIPTRANSFERRED_EVENT));
        return ownershipTransferredEventObservable(filter);
    }

    public static ApiBase load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ApiBase(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static ApiBase load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ApiBase(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class RequestIdEventEventResponse {
        public Log log;

        public BigInteger requestId;

        public BigInteger _queryType;

        public String _method;

        public String _url;

        public String _params;
    }

    public static class ApiCompletedEventResponse {
        public Log log;

        public BigInteger _requestId;
    }

    public static class OwnershipRenouncedEventResponse {
        public Log log;

        public String previousOwner;
    }

    public static class OwnershipTransferredEventResponse {
        public Log log;

        public String previousOwner;

        public String newOwner;
    }
}
