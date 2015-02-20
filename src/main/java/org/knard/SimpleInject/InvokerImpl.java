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

/**
 * default implementation of the Invoker. This implementation is using
 * reflection.
 * 
 * @author Pascal Migazzi
 *
 */
public class InvokerImpl implements Invoker {

	/**
	 * All method invoker that should be executed on the target object.
	 */
	private final MethodInvoker[] invokers;

	/**
	 * 
	 * @param invokers
	 *            list of <code>MethodInvoker</code> that will be used during
	 *            invocation.
	 */
	public InvokerImpl(final MethodInvoker[] invokers) {
		this.invokers = invokers;
	}

	public void invokeInContext(final Context ctx, final Object instance) throws InvocationException {
		for (final MethodInvoker invoker : this.invokers) {
			invoker.invoke(ctx, instance);
		}
	}

}
