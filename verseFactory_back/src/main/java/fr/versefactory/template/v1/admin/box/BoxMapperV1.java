package fr.versefactory.template.v1.admin.box;

import fr.versefactory.template.v1.admin.openapi.payload.BoxDto;
import fr.versefactory.template.v1.admin.box.representations.BoxRepresentationV1;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BoxMapperV1 {

    @Mapping(target = "id", source = "box.id")
    @Mapping(target = "name", source = "box.name")
    @Mapping(target = "description", source = "box.description")
    @Mapping(target = "price", source = "box.price")
    BoxDto toDto(BoxRepresentationV1 representation);
}
