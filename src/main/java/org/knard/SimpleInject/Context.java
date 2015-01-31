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

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class Context {

	private final Map<Class<?>, Set<Object>> ctxByType = new HashMap<Class<?>, Set<Object>>();
	private final Map<String, Object> ctxByName = new HashMap<String, Object>();

	public void add(final Object o) {
		if (o == null) {
			return;
		}
		map(o.getClass(), o);
	}

	private void map(final Class<? extends Object> c, final Object o) {
		add(c, o);
		Class<?> superClass = c.getSuperclass();
		if (superClass != null) {
			map(superClass, o);
		}
		final Class<?>[] interfaces = c.getInterfaces();
		for (final Class<?> i : interfaces) {
			add(i, o);
		}
	}

	private void add(final Class<? extends Object> c, final Object o) {
		Set<Object> instances = this.ctxByType.get(c);
		if (instances == null) {
			instances = new HashSet<Object>();
			this.ctxByType.put(c, instances);
		}
		instances.add(o);
	}

	public Object getInstance(final Class<?> c) {
		final Set<Object> instances = this.ctxByType.get(c);
		if (instances != null) {
			try {
				return instances.iterator().next();
			} catch (NoSuchElementException e) {
				// should never happen because the set is created only when the
				// first element will be added.
			}
		}
		return null;
	}

	public Set<Object> getInstances(final Class<?> c) {
		final Set<Object> instances = this.ctxByType.get(c);
		if (instances != null) {
			return Collections.unmodifiableSet(instances);
		}
		return null;
	}

	public void add(final String name, final Object o) {
		this.ctxByName.put(name, o);
		if (o != null) {
			add(o);
		}
	}

	public Object getInstance(final String name) {
		return this.ctxByName.get(name);
	}
}
