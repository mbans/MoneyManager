<?php 
// check if fields passed are empty 
include "class.smtp.php";
require "PHPMailerAutoload.php";

	//Sends from robinbansillustration@gmail.com to robinbans@hotmail.com and lumarmacy@gmail.com
	$name=$_POST["name"];
	$customerEmail=$_POST["email"];
	$message=$_POST["message"];
	
	$mail = new PHPMailer();		 // create a new object
	$mail->IsSMTP(); 				 // enable SMTP
	$mail->SMTPDebug = 1; 			 // debugging: 1 = errors and messages, 2 = messages only
	$mail->SMTPAuth = true; 		 // authentication enabled
	$mail->SMTPSecure = 'ssl'; 		 // secure transfer enabled REQUIRED for GMail
	$mail->Host = "smtp.gmail.com";
	$mail->Port = 465; 				 // or 587
	$mail->IsHTML(false);
	$mail->Username = 'robinbansillustration@gmail.com'; 
	$mail->Password = 'lionsdale7';
	$mail->SetFrom("robinbansillustration@gmail.com");

	$mail->AddReplyTo($customerEmail);
	$mail->AddAddress("robinbans@hotmail.com"); 
	$mail->AddAddress("lumarmacy@gmail.com"); 
	$mail->Body = "Hi Robin, \nYou have had an enquiry from " . $name . ". Please respond to them at " . $customerEmail . ". \n\n The message they left is below \n\n" . $message;
	$mail->Subject = "Enquiry on robinbans.com from "  . $name;
	
	if(!$mail->Send()) {
		return "Mailer Error: " . $mail->ErrorInfo;
	}
	else {
		return "Message has been sent";
	}
 ?>