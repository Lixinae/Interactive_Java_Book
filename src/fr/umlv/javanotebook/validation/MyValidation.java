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

	final BlockingQueue<String> queue;

	public MyValidation() {
		queue = new ArrayBlockingQueue<String>(10);
	}

	public void addInQueue(String input){
		Objects.requireNonNull(input);
		try {
			queue.put(input);
		} catch (InterruptedException|ClassCastException|IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	
	/*
		String input = takeFromQueue();
		StringBuilder sbrow = new StringBuilder();
		try (JShell js = JShell.create()) {
				List<SnippetEvent> events = js.eval(input);
				for (SnippetEvent e : events) {
					StringBuilder sb = new StringBuilder();
					if (e.causeSnippet() == null) {
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
		*/
	/**
	 * 
	 * @return return true if code is valid, false else.
	 */
	public boolean accept() {
		String input = takeFromQueue();
		try (JShell js = JShell.create()) {
				List<SnippetEvent> events = js.eval(input);
				for (SnippetEvent e : events) {
					if (e.causeSnippet() == null) {
						switch (e.status()) {
						case VALID:
							continue;
						case RECOVERABLE_DEFINED:
							return false;
						case RECOVERABLE_NOT_DEFINED:
							return false;
						case REJECTED:
							return false;
						default:
							throw new IllegalStateException();
						}
					}
				}
		}
		return true;
	}
	
	public String validate(){
		String input = takeFromQueue();
		StringBuilder sbrow = new StringBuilder();
		try (JShell js = JShell.create()) {
				List<SnippetEvent> events = js.eval(input);
				for (SnippetEvent e : events) {
					StringBuilder sb = new StringBuilder();
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
