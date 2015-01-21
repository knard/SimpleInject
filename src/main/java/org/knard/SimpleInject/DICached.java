package org.knard.SimpleInject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class DICached extends DI {

	private final LinkedList<Class<?>> cacheBasket;
	private final Map<Class<?>, Invoker> invokerMap;
	private final int cacheSize;

	public DICached(final int cacheSize) {
		this.cacheSize = cacheSize;
		this.cacheBasket = new LinkedList<Class<?>>();
		this.invokerMap = new HashMap<Class<?>, Invoker>(cacheSize + 1);
	}

	@Override
	public Invoker getInvoker(final Class<?> c) {
		Invoker invoker = this.invokerMap.get(c);
		if (invoker != null) {
			return invoker;
		}
		invoker = super.getInvoker(c);
		this.invokerMap.put(c, invoker);
		this.cacheBasket.addFirst(c);
		if (this.cacheBasket.size() > this.cacheSize) {
			this.cacheBasket.removeLast();
		}
		return invoker;
	}

}
