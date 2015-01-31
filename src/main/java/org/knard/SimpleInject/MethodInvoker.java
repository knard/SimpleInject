/*
 * Copyright 2015 Pascal Migazzi
 * 
 * This file is part of SimpleInject.
 *
 * SimpleInject is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SimpleInject is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SimplInject.  If not, see <http://www.gnu.org/licenses/>
 */

package org.knard.SimpleInject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Object responsible to invoke a specific method on the target object and to
 * inject value from the context as parameter.
 * 
 * @author Pascal Migazzi
 *
 */
public class MethodInvoker {

	/**
	 * array of <code>Injector</code> in the order of the parameter of the
	 * method that will be invoked.
	 */
	private final Injector[] injectors;

	/**
	 * the method that should be invoked.
	 */
	private final Method method;

	public MethodInvoker(final Method method, final Injector[] injectors) {
		this.injectors = injectors;
		this.method = method;
	}

	/**
	 * invoke the method and inject data from the context as parameter.
	 * 
	 * @param ctx
	 *            the context used for the injection
	 * @param instance
	 *            the object on whihc the method invocation shoudl occure.
	 */
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
