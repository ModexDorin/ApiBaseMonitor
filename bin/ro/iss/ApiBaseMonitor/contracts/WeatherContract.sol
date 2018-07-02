contract WeatherContract {
    
    // apibase contract address
    address public apibaseAddress = 0xd217bac159d76fccceac9be277edc15b72ffdee3;
    string public customRequestId;
    mapping(uint256 => string) requestList;
    mapping(uint256 => string) responseList;

    function callApiLocationInformation() public {
        require(msg.sender != address(0));
        ApiBase apiBase = ApiBase(apibaseAddress);
        customRequestId++;
        apiBase.queryApiBase(address(this), 0, "GET", "http://apidev.accuweather.com/locations/v1/search?q=bucharest&apikey=hoArfRosT1215","{}", customRequestId);

    }

    function callApiCurrentConditions() public {
        require(msg.sender != address(0));
        ApiBase apiBase = ApiBase(apibaseAddress);
        customRequestId++;
        apiBase.queryApiBase(address(this), 0, "GET", "http://apidev.accuweather.com/currentconditions/v1/287430.json?language=en&apikey=hoArfRosT1215","{}", customRequestId);

    }
    
    function updateValue(uint256 _customRequestId, string _responseString) {
        responseList[_customRequestId] = _responseString;
    }
    
}

contract ApiBase {
    function queryApiBase(address _callingContract, uint256 _type, string _method, string _url, string _params);
    function updateApiResponse(uint256 _requestId, string _response);
}
