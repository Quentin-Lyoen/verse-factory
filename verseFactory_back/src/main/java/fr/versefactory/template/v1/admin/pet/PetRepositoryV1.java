package fr.versefactory.template.v1.admin.pet;

import fr.versefactory.template.v1.core.TemplateRepositoryV1;
import fr.versefactory.template.v1.admin.pet.representations.PetRepresentationV1;
import fr.versefactory.template.storage.Tables;
import org.springframework.stereotype.Repository;

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

    public void addPetToFactory(UUID factoryId, UUID petId) {
        dslContext.insertInto(Tables.FACTORY_PET)
                .set(Tables.FACTORY_PET.FACTORY_ID, factoryId)
                .set(Tables.FACTORY_PET.PET_ID, petId)
                .execute();
    }
}
