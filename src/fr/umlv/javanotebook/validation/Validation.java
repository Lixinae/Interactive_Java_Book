package fr.umlv.javanotebook.validation;

import jdk.jshell.JShell;
import jdk.jshell.SnippetEvent;

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
	private int nbWait = 0;

	public Validation() {
		js = JShell.create();
	}

	// Cannot go lower than all these lignes , since we must catch all exceptions on the invoke method
	private static Object invok(Method m, Object obj, JShell js) {
		try {
			return m.invoke(obj, js);
		} catch (SecurityException | IllegalAccessException | IllegalArgumentException e) {
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

	/**
	 * valid an answer for java code,
	 * @param input The answer of the user to the given exercise
	 * @param methods List of Method Test the answer of the user
	 * @return Return String explain the error of answer,
	 * the method test failed, or "good answer" if method doesn't
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
		return sendAnswerToServer(methods);
	}

	private String sendAnswerToServer(List<Method> methods) {
		for (Method m : methods) {
			if (m.getReturnType() != boolean.class) {
				throw new IllegalStateException("This should never happen, Test isn't boolean type");
			}
			else {
				// This cast is here because the methods in the class are only boolean
				if (!(boolean) invok(m, m.getClass(), js)) {
					js.close();
					return "Bad answer method " + m.getName();
				}
			}
		}
		js.close();
		return "Good answer";
	}

	private void addInQueue(String input) {
		rlock.lock();
		try {
			while (this.input != null) {
				nbWait++;
				condition.await();
			}
			this.input =Objects.requireNonNull(input);
		} catch (InterruptedException e) {
			throw new AssertionError(e);
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

	// Splits the input so we can use it correctly
	private List<String> splitInput(String programInput) {
		List<String> listInput = new ArrayList<>();
		StringBuilder tmpInput = new StringBuilder();
		int nbCrochet = -1;
		for (int i = 0; i < programInput.length(); i++) {
			tmpInput.append(programInput.charAt(i));
			nbCrochet = splitInputAnnexe( listInput,tmpInput,programInput.charAt(i),nbCrochet);
		}
		listInput.add(tmpInput.toString());
		return listInput;
	}

	private int splitInputAnnexe( List<String> listInput, StringBuilder tmpInput, char token,int nbCrochet) {
		if (token == '}') {
			nbCrochet=nbCrochet>0?nbCrochet-1:-1;
			if(nbCrochet==-1){
				addTmpInputOnList(listInput, tmpInput);
			}
		}
		else if (token == '{') {
			nbCrochet++;
		}
		else if (token == ';' && nbCrochet == -1) {
			addTmpInputOnList(listInput, tmpInput);
		}
		return nbCrochet;
	}

	private void addTmpInputOnList(List<String> listInput, StringBuilder oneInput) {
		listInput.add(oneInput.toString());
		oneInput.delete(0, oneInput.length());
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
		throw new IllegalStateException("This should never happen,SnippetEvent argument can't have causeSnippet null");
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
		throw new IllegalStateException("This should never happen,valid when accept return false (invalid code)");
	}
}