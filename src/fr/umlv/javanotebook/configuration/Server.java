package fr.umlv.javanotebook.configuration;

public class Server{

	final int port; // port du server
	final String adress; // nom du serveur , localhost car on ne travail que en local 
	
	public Server(int port){
		adress="localhost";
		this.port=port;
	}
	
	// Permet de lancer le serveur
	public void launch(){
		
		
	}
	// Affiche l'url du serveur sur le terminal
	public void print_url(){
		System.out.println();

	}
	
	


}