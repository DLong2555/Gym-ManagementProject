package renewal.gym.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class EnumToValueConverter implements Converter<Enum<?>, String> {

    @Override
    public String convert(Enum source) {
        log.info("Converting Enum");
        return source.toString();
    }

}
