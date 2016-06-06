package fr.umlv.javanotebook.validation;

import jdk.jshell.JShell;
import jdk.jshell.SnippetEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * this class is used for validate a String for Java Language. he use the method
 * valid for validate the exercise. necessary: String of answer for the exercise
 * and list methods for test the exercise example: ValidationImpl valid = new
 * ValidationImpl() valid.valid(stringInput,listMethodsTest);
 */
public class ValidationImpl implements Validation {
    private final ReentrantLock rlock = new ReentrantLock();
    private final Condition condition = rlock.newCondition();
    private final JShell js;
    private String input;
    private int nbWait = 0;

    public ValidationImpl() {
        js = JShell.create();
    }

    /**
     * valid an answer for java code,
     *
     * @param userInput The answer of the user to the given exercise
     * @param testInput The code used to test the exercise
     * @return Return String explain the error of answer,
     * the method test failed, or "good answer" if method doesn't
     * failed and Java code haven't error.
     */
    public String valid(String userInput, String testInput) {
        if (validateInput(userInput, true) && validateInput(testInput, false)) {
            return "Good Answer";
        }
        return "Your code doesn't work with the test input";
    }

    private boolean validateInput(String input, boolean user) {

        List<String> list_input;
        if (user) {
            list_input = splitUserInput(input);
        } else {
            list_input = splitTestInput(input);
        }

        for (String toEval : list_input) {
            addInQueue(toEval);
            for (SnippetEvent e : js.eval(toEval)) {
                if (!accept(e)) {
                    js.close();
                    return false;
                }
            }
            reset();
        }
        return true;
    }


    private void addInQueue(String input) {
        rlock.lock();
        try {
            while (this.input != null) {
                nbWait++;
                condition.await();
            }
            this.input = Objects.requireNonNull(input);
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
    private List<String> splitUserInput(String programInput) {
        List<String> listInput = new ArrayList<>();
        StringBuilder tmpInput = new StringBuilder();
        int nbCrochet = -1;
        for (int i = 0; i < programInput.length(); i++) {
            tmpInput.append(programInput.charAt(i));
            nbCrochet = splitInputAnnexe(listInput, tmpInput, programInput.charAt(i), nbCrochet);
        }
        listInput.add(tmpInput.toString());
        return listInput;
    }

    private int splitInputAnnexe(List<String> listInput, StringBuilder tmpInput, char token, int nbCrochet) {
        if (token == '}') {
            nbCrochet = nbCrochet > 0 ? nbCrochet - 1 : -1;
            if (nbCrochet == -1) {
                addTmpInputOnList(listInput, tmpInput);
            }
        } else if (token == '{') {
            nbCrochet++;
        } else if (token == ';' && nbCrochet == -1) {
            addTmpInputOnList(listInput, tmpInput);
        }
        return nbCrochet;
    }

    // TODO Need to update the split input for the testInput
    private List<String> splitTestInput(String input) {
        List<String> listInput = new ArrayList<>();
        return listInput;
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
}