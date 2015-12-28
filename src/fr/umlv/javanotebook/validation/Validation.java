package fr.umlv.javanotebook.validation;

import jdk.jshell.JShell;
import jdk.jshell.Snippet.Status;
import jdk.jshell.SnippetEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * this class is used for validate a String for Java Language.
 * example:
 * Validation valid = new Validation()
 * val.addInQueue("public void test(){}");
 * if (!valid.accept()) {
 * routingContext.response().end(val.status());
 * }
 * else {
 * System.out.println(val.validate());
 * }
 * val.reset();
 */
public class Validation {
    private final ReentrantLock rlock = new ReentrantLock();
    private final Condition condition = rlock.newCondition();
    private final JShell js;
    private String input = null;
    private int nbWait = 0;

	public Validation() {
		js = JShell.create();
	}

	/**
	 * add the answer of user in input (thread-safe)
	 *
	 * @param input is the answer of user.
	 */
	private void addInQueue(String input) {
		Objects.requireNonNull(input);
		rlock.lock();
		try {
			while (this.input != null) {
				try {
					nbWait++;
					condition.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			this.input = input;
		} finally {
			rlock.unlock();
		}
	}

	private void reset() {
		input = null;
		if (nbWait > 0) {
			condition.signal();
			nbWait--;
		}
	}

	/**
	 * @param input The answer of the user to the given exercise
	 * @return Returns the answer of JShell on the input
	 */
	public String valid(String input){
		StringBuilder b = new StringBuilder();
		List<String> list_input = snippetInput(input);
		for (String toEval : list_input) {
			addInQueue(toEval);
			for (SnippetEvent e : js.eval(toEval)) {
				if(!accept(e)){
					js.close();
					return status(e);
				}
				else {
					b.append(validate(e));
				}
			}
			reset();
		}
		js.close();
		return b.toString();
	}

	private List<String> snippetInput(String input) {
		List<String> b = new ArrayList<>();
		int nbCrochet = -1;
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < input.length(); i++) {
			builder.append(input.charAt(i));
			if (input.charAt(i) == '}') {
				if (nbCrochet > 0) {
					nbCrochet--;
				} else {
					b.add(builder.toString());
					builder.delete(0, builder.length());
					nbCrochet = -1;
				}
			} 
			else if (input.charAt(i) == '{') {
				nbCrochet++;
			}
			else if(input.charAt(i) == ';' && nbCrochet == -1){
				b.add(builder.toString());
				builder.delete(0, builder.length());
			}
		}
		b.add(builder.toString());
		return b;
	}

	private boolean accept(SnippetEvent e) {
		if (e.causeSnippet() == null) {
			switch (e.status()) {
			case VALID:
				return true;
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
		throw new IllegalStateException();
	}

	private String status(SnippetEvent e) {
		if (e.causeSnippet() == null) {
			switch (e.status()) {
			case VALID:
				break;
			case RECOVERABLE_DEFINED:
				return "Code with unresolved references";
			case RECOVERABLE_NOT_DEFINED:
				return "Code possibly reparable, but failed";
			case REJECTED:
				return "Code failed";
			default:
				throw new IllegalStateException("internal error");
			}
		}
		throw new IllegalStateException("valid when accept return false (invalid code)");
	}

	private String validate(SnippetEvent e) {
		StringBuilder sbrow = new StringBuilder();
		StringBuilder sb = new StringBuilder();

		if (e.previousStatus() == Status.NONEXISTENT) {
			sb.append("addition");
		} else {
			sb.append("modification");
		}
		sb.append(" of ");
		sb.append(e.snippet().source());
		sbrow.append(sb.toString());
		// We do want the value of M test = new M(); for example , which contains an @ in the value
		String pattern = "[^@]+";
		if ((e.value() != null) && e.value().matches(pattern)) {
			sbrow.append(" ").append(e.value());
		}
		System.out.flush();

		// delete before sending
		System.out.println("In function validate " + sbrow);
		return sbrow.toString();

	}

	/*private String takeFromQueue() {
        String tmp=null;
		try {
			tmp=queue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return Objects.requireNonNull(tmp);
	}*/


}