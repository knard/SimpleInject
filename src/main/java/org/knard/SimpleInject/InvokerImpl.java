package org.knard.SimpleInject;

public class InvokerImpl implements Invoker {

	private final MethodInvoker[] invokers;

	public InvokerImpl(final MethodInvoker[] invokers) {
		this.invokers = invokers;
	}

	public void invokeInContext(final Context ctx, final Object instance) {
		for (final MethodInvoker invoker : this.invokers) {
			invoker.invoke(ctx, instance);
		}
	}

}
