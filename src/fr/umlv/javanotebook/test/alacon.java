package fr.umlv.javanotebook.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;



public class alacon {
	static class test{
		

		int bidule;
		public test(){
			bidule = 5;
		}

		public int getBidule() {
			return bidule;
		}
	}
	public static void main(String[] args) {
		test truc = new test();
		Method m;
		try {
			m = test.class.getMethod("getBidule");
				System.out.println(invok(m, truc));
			System.out.println(m.getReturnType());
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static Object invok(Method m,Object obj){
		try {
			return m.invoke(obj).toString();
		}catch(SecurityException | IllegalAccessException | IllegalArgumentException e){
			throw new AssertionError();
		}catch(InvocationTargetException e) {
			Throwable cause = e.getCause() ;
			if (cause instanceof RuntimeException) {
				throw (RuntimeException)cause;
			}
			if (cause instanceof Error) {
				throw (Error)cause;
			}
			throw new UndeclaredThrowableException(cause);
		}

	}
}
