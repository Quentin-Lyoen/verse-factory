package fr.versefactory.template.v1.admin.box.representations;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import fr.versefactory.template.storage.tables.records.BoxPetRecord;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoxPetDropRepresentationV1 {
    private BoxPetRecord boxPetRecord;
}
