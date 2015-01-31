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

import java.util.HashMap;
import java.util.Map;

/**
 * This class is an implementation of <code>InvokerRepository</code> where the
 * result is cached to improve performance.
 * 
 * @author Pascal Migazzi
 *
 */
public class InvokerRepositoryCached extends InvokerRepository {

	/**
	 * map used to store the result of
	 * <code>org.knard.SimpleInject.InvokerRepository.getInvoker(Class<?>)</code>
	 * call.
	 */
	private final Map<Class<?>, Invoker> invokerMap;

	public InvokerRepositoryCached() {
		this.invokerMap = new HashMap<Class<?>, Invoker>();
	}

	@Override
	public synchronized Invoker getInvoker(final Class<?> c) {
		Invoker invoker = this.invokerMap.get(c);
		if (invoker != null) {
			return invoker;
		}
		invoker = super.getInvoker(c);
		this.invokerMap.put(c, invoker);
		return invoker;
	}

}
