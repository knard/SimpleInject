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

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Entry point of the library. This class is used to retrieve Invoker associated
 * with a specific class.
 * 
 * @author Pascal Migazzi
 *
 */
public class InvokerRepository {

	/**
	 * get the invoker associated to a class.
	 * 
	 * @param c
	 *            the class for which the invoker should be retrieved.
	 * @return an Invoker.
	 */
	public Invoker getInvoker(final Class<?> c) {
		synchronized (c) {
			final List<MethodInvoker> methodInvokers = new ArrayList<MethodInvoker>();
			final Method[] methods = c.getMethods();
			for (final Method method : methods) {
				if (method.getAnnotation(Inject.class) != null) {
					final List<ObjectRetriever> objectRetrievers = new ArrayList<ObjectRetriever>();
					final Class<?>[] paramTypes = method.getParameterTypes();
					final Annotation[][] annotations = method
							.getParameterAnnotations();
					for (int i = 0; i < paramTypes.length; i++) {
						final Annotation[] paramAnnotations = annotations[i];
						final Named namedAnnotation = getAnnotation(
								paramAnnotations, Named.class);
						if (namedAnnotation != null) {
							objectRetrievers.add(new ByNameRetriever(namedAnnotation
									.value()));
						} else {
							objectRetrievers.add(new ByTypeRetriever(paramTypes[i]));
						}
					}
					method.setAccessible(true);
					final MethodInvoker methodInvoker = new MethodInvoker(
							method, objectRetrievers.toArray(new ObjectRetriever[objectRetrievers
									.size()]));
					methodInvokers.add(methodInvoker);
				}
			}
			return new InvokerImpl(
					methodInvokers.toArray(new MethodInvoker[methodInvokers
							.size()]));
		}
	}

	/**
	 * helper used to find if an annotation is present in an array.
	 * 
	 * @param paramAnnotations
	 *            the annotation array to search in.
	 * @param annotationClass
	 *            the annotation type used to find the annotation.
	 * @return the first annotation if the corresponding type.
	 */
	@SuppressWarnings("unchecked")
	static private <T extends Annotation> T getAnnotation(
			final Annotation[] paramAnnotations, final Class<T> annotationClass) {
		for (final Annotation annotation : paramAnnotations) {
			if (annotation.annotationType() == annotationClass) {
				return (T) annotation;
			}
		}
		return null;
	}
}
