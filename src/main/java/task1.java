import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

public class task1 {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        // Создаем новый класс
        DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
                .subclass(Object.class)  // Наследуемся от Object
                .method(ElementMatchers.isToString())  // Выбираем метод toString
                .intercept(FixedValue.value("Hello, ByteBuddy!"))  // Заменяем реализацию на фиксированное значение
                .make();

        // Загружаем класс
        Class<?> dynamicClass = dynamicType.load(task1.class.getClassLoader())
                .getLoaded();

        // Создаем экземпляр класса и вызываем метод toString
        Object instance = dynamicClass.newInstance();
        String result = instance.toString();
        System.out.println(result);
    }
}
