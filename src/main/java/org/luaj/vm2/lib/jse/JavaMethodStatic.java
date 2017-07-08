package org.luaj.vm2.lib.jse;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

import java.lang.reflect.Method;

class JavaMethodStatic extends JavaMethod {
	JavaMethodStatic(Method m) {
		super(m);
	}

	public int score(Varargs args) {
	    return super.score(convertForStatic(args));
    }

	public Varargs invoke(Varargs args) {
		return super.invoke(convertForStatic(args));
	}

	private Varargs convertForStatic(Varargs args) {
	    return LuaValue.varargsOf(new JavaInstance(null), args);
    }
}
