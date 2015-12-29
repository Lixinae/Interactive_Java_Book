package fr.umlv.javanotebook.test;

import jdk.jshell.JShell;
import jdk.jshell.SnippetEvent;

import java.util.ArrayList;
import java.util.List;

public class Test_Exo {
	public static boolean test_0(JShell js) {
		return true;
	}

	public static boolean test_1(JShell js) {
		return false;
	}

	public static boolean test_2(JShell js) {
		return true;
	}


	/*
	public class Soldier{
 		private int health;
 		private int damage;

  		public Soldier(int health,int damage){
    		this.health=health;
    		this.damage=damage;
  		}

 		public void hit(Soldier soldier){
   			soldier.health = soldier.health - damage;
  		}

	}


	 */

	public static boolean test_3(JShell js) {
		List<String> ListToEval = new ArrayList<>();
		ListToEval.add("Soldier s = new Soldier(10,10);");
		ListToEval.add("Soldier s2 = new Soldier(15,15);");
		ListToEval.add("s.hit(s2);");

		//System.out.println("Js "+js.types());
		for(String toEval:ListToEval){
			//System.out.println("toEval = "+toEval);
			List <SnippetEvent> listEvent = js.eval(toEval);
			for(SnippetEvent event:listEvent){
				//System.out.println("event ="+event);
				if (event.causeSnippet() == null) {
					//System.out.println("Event status ="+event.status());
					switch(event.status()){
					case VALID:
						break;
					default:
						return false;
					}
				} else {
					return false;
				}
			}
		}
		return true;
	}
}
