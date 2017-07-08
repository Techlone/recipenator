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

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.jse.CoerceLuaToJava.Coercion;

/**
 * Java method or constructor.
 * <p>
 * Primarily handles argument coercion for parameter lists including scoring of compatibility and 
 * java varargs handling.
 * <p>
 * This class is not used directly.  
 * It is an abstract base class for {@link JavaConstructor} and {@link JavaMethod}.
 * @see JavaConstructor
 * @see JavaMethod
 * @see CoerceJavaToLua
 * @see CoerceLuaToJava
 */
abstract
class JavaMember extends VarArgFunction {
	final Coercion[] fixedCoercions;
	final Coercion variableCoercion;

	protected JavaMember(Class[] params, boolean isvarargs) {
		fixedCoercions = new CoerceLuaToJava.Coercion[isvarargs ? params.length - 1 : params.length];
		for (int i = 0; i < fixedCoercions.length; i++)
			fixedCoercions[i] = CoerceLuaToJava.getCoercion(params[i]);
        variableCoercion = isvarargs ? CoerceLuaToJava.getCoercion(params[params.length - 1]) : null;
	}

	int score(Varargs args) {
		int n = args.narg();
		int s = n > fixedCoercions.length ? CoerceLuaToJava.SCORE_WRONG_TYPE * (n - fixedCoercions.length) : 0;
		for (int j = 0; j < fixedCoercions.length; j++)
			s += fixedCoercions[j].score(args.arg(j + 1));
		if (variableCoercion != null)
			for (int k = fixedCoercions.length; k < n; k++)
				s += variableCoercion.score(args.arg(k + 1));
		return s;
	}

	protected Object[] convertArgs(Varargs args) {
		Object[] a;
		if (variableCoercion == null) {
			a = new Object[fixedCoercions.length];
			for (int i = 0; i < a.length; i++)
				a[i] = fixedCoercions[i].coerce(args.arg(i + 1));
		} else {
			int n = Math.max(fixedCoercions.length, args.narg());
			a = new Object[n];
			for (int i = 0; i < fixedCoercions.length; i++)
				a[i] = fixedCoercions[i].coerce(args.arg(i + 1));
			for (int i = fixedCoercions.length; i < n; i++)
				a[i] = variableCoercion.coerce(args.arg(i + 1));
		}
		return a;
	}
}
