package fr.umlv.javanotebook.validation;

import jdk.jshell.JShell;
import jdk.jshell.Snippet.Status;
import jdk.jshell.SnippetEvent;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Project :Interactive_Java_Book
 * Created by kbondel on 21/12/2015.
 */
public class MyValidation {

	final BlockingQueue<String> queue = new ArrayBlockingQueue<String>(10);
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

	public MyValidation() {

	}

	public void addInQueue(String input){
		Objects.requireNonNull(input);
		try {
			queue.put(input);
		} catch (InterruptedException|ClassCastException|IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	public String accept(String numExo) {
		System.out.printf(numExo); // Ligne juste pour eviter que l'IDE m'emmerde.
		String input = takeFromQueue();
		StringBuilder sbrow = new StringBuilder();
		try (JShell js = JShell.create()) {
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
							throw new IllegalStateException();
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
							sbrow.append("Value is: ").append(e.value()).append("\n");
						}
						System.out.flush();
					}
				}
		}
		return sbrow.toString();
	}

	private String takeFromQueue() {
		String tmp=null;
		try {
			tmp=queue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return Objects.requireNonNull(tmp);
	}
}
