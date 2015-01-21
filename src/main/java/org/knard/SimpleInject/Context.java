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
		if (c == null) {
			return;
		}
		add(c, o);
		map(c.getSuperclass(), o);
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
