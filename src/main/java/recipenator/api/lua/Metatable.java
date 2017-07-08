package recipenator.api.lua;

public abstract class Metatable {
    public final Object __index(Object id) {
        return index(id);
    }

    public final Object __call(Object[] args) {
        return call(args);
    }

    public final Object __mul(Object operand) {
        return mul(operand);
    }

    protected Object index(Object id) {
        throw new UnsupportedMetamethodException("index");
    }

    protected Object call(Object[] args) {
        throw new UnsupportedMetamethodException("call");
    }

    protected Object mul(Object operand) {
        throw new UnsupportedMetamethodException("mul");
    }

    public class UnsupportedMetamethodException extends UnsupportedOperationException {
        public UnsupportedMetamethodException(String metamethod) {
            super("Methamethod __" + metamethod + " isn't implemented for class " + Metatable.this.getClass().getName());
        }
    }
}
