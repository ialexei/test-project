package com.test;

/**
 * This App demonstrates the Immutable 
 * Class
 *
 */
public class App {
	
	/**
	 * This is the interface that will be implementedby the non-immutable class
	 * @author ialexei
	 *
	 */
	public interface NonImmutableIfc {
		
		void setName(String name);
		String getName();
	}
	
	/**
	 * The Non-immutable class
	 * @author ialexei
	 *
	 */
	public class NonImmutable implements NonImmutableIfc {

		private String name = "";
		public void setName(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}
	
	/**
	 * This is the truly immutable class
	 * @author ialexei
	 *
	 */
	public class TrulyImmutable {
	
		private final Immutable<NonImmutableIfc> immutable; 
		
		public TrulyImmutable(NonImmutableIfc nonImmutableIfc) {
			
			this.immutable = 
					new Immutable<App.NonImmutableIfc>(
							new NonImmutable(), 
							NonImmutableIfc.class);
		}
		
		/**
		 * This acutally gives you a proxy and not the real object.
		 * Any calls to setXXX will be intercepted and an exception will be generated.
		 * @return
		 */
		public NonImmutableIfc getNonImmutable() {
			return immutable.get();
		}
	}
	
	public void test() {
		
		TrulyImmutable trulyImmutable = new TrulyImmutable(new NonImmutable());
		try {
			trulyImmutable.getNonImmutable().getName();
		}catch(Throwable ex) {
			throw new IllegalStateException(ex);
		}
		
		trulyImmutable.getNonImmutable().setName("junk");
	}
	
	public static void main(String[] args) {
		
		try {
			new App().test();
			System.out.println("Test failed");
		}catch(IllegalAccessError ex) {
			System.out.println("Test passed");
		}catch(IllegalStateException ex) {
			System.out.println("Test failed");
		}
	}
}
