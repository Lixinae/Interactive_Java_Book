package fr.umlv.javanotebook.validation;

import java.util.ArrayList;
import java.util.List;

public class Validations {
	private final List<Validation> validations = new ArrayList<>();
	public Validations() {
		
	}
	public boolean add(Validation val){
		return validations.add(val);
	}
	public int length() {
		return validations.size();
	}
	public Validation get(int index) {
		return validations.get(index);
	}
}
