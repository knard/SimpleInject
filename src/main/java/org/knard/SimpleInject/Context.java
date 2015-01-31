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

/**
 * 
 * This class is used to contain the data that can be used during injection.
 * 
 * @author Pascal Migazzi
 *
 */
public class Context {

	/**
	 * Contains data associated by type. This map is used to speed up the
	 * retrieval by type.
	 */
	private final Map<Class<?>, Set<Object>> ctxByType = new HashMap<Class<?>, Set<Object>>();

	/**
	 * Contains data associated by name.
	 */
	private final Map<String, Object> ctxByName = new HashMap<String, Object>();

	/**
	 * Add a new object to the context. This object will not be associated to
	 * any name.
	 * 
	 * @param o
	 *            the object to associate to the context.
	 */
	public void add(final Object o) {
		if (o == null) {
			return;
		}
		map(o.getClass(), o);
	}

	/**
	 * Associate an object to all types (interface or class) that this object
	 * represent. This method is also responsible to associate with parent type
	 * and interfaces.
	 * 
	 * @param c
	 *            the class to which the object should be associated.
	 * @param o
	 *            the object to be associated.
	 */
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

	/**
	 * Associate an object to a specific type. This method doesn't manage
	 * inheritance or interfaces.
	 * 
	 * @param c
	 *            the type to which we want to associate the object.
	 * @param o
	 *            the object that should be associated.
	 */
	private void add(final Class<? extends Object> c, final Object o) {
		Set<Object> instances = this.ctxByType.get(c);
		if (instances == null) {
			instances = new HashSet<Object>();
			this.ctxByType.put(c, instances);
		}
		instances.add(o);
	}

	/**
	 * retrieve an object associated to this type.
	 * 
	 * @param c
	 *            the type used to find an object.
	 * @return one of the object associated to this type or <code>null</code> if
	 *         nothing is found.
	 */
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

	/**
	 * retrieve all objects associated to this type.
	 * 
	 * @param c
	 *            the type used to find objects.
	 * @return all objects associated to this type or <code>null</code> if
	 *         nothing is found. The set return can't be modified.
	 */
	public Set<Object> getInstances(final Class<?> c) {
		final Set<Object> instances = this.ctxByType.get(c);
		if (instances != null) {
			return Collections.unmodifiableSet(instances);
		}
		return null;
	}

	/**
	 * Add an object to the context and associate it to a name.
	 * 
	 * @param name
	 *            the name associated to this object.
	 * @param o
	 *            the object to add to the contect.
	 */
	public void add(final String name, final Object o) {
		this.ctxByName.put(name, o);
		if (o != null) {
			add(o);
		}
	}

	/**
	 * retrieve an object associated to a name.
	 * 
	 * @param name
	 *            the name of the object we want to retrieve.
	 * @return the object associated to the name or <code>null</code> if no
	 *         object is associated to this name.
	 */
	public Object getInstance(final String name) {
		return this.ctxByName.get(name);
	}
}
