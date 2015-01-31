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
 * retrieve a value by type from the context.
 * 
 * @author Pascal Migazzi
 *
 */
public class ByTypeRetriever implements ObjectRetriever {

	/**
	 * type used to retrieve the data from the context.
	 */
	private final Class<?> c;

	/**
	 * 
	 * @param c
	 *            type used to retrieve the data from the context.
	 */
	public ByTypeRetriever(final Class<?> c) {
		this.c = c;
	}

	public Object retrieve(final Context ctx) {
		return ctx.getInstance(this.c);
	}

}
