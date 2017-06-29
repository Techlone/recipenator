package org.luaj.vm2.lib.jse;

import org.luaj.vm2.Varargs;

import java.lang.reflect.Method;

class JavaMethodStatic extends JavaMethod {
	JavaMethodStatic(Method m) {
		super(m);
	}

	public Varargs invoke(Varargs args) {
		return invokeMethod(null, args);
	}
}
