package fr.umlv.javanotebook.test;

import jdk.jshell.JShell;
import jdk.jshell.SnippetEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * This class must never be used alone !!!
 * If used alone it will crash
 */
public class Test_Exo {


	public static boolean test_0(JShell js) {
		List<String> ListToEval = new ArrayList<>();


		return eval(js, ListToEval);
	}

	public static boolean test_1(JShell js) {
		List<String> ListToEval = new ArrayList<>();


		return eval(js, ListToEval);
	}

	public static boolean test_2(JShell js) {
		List<String> ListToEval = new ArrayList<>();
		ListToEval.add("Operations o = new Operations();");
		ListToEval.add("o.add(2,3);");
		ListToEval.add("o.sub(2,3);");
		ListToEval.add("o.divide(2,3)");
		ListToEval.add("o.multiply(2,3);");

		return eval(js, ListToEval);
	}

	public static boolean test_3(JShell js) {
		List<String> ListToEval = new ArrayList<>();
		ListToEval.add("Soldier s = new Soldier(10,10);");
		ListToEval.add("Soldier s2 = new Soldier(15,15);");
		ListToEval.add("s.hit(s2);");
		return eval(js, ListToEval);
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

	private static boolean eval(JShell js, List<String> listToEval) {
		if(listToEval.isEmpty()){
			throw new IllegalStateException("empty test");
		}
		for (String toEval : listToEval) {
			List <SnippetEvent> listEvent = js.eval(toEval);
			if (eval_error(listEvent)) {
				return false;
			}
		}
		return true;
	}

	private static boolean eval_error(List<SnippetEvent> listEvent) {
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
