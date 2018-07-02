# Apibase Monitor

Apibase is a solidity smart-contract written with the exact scope to help other custom contracts to interact with external world (out of the blockchain) through API requests.   
It has methods to raise logged events with parameters and there is a java application, on top of Web3j package, to monitor and operate API requests with the response written back to the Apibase smart contract.
Apibase will call the custom contract with the response it receives.   

## Installation

+ Install Solidity Compiler

    ```
    sudo add-apt-repository ppa:ethereum/ethereum
    sudo apt-get update
    sudo apt-get install solc
    ```

+ Download [web3j(v3.4.0)](https://github.com/web3j/web3j/releases)

## Usage

+ Compile the contract to generate *abi* and *bin* files
    ```
    solc ApiBase.sol --bin --abi --optimize -o build/
    ```
+ Generate the java class coresponding to the contract by using the web3j downloaded tool:

    ```
    ~/web3j-3.4.0/bin/web3j solidity generate '~/pathToBinFile/ApiBase.bin' '~/pathToAbiFile/ApiBase.abi' -p packageName -o '~/pathToJavaClassesFolder'
    ```
+ Deploy Apibase contact to the ethereum blockchain using [MyEtherWallet](https://www.myetherwallet.com/#contracts)

## Apibase contract in details:

+ __Events__

    - __RequestIdEvent__  logs the queryApiBase function call with the below parameters:  
        1. uint256 indexed requestId, logs the request id specific to the call  
        2. uint256 indexed _queryType, logs the query id specific to the call; 0 storage is contract internal and 1 storage is made to mongodb and a link to the response is made  
        3. uint256 indexed _customRequestId, logs the custom request id that was sent by the calling contract  
        4. string _method, logs the request type GET or POST  
        5. string _url, logs the URL to be called by the java monitor  
        6. string _params, logs the parameters to attach in case of a POST call, in json format  
          
    - __ApiCompleted__  logs the updateApiResponse function call with the below parameters:  
        1. uint256 indexed _requestId, logs the request id specific to the call  
        
    - __OwnershipRenounced__  logs the updateApiResponse function call with the below parameters:  
        1. address indexed previousOwner;  
        
    - __OwnershipTransferred__   
        1. address indexed previousOwner, logs the previous owner of the contract  
        2. address indexed newOwner, logs the new owner of the contract  
 
+ __Available Variables__  
    - __owner__ is the owner of the contract; only the owner can execute the main methods of the contract  
    - __gasAddress__ is the address to receive the payment for response gas  
    - __myTypeMap__ is a solidity map to store the query type (0 contract internal response storage/1 contract external storage) in the format: requestId => queryType  
    - __myResultMap__ is a solidity map to store the results for each request in the format: requestId => result   
    - __allowedOwnerMembers__ is a solidity map to store the allowed member addresses to connect, access and operate the Apibase contract   
    - __requestAllowedOwnerMembers__ is a solidity map to store the requests that are correlated to their owner; only the request owner can see the result  
    - __requestCallingContractAddress__ is a solidity map to store the contract addresses that are correlated to each request id  
    - __requestIsProcessed__ is a solidity map to store the requests that are processed by java monitor  
    - __customRequestIds__ list of custom request Ids to be returned to calling contract: requestId => customRequestId  
    - __counter__ stores the internal request id counter          

+ __Methods__    
    - ___queryApiBase__   
        1.  address _callingContract, log the address of the calling contract  
        2. uint256 _type, is the query type specific to the call; 0 storage is contract internal and 1 storage is made to mongodb and a link to the response is made  
        3. string _method, is the request type GET or POST  
        4. string _url, is the URL to be called by the java monitor  
        5. string _params, are the parameters to attach in case of a POST call in json format  
        6. uint256 customRequestId, logs the custom request id that was sent by the calling contract  
        
            __Note:__ can be executed only by approved member    
    - __updateApiResponse__   
        1. uint256 _requestId, the request id  
        2. string _response, the response relative to the request id  
        
            __Note:__ can be executed only by contract owner  
    - __getResult__ returns the response for a request id  
        1. uint256 _requestId, the request id  
            
            __Note:__ can be executed only by approved member  
    - __getRequestType__ gets the request type (GET or POST) specific for a request id  
        1. uint256 _requestId, the request id  

           __Note:__ can be executed only by approved member  
    - __getAllowedOwnerMembers__ returns if an address is a member and can call the Apibase contract  
        1. address _member      
        
            __Note:__ can be executed only by contract owner  
    - __addOwnerMember__ enables the member address to call the Apibase contract  
        1. address _memberAddress  
        
            __Note:__ can be executed only by contract owner         
    - __disableOwnerMember__ disables the member address from calling the Apibase contract  
        1. address _memberAddress  
              
            __Note:__ can be executed only by contract owner  

    - __setGasPayAddress__ sets the account address that records the payments for response calls from Apibase contract to the calling contract  
        1. address _address   
        
           __Note:__ can be executed only by contract owner  
    - __getGasPayAddress__ returns the account address that records the payments for response calls from Apibase contract to the calling contract  
              
        __Note:__ can be executed only by approved member  

## Custom contract:  
The custom contract is the contract that connects and creates a communication to the Apibase contract.   
The response from the Apibase contract comes back and it is written    

+ ___Mandatory methods:___               
    - __updateValue__  used by Apibase java monitor to send back the response for the callApi call    
        1. 
        2. string _result  

The custom contract must contain the below Apibase interface:   

```
contract ApiBase {  
    function queryApiBase(address _callingContract, uint256 _type, string _method, string _url, string _params);  
    function updateApiResponse(uint256 _requestId, string _response);  
}  
```

## Java Application   

Contains two parts:  

a. a program to monitor the Apibase smart contract  

b. an application to open API endpoints to write and read responses correlated to the Apibase requests  

### In details:
#### a. Program to monitor the Apibase smart contract is called SmartMonitor  

Requirements:  
1. Install Java
    
    ```
    sudo apt-get update
    sudo apt-get install default-jre
    ```
2. Install mongodb

    ```
    sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 2930ADAE8CAF5059EE73BB4B58712A2291FA4AD5
    echo "deb http://repo.mongodb.org/apt/ubuntu xenial/mongodb-org/3.6 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-3.6.list
    sudo apt-get update
    sudo apt-get install -y mongodb-org
    systemctl start mongod
    systemctl enable mongod
    ``` 
    
    Configure MongoDB username and password
    + Step 1 - Open mongo shell
    ```
    mongo
    ```
    + Step 2 - Switch to the database admin
    ```
    use admin
    ```
    + Step 3 - Create the root user
    ```
    db.createUser({user:"myUser", pwd:"myPass", roles:[{role:"root", db:"admin"}]})        
    ```
    + Step 4 - Enable mongodb authentication
    ```   
    nano /lib/systemd/system/mongod.service
    systemctl daemon-reload        
    ```
    
    + Step 5 - Restart MongoDB and try to connect
    ```
    sudo service mongod restart        
    ```
    
    Fill in the user and the pass credentials in the java application ApibaseMonitor>classes>DatabaseHelper.class
    
3. Open three terminal windows and go to project location:
   
   + terminal 1
       ```
       geth 
       ```
   + terminal 2
       ```
        java -jar SmartMonitor.jar param1 param2
       ```   
       **where**:  
        _param1_ is the contract address and  
        _param2_ is the account[0] private key
   
       Both params are in terminal 2 window at run time. 

#### b. Application to open API endpoints to write and read responses correlated to the Apibase requests:

_Requirements:_

+ install sbt

    ```
    echo "deb https://dl.bintray.com/sbt/debian /" | sudo tee -a /etc/apt/sources.list.d/sbt.list
    sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 2EE0EA64E40A89B84B2DF73499E82A75642AC823
    sudo apt-get update
    sudo apt-get install sbt
    ```

+ Open new terminal window and Go to the application folder and then execute:

    ```
    sbt run
    ```