package renewal.gym.converter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;
import org.springframework.stereotype.Component;
import renewal.gym.service.SecureIdEncryptor;

import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class EncryptConverter implements ConditionalGenericConverter {

    private final SecureIdEncryptor secureIdEncryptor;

    @Override
    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        return sourceType.hasAnnotation(Encrypt.class);
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Set.of(
                new ConvertiblePair(Long.class, String.class)
        );
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        return secureIdEncryptor.encryptId((Long) source);
    }
}
