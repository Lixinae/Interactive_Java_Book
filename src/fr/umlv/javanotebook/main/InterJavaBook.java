package fr.umlv.javanotebook.main;

import fr.umlv.javanotebook.configuration.Server;

public class InterJavaBook {

	
	public static void main(String[] args) {
		Server s = new Server();		
		s.start();
		s.print_url();
	}
}
