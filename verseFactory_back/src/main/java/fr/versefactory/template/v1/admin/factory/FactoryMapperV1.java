package fr.versefactory.template.v1.admin.factory;

import fr.versefactory.template.v1.admin.openapi.payload.FactoryDto;
import fr.versefactory.template.v1.admin.factory.representations.FactoryRepresentationV1;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring")
public interface FactoryMapperV1 {

    @Mapping(target = "id", source = "factory.id")
    @Mapping(target = "userId", source = "factory.userId")
    @Mapping(target = "balance", source = "factory.balance")
    @Mapping(target = "lastUpdatedAt", source = "factory.lastUpdatedAt", qualifiedByName = "localToOffsetDateTime")
    FactoryDto toDto(FactoryRepresentationV1 representation);

    @Named("localToOffsetDateTime")
    default OffsetDateTime localToOffsetDateTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.atOffset(ZoneOffset.UTC);
    }
}
