package renewal.gym.controller.argument;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.util.Collections;
import java.util.Set;

public class PhoneNumberFormatterResolver implements AnnotationFormatterFactory<PhoneNumberFormatter> {

    @Override
    public Set<Class<?>> getFieldTypes() {
        return Collections.singleton(String.class);
    }

    @Override
    public Printer<?> getPrinter(PhoneNumberFormatter annotation, Class<?> fieldType) {
        return (object, locale) -> object.toString().substring(0, 3) + " - "
                + object.toString().substring(3, 7) + " - " + object.toString().substring(7);
    }

    @Override
    public Parser<?> getParser(PhoneNumberFormatter annotation, Class<?> fieldType) {
        return (text, locale) -> text.replaceAll(" - ","");
    }
}
