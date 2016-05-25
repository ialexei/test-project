package com.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Wrap attributes of an immutable class that you are not sure
 * are also immutable. This is similar to Collection.unmodifiableXXX
 * 
 * Caveat 1: Class assumes the only methods that change state are the setXXX methods
 * Caveat 2: Class takes in an interface
 *   
 * @author ialexei
 *
 * @param <TInterface>
 */
public final class Immutable<TInterface> {

	private final TInterface object;
	
	@SuppressWarnings("unchecked")
	public Immutable(
			final TInterface innerObject,
			Class<TInterface> tClass) {
		
		this.object = (TInterface)Proxy.newProxyInstance(
				innerObject.getClass().getClassLoader(), 
				new Class<?>[] {tClass}, 
				new InvocationHandler() {
					
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						
						if (method.getName().startsWith("set")) {
							throw new IllegalAccessError(method.getName()+" changes internal state of the object.");
						}
						
						return method.invoke(innerObject, args);
					}
				});
	}
	
	public TInterface get() {
		return object;
	}
}
