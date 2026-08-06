package fr.versefactory.template.v1.admin.box;

import fr.versefactory.template.v1.core.TemplateRepositoryV1;
import fr.versefactory.template.v1.admin.box.representations.BoxPetDropRepresentationV1;
import fr.versefactory.template.v1.admin.box.representations.BoxRepresentationV1;
import fr.versefactory.template.v1.admin.pet.representations.PetRepresentationV1;
import fr.versefactory.template.storage.Tables;
import org.jooq.Field;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class BoxRepositoryV1 extends TemplateRepositoryV1 {

    public List<BoxRepresentationV1> findAll() {
        return dslContext.selectFrom(Tables.BOX)
                .fetch()
                .stream()
                .map(BoxRepresentationV1::new)
                .collect(Collectors.toList());
    }

    public Optional<BoxRepresentationV1> findById(UUID id) {
        return dslContext.selectFrom(Tables.BOX)
                .where(Tables.BOX.ID.eq(id))
                .fetchOptional()
                .map(BoxRepresentationV1::new);
    }

    public List<BoxPetDropRepresentationV1> findPetsByBoxId(UUID boxId) {
        return dslContext.selectFrom(Tables.BOX_PET)
                .where(Tables.BOX_PET.BOX_ID.eq(boxId))
                .fetch()
                .stream()
                .map(record -> BoxPetDropRepresentationV1.builder()
                        .boxPetRecord(record)
                        .build())
                .collect(Collectors.toList());
    }

    public List<PetRepresentationV1> findAllPetsByBoxId(UUID boxId) {
        Field<Integer> rarityRank = DSL.decode(Tables.PET.RARITY,
                "COMMON", 1,
                "UNCOMMON", 2,
                "RARE", 3,
                "EPIC", 4,
                "LEGENDARY", 5,
                "MYTHIC", 6,
                99);

        return dslContext.select(Tables.PET.fields())
                .from(Tables.PET)
                .join(Tables.BOX_PET).on(Tables.BOX_PET.PET_ID.eq(Tables.PET.ID))
                .where(Tables.BOX_PET.BOX_ID.eq(boxId))
                .orderBy(rarityRank.asc(), Tables.PET.NAME.asc())
                .fetchInto(Tables.PET)
                .stream()
                .map(PetRepresentationV1::new)
                .collect(Collectors.toList());
    }
}


