package fr.versefactory.template.v1.admin.pet;

import fr.versefactory.template.v1.admin.openapi.payload.PetDto;
import fr.versefactory.template.v1.admin.pet.representations.PetRepresentationV1;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PetMapperV1 {

    @Mapping(target = "id", source = "pet.id")
    @Mapping(target = "name", source = "pet.name")
    @Mapping(target = "rarity", source = "pet.rarity")
    @Mapping(target = "incomePerSecond", source = "pet.incomePerSecond")
    @Mapping(target = "baseCost", source = "pet.baseCost")
    PetDto toDto(PetRepresentationV1 representation);
}
