package com.journal.florist.app.common.utils.converter;

import com.journal.florist.app.common.utils.converter.RequestParameterConverter;
import com.journal.florist.backend.feature.user.enums.ERole;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;

@RequestParameterConverter
public class StringToEnumTypeConverter implements Converter<String, ERole> {

    @Override
    public ERole convert(@NotNull String source) {
        return ERole.decode(source);
    }

}
