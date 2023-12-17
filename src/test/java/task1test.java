import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class task1test {

    @Test
    public void testToStringReturnsHelloByteBuddy() throws IllegalAccessException, InstantiationException {
        // Создаем новый класс
        DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
                .subclass(Object.class)
                .method(ElementMatchers.isToString())
                .intercept(FixedValue.value("Hello, ByteBuddy!"))
                .make();

        // Загружаем класс
        Class<?> dynamicClass = dynamicType.load(getClass().getClassLoader())
                .getLoaded();

        // Создаем экземпляр класса и вызываем метод toString
        Object instance = dynamicClass.newInstance();
        String result = instance.toString();

        // Проверяем, что метод toString возвращает ожидаемое значение
        assertEquals("Hello, ByteBuddy!", result);
    }
}