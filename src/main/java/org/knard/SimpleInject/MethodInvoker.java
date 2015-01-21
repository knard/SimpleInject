package org.knard.SimpleInject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodInvoker {

	private final Injector[] injectors;
	private final Method method;

	public MethodInvoker(final Method method, final Injector[] injectors) {
		this.injectors = injectors;
		this.method = method;
	}

	public void invoke(final Context ctx, final Object instance) {
		final Object[] params = new Object[this.injectors.length];
		for (int i = 0; i < this.injectors.length; i++) {
			params[i] = this.injectors[i].inject(ctx);
		}
		try {
			this.method.invoke(instance, params);
		} catch (final IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
