package fr.umlv.javanotebook.validation;

import jdk.jshell.JShell;

public class Validation {
	public boolean valid(String answer){
		String[] copy = answer.split(" ");
		int it = 0;
		for (int i=0;i<copy.length;i++){
			if (copy[i].equals("return")){
				it=i;
			}
		}
		if(Integer.parseInt(copy[it+1])==4){
			return true;
		}
		return false;
	}
	public static void main(String[] args) {
		
	}
}
