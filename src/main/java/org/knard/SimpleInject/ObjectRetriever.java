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
 * This class represent object used during the invocation to retrieve object
 * that should be injected.
 * 
 * @author Pascal Migazzi
 *
 */
public interface ObjectRetriever {

	/**
	 * Retrieve the object from the context that should be injected.
	 * 
	 * @param ctx
	 *            the context used during the injection.
	 * @return object that should be injected or <code>null</code> if the
	 *         context doesn't contain any object that fit this retriever.
	 */
	Object retrieve(Context ctx);

}
