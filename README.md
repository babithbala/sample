					Report
Its a maven projects , please update the maven repository before building the application

Sample JSON format, I have created for the REST service
{
  "title": "Trade Title",
  "description": "Trade Description",
  "products": [
    {
      "option": "BUY", // accepted values BUY or SELL
      "productType": "CURRENCY",  // accepted values CURRENCY or TRAVEL_CARD or TRANSFER
      "counterparty": "PLUTO1",   // accepted values PLUTO1 or PLUTO2
      "tradeInfo": {
        "to": "EUR",
        "from": "USD",
        "rate": "0.88747",
		"valueDate": "08/12/2016",
		"tradeDate": "08/09/2016",
        "currentDate": "09/10/2016",
        "tradeType": "OPTION", //accepted values ALL , SPOT, FORWARD or OPTION
        "tradeStyle": "EUR", // accepted values EUR or USD
        "excerciseStartDate": "08/09/2016",
        "expiryDate": "08/10/2016",
        "premiumDate": "08/08/2016",
        "deliveryDate": "08/09/2016"
      }
    }
  ]
}

** Actually for predefined accepted values , I need to convert use enum objects, but there were some bugs while using enum. so for now I am using String for those properties and validate it inside validating classes.

The rest controller class is CodetestRestController.java and the service method is named tradingService , which accepts and produces JSON.

Service exposed url is http://localhost:8080/mavenRestApp/task/CS 

JSON validator classes are TradeValidator.java, ProductValidator.java and TradeInfoValidator.java . You can find the validation rules inside these class methods and inside corresponding Java POJO classes


