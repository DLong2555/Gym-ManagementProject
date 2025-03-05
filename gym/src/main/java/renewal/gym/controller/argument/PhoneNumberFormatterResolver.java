package renewal.gym.controller.argument;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.util.Collections;
import java.util.Set;

@Slf4j
public class PhoneNumberFormatterResolver implements AnnotationFormatterFactory<PhoneNumberFormatter> {

    @Override
    public Set<Class<?>> getFieldTypes() {
        return Collections.singleton(String.class);
    }

    @Override
    public Printer<?> getPrinter(PhoneNumberFormatter annotation, Class<?> fieldType) {
        log.debug("format phone number");
        return (object, locale) -> object.toString().substring(0, 3) + " - "
                + object.toString().substring(3, 7) + " - " + object.toString().substring(7);
    }

    @Override
    public Parser<?> getParser(PhoneNumberFormatter annotation, Class<?> fieldType) {
        log.debug("format parse phone number");
        return (text, locale) -> text.replaceAll(" - ","");
    }
}


