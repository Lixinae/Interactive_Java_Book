//package fr.umlv.javanotebook.test;
//
//import jdk.jshell.JShell;
//import jdk.jshell.SnippetEvent;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//
//public class Test_Exo {
//
//
//	private static boolean test_0(JShell js) {
//		List<String> ListToEval = new ArrayList<>();
//		ListToEval.add("Hello h = new Hello();");
//		ListToEval.add("h.HelloWorld();");
//		return eval(js, ListToEval) && validate(js, "h.HelloWorld()").compareTo("HelloWorld") == 0;
//	}
//
//	private static boolean test_1(JShell js) {
//		List<String> ListToEval = new ArrayList<>();
//		ListToEval.add("Bidule b = new Bidule();");
//		ListToEval.add("b.truc(5);");
//		return eval(js, ListToEval) && validate(js, "b.truc(5);").compareTo("5_ma super concatenation") == 0;
//	}
//
//	private static boolean test_2(JShell js) {
//		List<String> ListToEval = new ArrayList<>();
//		ListToEval.add("Operations o = new Operations();");
//		ListToEval.add("o.add(2,3);");
//		ListToEval.add("o.sub(2,3);");
//		ListToEval.add("o.divide(2,3);");
//		ListToEval.add("o.multiply(2,3);");
//		return eval(js, ListToEval)
//				&& (validate(js, "o.add(2,3)").compareTo("5") == 0)
//				&& (validate(js, "o.sub(2,3);").compareTo("-1") == 0)
//				&& (validate(js, "o.divide(2,3);").compareTo("0") == 0)
//				&& (validate(js, "o.multiply(2,3);").compareTo("6") == 0);
//	}
//
//	private static boolean test_3_hit(JShell js) {
//		List<String> ListToEval = new ArrayList<>();
//		ListToEval.add("Soldier s = new Soldier(10,10);");
//		ListToEval.add("Soldier s2 = new Soldier(15,15);");
//		ListToEval.add("s.hit(s2);");
//		return eval(js, ListToEval);
//	}
//
//	private static boolean eval(JShell js, List<String> listToEval) {
//		if(listToEval.isEmpty()){
//			throw new IllegalStateException("empty test");
//		}
//		for (String toEval : listToEval) {
//			List <SnippetEvent> listEvent = js.eval(toEval);
//			if (eval_error(listEvent)) {
//				return false;
//			}
//		}
//		return true;
//	}
//
//	private static boolean eval_error(List<SnippetEvent> listEvent) {
//		for (SnippetEvent event : listEvent) {
//			if (event.causeSnippet() == null) {
//				switch (event.status()) {
//					case VALID:
//						break;
//					default:
//						return true;
//				}
//			} else {
//				return true;
//			}
//		}
//		return false;
//	}
//
//	private static String validate(JShell js, String input) {
//		List<SnippetEvent> listEvent = js.eval(input);
//		return Objects.requireNonNull(listEvent.get(0).value());
//	}
//
//}
