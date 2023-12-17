import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class task2test {

    @Test
    void testModifiedSumMethod() throws IllegalAccessException, InstantiationException {
        // Создаем новый класс
        Class<?> dynamicClass = new ByteBuddy()
                .subclass(task2.ArithmeticUtils.class) // замените "ArithmeticUtils" на ваш класс
                // Меняем поведение метода sum
                .method(ElementMatchers.named("sum"))
                .intercept(MethodDelegation.to(task2.ArithmeticInterceptor.class))
                .make()
                .load(getClass().getClassLoader())
                .getLoaded();

        // Создаем экземпляр класса и вызываем метод sum
        task2.ArithmeticUtils arithmeticUtils = (task2.ArithmeticUtils) dynamicClass.newInstance(); // замените "ArithmeticUtils" на ваш класс
        int result = arithmeticUtils.sum(2, 3);

        // Проверяем, что метод sum теперь выполняет умножение
        assertEquals(6, result);
    }
}
