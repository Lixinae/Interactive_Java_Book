package fr.umlv.javanotebook.configuration;

public class Server{

	final int port; // port du server
	final String adress; // nom du serveur , localhost car on ne travail que en local 
	
	public Server(){
		this.adress="localhost";
		this.port=8989;
	}
	
	// Permet de lancer le serveur
	public void start(){
		
		
	}
	
	// Affiche l'url du serveur sur le terminal
	public void print_url(){
		System.out.println(this.adress+" "+this.port);

	}
	
	public boolean verify(){
		return true;
	}
	


}