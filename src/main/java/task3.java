import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.BindingPriority;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;

import java.lang.reflect.Method;

public class task3 {

    public static void main(String[] args) throws Exception {
        // Создаем динамический класс с методом fib(int n)
        Class<?> dynamicClass = createDynamicClass();

        // Создаем экземпляр класса
        Object instance = dynamicClass.getDeclaredConstructor().newInstance();

        // Вызываем метод fib(10) и выводим результат
        Method fibMethod = dynamicClass.getMethod("fib", int.class);
        Object result = fibMethod.invoke(instance, 10);
        System.out.println("Result: " + result);
    }

    private static Class<?> createDynamicClass() {
        DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
                .subclass(Object.class)
                .name("DynamicFibonacciClass")
                .defineMethod("fib", long.class, Visibility.PUBLIC)
                .withParameter(int.class, "n")
                .intercept(MethodDelegation.to(FibonacciInterceptor.class))
                .make();

        return dynamicType.load(task3.class.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                .getLoaded();
    }

    public static class FibonacciInterceptor {

        @RuntimeType
        @BindingPriority(1)
        public static long fib(@AllArguments int[] args) {
            int n = args[0];
            if (n <= 1) {
                return n;
            } else {
                long[] fib = new long[n + 1];
                fib[0] = 0;
                fib[1] = 1;
                for (int i = 2; i <= n; i++) {
                    fib[i] = fib[i - 1] + fib[i - 2];
                }
                return fib[n];
            }
        }
    }
}



































































