//Defining the Route Module of our application
//This Creates the module, and injects the required dependancies
var configuration = {
		baseUrl:"http://localhost:5678",
		user:"martin"
			
}

var moneyManagerApp = angular.module('money-manager', ['ngLocale','ui.grid','ui.grid.edit', 'ngRoute'] );