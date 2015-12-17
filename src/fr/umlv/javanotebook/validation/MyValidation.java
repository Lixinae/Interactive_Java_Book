package fr.umlv.javanotebook.validation;

import jdk.jshell.*;

import java.util.List;

import jdk.jshell.Snippet.Status;

public class MyValidation {
	
	/* Enter some Java code: 2+2
		Successful addition of 2+2
		Value is: 4
		
		 Enter some Java code: prout
		Failed addition of prout
		
		 Enter some Java code: public int aff(){ return 0; }
		Successful addition of public int aff(){ return 0; }
		
		 Enter some Java code: aff()
		Successful addition of aff()
		Value is: 0
		
		 Enter some Java code: 
		 package fr.umlv.javanotebook.validation;

import jdk.jshell.*;

import java.util.List;

import jdk.jshell.Snippet.Status;

class Validation {
	public String accept(String input) {
		StringBuilder sbrow = new StringBuilder();
		try (JShell js = JShell.create()) {
			do {
				if (input == null) {
					break;
				}
				List<SnippetEvent> events = js.eval(input);
				for (SnippetEvent e : events) {
					StringBuilder sb = new StringBuilder();
					if (e.causeSnippet() == null) {
						//  We have a snippet creation event
						switch (e.status()) {
						case VALID:
							sb.append("Successful ");
							break;
						case RECOVERABLE_DEFINED:
							sb.append("With unresolved references ");
							break;
						case RECOVERABLE_NOT_DEFINED:
							sb.append("Possibly reparable, failed  ");
							break;
						case REJECTED:
							sb.append("Failed ");
							break;
						default:
							new IllegalStateException();
							break;
						}
						if (e.previousStatus() == Status.NONEXISTENT) {
							sb.append("addition");
						} else {
							sb.append("modification");
						}
						sb.append(" of ");
						sb.append(e.snippet().source());
						sbrow.append(sb.toString());
						if (e.value() != null) {
							sbrow.append("Value is: "+e.value()+"\n");
						}
						System.out.flush();
					}
				}
			} while (true);
		}
		return sbrow.toString();
	}
}
 */
	
	public static String accept(String numExo,String input) {
		StringBuilder sbrow = new StringBuilder();
		try (JShell js = JShell.create()) {
			//do {
//				if (input == null) {
//					break;
//				}
				List<SnippetEvent> events = js.eval(input);
				for (SnippetEvent e : events) {
					StringBuilder sb = new StringBuilder();
					if (e.causeSnippet() == null) {
						//  We have a snippet creation event
						switch (e.status()) {
						case VALID:
							sb.append("Successful ");
							break;
						case RECOVERABLE_DEFINED:
							sb.append("With unresolved references ");
							break;
						case RECOVERABLE_NOT_DEFINED:
							sb.append("Possibly reparable, failed  ");
							break;
						case REJECTED:
							sb.append("Failed ");
							break;
						default:
							new IllegalStateException();
							break;
						}
						if (e.previousStatus() == Status.NONEXISTENT) {
							sb.append("addition");
						} else {
							sb.append("modification");
						}
						sb.append(" of ");
						sb.append(e.snippet().source());
						sbrow.append(sb.toString());
						if (e.value() != null) {
							sbrow.append("Value is: "+e.value()+"\n");
						}
						System.out.flush();
					}
				}
			//} while (true);
		}
		return sbrow.toString();
	}

	/**
	 * 
	 * @param id : ID of the exercice
	 * @return : string with html format
	 */
}
