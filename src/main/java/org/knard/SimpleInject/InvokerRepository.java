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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

public class InvokerRepository {

	private final Map<Class<?>, Invoker> cache = new HashMap<Class<?>, Invoker>();

	public Invoker getInvoker(final Class<?> c) {
		synchronized (c) {
			Invoker invoker = this.cache.get(c);
			if (invoker != null) {
				return invoker;
			}
			final List<MethodInvoker> methodInvokers = new ArrayList<MethodInvoker>();
			final Method[] methods = c.getMethods();
			for (final Method method : methods) {
				if (method.getAnnotation(Inject.class) != null) {
					final List<Injector> injectors = new ArrayList<Injector>();
					final Class<?>[] paramTypes = method.getParameterTypes();
					final Annotation[][] annotations = method.getParameterAnnotations();
					for (int i = 0; i < paramTypes.length; i++) {
						final Annotation[] paramAnnotations = annotations[i];
						final Named namedAnnotation = getAnnotation(paramAnnotations, Named.class);
						if (namedAnnotation != null) {
							injectors.add(new NamedInjector(namedAnnotation.value()));
						}
						else {
							injectors.add(new TypeInjector(paramTypes[i]));
						}
					}
					method.setAccessible(true);
					final MethodInvoker methodInvoker = new MethodInvoker(method,
							injectors.toArray(new Injector[injectors.size()]));
					methodInvokers.add(methodInvoker);
				}
			}
			invoker = new InvokerImpl(methodInvokers.toArray(new MethodInvoker[methodInvokers.size()]));
			this.cache.put(c, invoker);
			return invoker;
		}
	}

	@SuppressWarnings("unchecked")
	private <T extends Annotation> T getAnnotation(final Annotation[] paramAnnotations, final Class<T> annotationClass) {
		for (final Annotation annotation : paramAnnotations) {
			if (annotation.annotationType() == annotationClass) {
				return (T) annotation;
			}
		}
		return null;
	}
}
