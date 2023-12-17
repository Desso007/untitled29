import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.matcher.ElementMatchers;

public class task2 {

    public static class ArithmeticUtils {
        public int sum(int a, int b) {
            return a + b;
        }
    }

    public static class ArithmeticInterceptor {
        @RuntimeType
        public static int intercept(@RuntimeType int a, @RuntimeType int b) {
            // Вместо сложения выполняем умножение
            return a * b;
        }
    }

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        // Создаем новый класс
        Class<?> dynamicClass = new ByteBuddy()
                .subclass(ArithmeticUtils.class)
                // Меняем поведение метода sum
                .method(ElementMatchers.named("sum"))
                .intercept(MethodDelegation.to(ArithmeticInterceptor.class))
                .make()
                .load(task2.class.getClassLoader())
                .getLoaded();

        // Создаем экземпляр класса и вызываем метод sum
        ArithmeticUtils arithmeticUtils = (ArithmeticUtils) dynamicClass.newInstance();
        int result = arithmeticUtils.sum(2, 3);

        // Выводим результат
        System.out.println("Result: " + result); // Вывод: Result: 6
    }
}
