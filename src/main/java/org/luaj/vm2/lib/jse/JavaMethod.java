/*******************************************************************************
* Copyright (c) 2011 Luaj.org. All rights reserved.
*
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
*
* The above copyright notice and this permission notice shall be included in
* all copies or substantial portions of the Software.
* 
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
* THE SOFTWARE.
******************************************************************************/
package org.luaj.vm2.lib.jse;

import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import recipenator.utils.CommonHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * LuaValue that represents a Java method.
 * <p>
 * Can be invoked via call(LuaValue...) and related methods. 
 * <p>
 * This class is not used directly.  
 * It is returned by calls to calls to {@link JavaInstance#get(LuaValue key)} 
 * when a method is named.
 * @see CoerceJavaToLua
 * @see CoerceLuaToJava
 */
class JavaMethod extends JavaMember {

	static final Map<Method, JavaMethod> methods = new ConcurrentHashMap<>();

	static JavaMethod forMethod(Method m) {
		return methods.computeIfAbsent(m, Modifier.isStatic(m.getModifiers()) ? JavaMethodStatic::new : JavaMethod::new);
	}
	
	static LuaFunction forMethods(List<JavaMethod> methods) {
		return new JavaOverload<>(methods);
	}
	
	private final Method method;
	
	JavaMethod(Method method) {
		super(method.getParameterTypes(), method.isVarArgs());
		CommonHelper.ignoreErrors(m -> {
			if (!m.isAccessible())
				m.setAccessible(true);
		}, method);
        this.method = method;
	}

	public Varargs invoke(Varargs args) {
		if (args.narg() == 0) error("method cannot be called without instance");
		return invokeMethod(args.checkuserdata(1), args.subargs(2));
	}
	
	LuaValue invokeMethod(Object instance, Varargs args) {
		try {
            Object result = method.invoke(instance, convertArgs(args));
            return CoerceJavaToLua.coerce(result);
		} catch (InvocationTargetException e) {
			throw new LuaError(e.getTargetException());
		} catch (Exception e) {
			return error("coercion error " + e);
		}
	}
}
