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
 * 
 * This represent object used to invoke the object and inject correct object
 * from the context.
 * 
 * @author Pascal Migazzi
 *
 */
public interface Invoker {

	/**
	 * Invoke all methods annotated with <code>javax.inject.Inject</code> and
	 * inject parameter with object found in the context.
	 * 
	 * @param ctx
	 *            context used during the injection.
	 * @param instance
	 *            the object from which method should be invoked.
	 * @throws InvocationException 
	 */
	public void invokeInContext(final Context ctx, final Object instance) throws InvocationException;
}
