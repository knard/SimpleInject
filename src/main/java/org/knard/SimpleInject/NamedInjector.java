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
 * retrieve a value by name from the context.
 * 
 * @author Pascal Migazzi
 *
 */
public class NamedInjector implements Injector {

	/**
	 * the name used to retrieve the value from the context.
	 */
	private final String name;

	/**
	 * 
	 * @param name
	 *            the name used to retrieve the value from the context.
	 */
	public NamedInjector(final String name) {
		this.name = name;
	}

	public Object inject(final Context ctx) {
		return ctx.getInstance(this.name);
	}

}
