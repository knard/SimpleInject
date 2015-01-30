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
