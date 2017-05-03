package org.luaj.vm2.lib.jse;

import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

import java.util.List;

/**
 * LuaValue that represents an overloaded Java method.
 * <p>
 * On invocation, will pick the best method from the list, and invoke it.
 * <p>
 * This class is not used directly.
 * It is returned by calls to calls to {@link JavaInstance#get(LuaValue key)}
 * when an overloaded method is named.
 */
class JavaOverload<T extends JavaMember> extends LuaFunction {
    private final List<T> methods;

    JavaOverload(List<T> methods) {
        this.methods = methods;
    }

    @Override
    public Varargs invoke(Varargs args) {
        JavaMember best = null;
        int score = CoerceLuaToJava.SCORE_UNCOERCIBLE;
        for (JavaMember method : methods) {
            int s = method.score(args);
            if (s >= score) continue;
            score = s;
            best = method;
            if (score == 0) break;
        }

        if (best == null) error("no coercible public method");
        return best.invoke(args);
    }
}
