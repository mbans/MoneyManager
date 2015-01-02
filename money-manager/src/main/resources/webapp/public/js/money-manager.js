//Defining the Route Module of our application
//This Creates the module, and injects the required dependancies
var configuration = {
		baseUrl:"http://localhost:4567"
}

var moneyManagerApp = angular.module('money-manager', ['ngLocale','ngGrid','ngRoute'] );

