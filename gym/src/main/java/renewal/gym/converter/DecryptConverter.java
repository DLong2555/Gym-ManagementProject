package renewal.gym.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;
import org.springframework.stereotype.Component;
import renewal.gym.service.SecureIdEncryptor;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DecryptConverter implements ConditionalGenericConverter {

    private final SecureIdEncryptor secureIdEncryptor;

    @Override
    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        return targetType.hasAnnotation(Decrypt.class);
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Set.of(
                new ConvertiblePair(String.class, Long.class)
        );
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        return secureIdEncryptor.decryptId((String) source);
    }
}
