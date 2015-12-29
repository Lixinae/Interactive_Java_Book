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

	/**
	 * @param js
	 * @return
	 */
	public static boolean test_3(JShell js) {
		List<String> ListToEval = new ArrayList<>();
		ListToEval.add("Soldier s = new Soldier(10,10);");
		ListToEval.add("Soldier s2 = new Soldier(15,15);");
		ListToEval.add("s.hit(s2);");
		return eval(js, ListToEval);
	}

	private static boolean eval(JShell js, List<String> listToEval) {
		for (String toEval : listToEval) {
			List <SnippetEvent> listEvent = js.eval(toEval);
			if (eval_annexe(listEvent)) {
				return false;
			}
		}
		return true;
	}

	private static boolean eval_annexe(List<SnippetEvent> listEvent) {
		for (SnippetEvent event : listEvent) {
			if (event.causeSnippet() == null) {
				switch (event.status()) {
					case VALID:
						break;
					default:
						return true;
				}
			} else {
				return true;
			}
		}
		return false;
	}
}
