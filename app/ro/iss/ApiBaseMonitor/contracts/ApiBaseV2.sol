pragma solidity ^0.4.23;


contract ApiBase {

    event RequestIdEvent(uint256 indexed requestId, uint256 indexed _queryType, uint256 indexed _customRequestId, string _method, string _url, string _params);
    event ApiCompleted(uint256 indexed _requestId);
    event OwnershipRenounced(address indexed previousOwner);
    event OwnershipTransferred( address indexed previousOwner, address indexed newOwner);

    address public owner;
    
    // the address to receive the payment for response gas
    address public gasAddress;
    
    // requestId => queryType
    mapping(uint256 => uint256) private myTypeMap; 
    // requestId => result (0 if external response)
    mapping(uint256 => string) private myResultMap; 
    // list of allowed members
    mapping(address => bool) private allowedOwnerMembers; 
    // the requests are correlated to their owner; only the request owner can see the result
    mapping(uint256 => address) private requestAllowedOwnerMembers; 
    // the requests
    mapping(uint256 => address) private requestCallingContractAddress; 
    // java monitor will process only if false
    mapping(uint256 => bool) private requestIsProcessed;
    // list of custom requests to be returned to calling contract: requestId => customRequestId
    mapping(uint256 => uint256) private customRequestIds;

    uint256 internal counter = 0;

    // checks if the operated by the owner
    modifier onlyOwner() {
        require(msg.sender == owner);
        _;
    }

    modifier onlyMember() {
        require(allowedOwnerMembers[msg.sender] == true);
        _;
    }

    // check if the request is the from its owner
    modifier allowOnlyMember(uint256 _requestId) {
        require(requestAllowedOwnerMembers[_requestId] == msg.sender);
        _;
    }

    constructor() public {
        require(owner == address(0));
        owner = msg.sender;
        allowedOwnerMembers[owner] = true;
    }

    // _type: 0 internal; _type: 1 external;
    function queryApiBase(address _callingContract, uint256 _type, string _method, string _url, string _params, uint256 _customRequestId) public onlyMember returns (uint256) {
        require(_type == 0 || _type == 1);
        counter++;
        myTypeMap[counter] = _type;
        requestAllowedOwnerMembers[counter] = msg.sender;
        requestCallingContractAddress[counter] = _callingContract;
        customRequestIds[counter] = _customRequestId;
        emit RequestIdEvent(counter, _type, _method, _url, _params, _customRequestId);
        return counter;
    }

    function updateApiResponse(uint256 _requestId, string _response) public onlyOwner {
		require(requestIsProcessed[_requestId] != true);
        myResultMap[_requestId] = _response;

        CustomContract customContract = CustomContract(requestCallingContractAddress[_requestId]);
        customContract.updateValue(_response);
        requestIsProcessed[_requestId] = true;
        emit ApiCompleted(_requestId);
    }

    // get the response for the requestId
    function getResult(uint256 _requestId) public view allowOnlyMember(_requestId) returns (string){
        return myResultMap[_requestId];
    }

    // get the request type
    function getRequestType(uint256 _requestId) public view allowOnlyMember(_requestId) returns (uint256){
        return myTypeMap[_requestId];
    }

    function getAllowedOwnerMembers(address _member) public view onlyOwner returns (bool) {
        return allowedOwnerMembers[_member];
    }

    // add member address
    function addOwnerMember(address _memberAddress) public onlyOwner {
        require(_memberAddress != address(0));
        allowedOwnerMembers[_memberAddress] = true;
    }

    // disable member address
    function disableOwnerMember(address _memberAddress) public onlyOwner {
        require(_memberAddress != address(0));
        allowedOwnerMembers[_memberAddress] = false;
    }

    function setGasPayAddress(address _address) onlyOwner {
        require(_address != address(0));
        gasAddress = _address;
    }

    function getGasPayAddress() onlyMember returns (address) {
        return gasAddress;
    }
}

contract CustomContract {    
    function updateValue(string _result);
}