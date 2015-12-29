package fr.umlv.javanotebook.test;

import java.util.ArrayList;
import java.util.List;

import jdk.jshell.JShell;
import jdk.jshell.SnippetEvent;

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

	public static boolean test_3(JShell js) {
		List<String> ListToEval = new ArrayList<>();
		ListToEval.add("Soldier s = new Soldier();");
		ListToEval.add("Soldier s2 = new Soldier();");
		ListToEval.add("s.hit(s2)");
		for(String toEval:ListToEval){
			List <SnippetEvent> listEvent = js.eval(toEval);
			for(SnippetEvent event:listEvent){
				if(event.causeSnippet()!=null){
					switch(event.status()){
					case VALID:
						break;
					default:
						return false;
					}
				}
			}
		}
		return true;
	}
}
