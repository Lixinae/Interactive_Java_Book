package fr.umlv.javanotebook.test;

import jdk.jshell.JShell;
import jdk.jshell.SnippetEvent;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Test_Exo {
	/**
	 * this is the method for test if answer's user
	 * correspond exercise's request.
	 * @param js is JShell of the answer's user,
	 * he save all method of the answer.
	 * @return test working or not.
	 */
	public static boolean test_0(JShell js,ByteArrayOutputStream out) {
		List<String> ListToEval = new ArrayList<>();
		ListToEval.add("Main.helloWorld()");
		boolean bEval = eval(js, ListToEval);
		return out.toString().compareTo("HelloWorld")==0?bEval:false;
	}
	/**
	 * this is the method for test if answer's user
	 * correspond exercise's request.
	 * @param js is JShell of the answer's user,
	 * he save all method of the answer.
	 * @return test working or not.
	 */
	public static boolean test_1(JShell js,ByteArrayOutputStream out) {
		List<String> ListToEval = new ArrayList<>();
		return eval(js, ListToEval);
	}
	/**
	 * this is the method for test if answer's user
	 * correspond exercise's request.
	 * @param js is JShell of the answer's user,
	 * he save all method of the answer.
	 * @return test working or not.
	 */
	public static boolean test_2(JShell js,ByteArrayOutputStream out) {
		List<String> ListToEval = new ArrayList<>();
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

	/**
	 * this is the method for test if answer's user
	 * correspond exercise's request.
	 * @param js is JShell of the answer's user,
	 * he save all method of the answer.
	 * @return test working or not.
	 */
	public static boolean test_3(JShell js,ByteArrayOutputStream out) {
		List<String> ListToEval = new ArrayList<>();
		ListToEval.add("Soldier s = new Soldier(10,10);");
		ListToEval.add("Soldier s2 = new Soldier(15,15);");
		ListToEval.add("s.hit(s2);");
		return eval(js, ListToEval);
	}

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
