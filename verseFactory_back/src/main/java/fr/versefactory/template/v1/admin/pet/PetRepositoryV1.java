package fr.versefactory.template.v1.admin.pet;

import fr.versefactory.template.v1.core.TemplateRepositoryV1;
import fr.versefactory.template.v1.admin.pet.representations.PetRepresentationV1;
import fr.versefactory.template.storage.Tables;
import org.springframework.stereotype.Repository;

import fr.versefactory.template.v1.admin.openapi.payload.FactoryPetDto;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class PetRepositoryV1 extends TemplateRepositoryV1 {

    public List<PetRepresentationV1> findAll() {
        return dslContext.selectFrom(Tables.PET)
                .fetch()
                .stream()
                .map(PetRepresentationV1::new)
                .collect(Collectors.toList());
    }

    public Optional<PetRepresentationV1> findById(UUID id) {
        return dslContext.selectFrom(Tables.PET)
                .where(Tables.PET.ID.eq(id))
                .fetchOptional()
                .map(PetRepresentationV1::new);
    }

    public List<PetRepresentationV1> findAllByFactoryId(UUID factoryId) {
        return dslContext.select(Tables.PET.fields())
                .from(Tables.PET)
                .join(Tables.FACTORY_PET).on(Tables.FACTORY_PET.PET_ID.eq(Tables.PET.ID))
                .where(Tables.FACTORY_PET.FACTORY_ID.eq(factoryId))
                .fetchInto(Tables.PET)
                .stream()
                .map(PetRepresentationV1::new)
                .collect(Collectors.toList());
    }

    public List<FactoryPetDto> findFactoryPetsByFactoryId(UUID factoryId) {
        return dslContext.select(
                    Tables.FACTORY_PET.ID,
                    Tables.FACTORY_PET.PET_ID,
                    Tables.PET.NAME,
                    Tables.PET.RARITY,
                    Tables.PET.INCOME_PER_SECOND,
                    Tables.PET.BASE_COST,
                    Tables.FACTORY_PET.ACQUIRED_AT
                )
                .from(Tables.FACTORY_PET)
                .join(Tables.PET).on(Tables.FACTORY_PET.PET_ID.eq(Tables.PET.ID))
                .where(Tables.FACTORY_PET.FACTORY_ID.eq(factoryId))
                .fetch(record -> {
                    FactoryPetDto dto = new FactoryPetDto(
                        record.get(Tables.FACTORY_PET.ID),
                        record.get(Tables.FACTORY_PET.PET_ID),
                        record.get(Tables.PET.NAME),
                        record.get(Tables.PET.RARITY)
                    );
                    dto.setIncomePerSecond(record.get(Tables.PET.INCOME_PER_SECOND));
                    dto.setBaseCost(record.get(Tables.PET.BASE_COST));
                    java.time.LocalDateTime acquiredAt = record.get(Tables.FACTORY_PET.ACQUIRED_AT);
                    if (acquiredAt != null) {
                        dto.setAcquiredAt(acquiredAt.atZone(ZoneId.systemDefault()).toOffsetDateTime());
                    }
                    return dto;
                });
    }

    public void addPetToFactory(UUID factoryId, UUID petId) {
        dslContext.insertInto(Tables.FACTORY_PET)
                .set(Tables.FACTORY_PET.FACTORY_ID, factoryId)
                .set(Tables.FACTORY_PET.PET_ID, petId)
                .execute();
    }

    public boolean removePetFromFactory(UUID factoryPetId, UUID factoryId) {
        int rows = dslContext.deleteFrom(Tables.FACTORY_PET)
                .where(Tables.FACTORY_PET.ID.eq(factoryPetId))
                .and(Tables.FACTORY_PET.FACTORY_ID.eq(factoryId))
                .execute();
        return rows > 0;
    }
}
