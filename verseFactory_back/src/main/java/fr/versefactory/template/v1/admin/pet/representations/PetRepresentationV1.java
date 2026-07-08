package fr.versefactory.template.v1.admin.pet.representations;

import fr.versefactory.template.storage.tables.records.PetRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetRepresentationV1 {
    private PetRecord pet;
}
