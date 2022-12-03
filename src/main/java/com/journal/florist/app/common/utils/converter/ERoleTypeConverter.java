package com.journal.florist.app.common.utils.converter;

import com.journal.florist.backend.feature.user.enums.ERole;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Optional;

@Component
@Converter(autoApply = true)
public class ERoleTypeConverter implements AttributeConverter<ERole, String> {
    @Override
    public String convertToDatabaseColumn(final ERole attribute) {
        return Optional.ofNullable(attribute)
                .map(ERole::getRoleName)
                .orElse(null);
    }

    @Override
    public ERole convertToEntityAttribute(final String dbData) {
        return ERole.decode(dbData);
    }
}
