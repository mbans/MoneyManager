package com.lumar.robinbans;

import static spark.Spark.get;
import static spark.SparkBase.externalStaticFileLocation;
import static spark.SparkBase.setPort;

import java.util.Set;

public class Bootstrapper {

	public static void main(String[] args) {
		setPort(4444);
		externalStaticFileLocation("C:/Users/Martin/git/LumarProjects/robin-bans/src/main/resources/robinbans.kissr.com/public/");
		init();
		System.out.println("Here");
	}

	public static void init() {
		get("/bla", (req, res) -> {
			return "";
		});
	}
}
