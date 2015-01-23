$(document).ready(function() {
	
	//Form validation
    $("#contactForm").validate({
    rules: {
        name: {
            minlength: 2,
            required: true
        },
        email: {
            required: true,
            email: true
        },
        message: {
            minlength: 2,
            required: true
        }
    },
    highlight: function (element) {
        $(element).closest('.control-group').removeClass('success').addClass('error');
    },
    success: function (element) {
        element.addClass('valid')
            .closest('.control-group').removeClass('error').addClass('success');
    },
    
    submitHandler: function(form) {

       var name = $("input#name").val();
       var email = $("input#email").val();
       var message = $("textarea#message").val();
       var firstName = name; 
       
       console.log("Enquiry from "+name +", Email["+email+"] Message["+message+"]");
       
       $("#statusMessage").text("Sending....be patient please");
       
       $.ajax({
              url: "./bin/contact-me.php",
              type: "POST",
              data: {name: name, email: email, message: message},
              cache: false,
          success: function() {
              $("#statusMessage").text("");
        	  bootbox.alert("Your message was successfully sent to Robin :)");
          },
          error: function(error) {
              $("#statusMessage").text("");
        	  bootbox.alert("<center><b>;-( </b><center>" +
        	  		"<br>How embarassing!!" +
        	  		" There appears to be a problem submitting your message. Please send an email to Robin at robinbans@hotmail.com Error="+error);
          }
       	});
   }//Submit handler
});
    
	
//	
//	//Form Submission
//	$("#contactForm").submit(function(e) {
//	   var currentForm=this;
//	   e.preventDefault();
//       var name = $("input#name").val();
//       var email = $("input#email").val();
//       var message = $("textarea#message").val();
//       var firstName = name; 
//       
//       console.log("Enquiry from "+name +", Email["+email+"] Message["+message+"]");
//       
//       $("#statusMessage").text("Sending....be patient please");
//       
//       $.ajax({
//              url: "./bin/contact-me.php",
//              type: "POST",
//              data: {name: name, email: email, message: message},
//              cache: false,
//          success: function() {
//              $("#statusMessage").text("");
//        	  bootbox.alert("Your message was successfully sent to Robin :)");
//          },
//          error: function(error) {
//              $("#statusMessage").text("");
//        	  bootbox.alert("<center><b>;-( </b><center>" +
//        	  		"<br>How embarassing!!" +
//        	  		" There appears to be a problem submitting your message. Please send an email to Robin at robinbans@hotmail.com Error="+error);
//          }
//       });
//  });
  
  
});