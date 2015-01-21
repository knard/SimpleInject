package org.knard.SimpleInject;

public class NamedInjector implements Injector {

	private final String name;

	public NamedInjector(final String name) {
		this.name = name;
	}

	public Object inject(final Context ctx) {
		return ctx.getInstance(this.name);
	}

}
