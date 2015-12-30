package fr.umlv.javanotebook.validation;

import jdk.jshell.JShell;
import jdk.jshell.SnippetEvent;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * this class is used for validate a String for Java Language. he use the method
 * valid for validate the exercise. necessary: String of answer for the exercise
 * and list methods for test the exercise example: Validation valid = new
 * Validation() valid.valid(stringInput,listMethodsTest);
 */
public class Validation {
	private final ReentrantLock rlock = new ReentrantLock();
	private final Condition condition = rlock.newCondition();
	private final JShell js;
	private String input = null;
	private ByteArrayOutputStream out;
	private int nbWait = 0;

	public Validation() {
		js = JShell.builder().out(new PrintStream(out)).build();
	}

	/**
	 * valid an answer for java code,
	 * @param input The answer of the user to the given exercise
	 * @param methods List of Method Test the answer of the user
	 * @return Return String explain the error of answer,
	 * the method test failed, or "good answer" if method doesnt
	 * failed and Java code haven't error. 
	 */
	public String valid(String input, List<Method> methods) {
		List<String> list_input = splitInput(input);
		for (String toEval : list_input) {
			addInQueue(toEval);
			for (SnippetEvent e : js.eval(toEval)) {
				if (!accept(e)) {
					js.close();
					return status(e);
				}
			}
			reset();
		}

		for (Method m : methods) {
			if (m.getReturnType() != boolean.class) {
				throw new IllegalStateException(
						"This should never happen, Test isnt boolean type");
			} 
			else {
				if (!(boolean) invok(m, m.getClass(), js,out)) {
					js.close();
					return "Bad answer method " + m.getName();
				}
			}

		}
		js.close();
		return "Good answer";
	}

	private static Object invok(Method m, Object obj, JShell js,ByteArrayOutputStream out) {
		try {
			return m.invoke(obj, js,out);
		} catch (SecurityException | IllegalAccessException
				| IllegalArgumentException e) {
			throw new AssertionError();
		} catch (InvocationTargetException e) {
			Throwable cause = e.getCause();
			if (cause instanceof RuntimeException) {
				throw (RuntimeException) cause;
			}
			if (cause instanceof Error) {
				throw (Error) cause;
			}
			throw new UndeclaredThrowableException(cause);
		}

	}

	private void addInQueue(String input) {
		Objects.requireNonNull(input);
		rlock.lock();
		try {
			while (this.input != null) {
				nbWait++;
				try {
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

	private List<String> splitInput(String programInput) {
		List<String> listInput = new ArrayList<>();
		int nbCrochet = -1;
		StringBuilder oneInput = new StringBuilder();
		for (int i = 0; i < programInput.length(); i++) {
			oneInput.append(programInput.charAt(i));
			if (programInput.charAt(i) == '}') {
				if (nbCrochet > 0) {
					nbCrochet--;
				} 
				else {
					listInput.add(oneInput.toString());
					oneInput.delete(0, oneInput.length());
					nbCrochet = -1;
				}
			} 
			else if (programInput.charAt(i) == '{') {
				nbCrochet++;
			} 
			else if (programInput.charAt(i) == ';' && nbCrochet == -1) {
				listInput.add(oneInput.toString());
				oneInput.delete(0, oneInput.length());
			}
		}
		listInput.add(oneInput.toString());
		return listInput;
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
				throw new IllegalStateException("invalid status");
			}
		}
		throw new IllegalStateException("SnippetEvent argument can't have causeSnippet null");
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
		throw new IllegalStateException(
				"valid when accept return false (invalid code)");
	}
}