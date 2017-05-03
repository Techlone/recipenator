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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

/**
 * LuaValue that represents a particular public Java constructor.
 * <p>
 * May be called with arguments to return a JavaInstance 
 * created by calling the constructor.  
 * <p>
 * This class is not used directly.  
 * It is returned by calls to calls to {@link JavaInstance#get(LuaValue key)}
 * when the value of key is "new".
 * @see CoerceJavaToLua
 * @see CoerceLuaToJava
 */
class JavaConstructor extends JavaMember {

	static final Map<Constructor, JavaConstructor> constructors = Collections.synchronizedMap(new HashMap<>());
	
	static JavaConstructor forConstructor(Constructor c) {
		return constructors.computeIfAbsent(c, JavaConstructor::new);
	}
	
	public static LuaValue forConstructors(List<JavaConstructor> constructors) {
		return new JavaOverload<>(constructors);
	}

	final Constructor constructor;
	
	private JavaConstructor(Constructor c) {
		super(c.getParameterTypes(), c.isVarArgs());
		this.constructor = c;
	}
	
	public Varargs invoke(Varargs args) {
		try {
			return CoerceJavaToLua.coerce(constructor.newInstance(convertArgs(args)));
		} catch (InvocationTargetException e) {
			throw new LuaError(e.getTargetException());
		} catch (Exception e) {
			return LuaValue.error("coercion error "+e);
		}
	}
}
