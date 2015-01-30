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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Context {

	private final Map<Class<?>, List<Object>> ctxByType = new HashMap<Class<?>, List<Object>>();
	private final Map<String, Object> ctxByName = new HashMap<String, Object>();

	public void add(final Object o) {
		if (o == null) {
			return;
		}
		map(o.getClass(), o);
		final Class<?>[] interfaces = o.getClass().getInterfaces();
		for (final Class<?> c : interfaces) {
			add(c, o);
		}
	}

	private void map(final Class<? extends Object> c, final Object o) {
		add(c, o);
		Class<?> superClass = c.getSuperclass();
		if (superClass != null) {
			map(superClass, o);
		}
	}

	private void add(final Class<? extends Object> c, final Object o) {
		List<Object> instances = this.ctxByType.get(c);
		if (instances == null) {
			instances = new ArrayList<Object>();
			this.ctxByType.put(c, instances);
		}
		instances.add(o);
	}

	public Object getInstance(final Class<?> c) {
		final List<Object> instances = this.ctxByType.get(c);
		if (instances != null) {
			return instances.get(0);
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
