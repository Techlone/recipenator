package recipenator.utils;

public class CommonHelper {
    public static <T> void ignoreErrors(Runnable runnable) {
        ignoreErrors(runnable, false);
    }

    public static <T> void ignoreErrors(Consumer<T> consumer, T arg) {
        ignoreErrors(consumer, arg, false);
    }

    public static <T> T ignoreErrors(Supplier<T> supplier, T defaultValue) {
        return ignoreErrors(supplier, defaultValue, false);
    }

    public static <T, N> T ignoreErrors(Function<N, T> function, N argument, T defaultValue) {
        return ignoreErrors(function, argument, defaultValue, false);
    }

    public static <T> void ignoreErrors(Runnable runnable, boolean printError) {
        ignoreErrors(Runnable::run, runnable, printError);
    }

    public static <T> void ignoreErrors(Consumer<T> consumer, T argument, boolean printError) {
        try {
            consumer.accept(argument);
        } catch (Exception e) {
            if (printError) e.printStackTrace();
        }
    }

    public static <T> T ignoreErrors(Supplier<T> supplier, T defaultValue, boolean printError) {
        return ignoreErrors(Supplier::get, supplier, defaultValue, printError);
    }

    public static <T, N> T ignoreErrors(Function<N, T> function, N argument, T defaultValue, boolean printError) {
        try {
            return function.apply(argument);
        } catch (Exception e) {
            if (printError) e.printStackTrace();
            return defaultValue;
        }
    }

    public interface Runnable { void run() throws Exception; }
    public interface Supplier<T> { T get() throws Exception; }
    public interface Consumer<T> { void accept(T t) throws Exception; }
    public interface Function<T, R> { R apply(T t) throws Exception; }
}
