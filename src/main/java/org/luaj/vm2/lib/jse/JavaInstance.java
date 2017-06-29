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

import org.luaj.vm2.LuaUserdata;
import org.luaj.vm2.LuaValue;

/**
 * LuaValue that represents a Java instance.
 * <p>
 * Will respond to get() and set() by returning field values or methods. 
 * <p>
 * This class is not used directly.  
 * It is returned by calls to {@link CoerceJavaToLua#coerce(Object)} 
 * when a subclass of Object is supplied.
 * @see CoerceJavaToLua
 * @see CoerceLuaToJava
 */
class JavaInstance extends LuaUserdata {
	JavaClass javaClass;

	JavaInstance(Object instance) {
		super(instance);
	}
	//do not call in constructor! it will cause StackOverflow
	private void setJavaClass() {
		if (javaClass == null) javaClass = JavaClass.forClass(m_instance.getClass());
	}

	public LuaValue get(LuaValue key) {
		setJavaClass();

		LuaValue fieldValue = javaClass.getFieldValue(this, key);
		if (!isNullOrNil(fieldValue)) return fieldValue;

        LuaValue method = javaClass.getMethod(key);
		if (!isNullOrNil(method)) return method;

		LuaValue innerClass = javaClass.getInnerClass(key);
		if (!isNullOrNil(innerClass)) return innerClass;

		return super.get(key);
	}

	public void set(LuaValue key, LuaValue value) {
		setJavaClass();
		boolean successSetting = javaClass.setFieldValue(this, key, value);
		if (!successSetting) super.set(key, value);
	}

	public static boolean isNullOrNil(LuaValue luaValue) {
		return luaValue == null || luaValue.isnil();
	}
}
