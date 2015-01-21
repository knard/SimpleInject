package org.knard.SimpleInject;

public class TypeInjector implements Injector {

	private final Class<?> c;

	public TypeInjector(final Class<?> c) {
		this.c = c;
	}

	public Object inject(final Context ctx) {
		return ctx.getInstance(this.c);
	}

}
