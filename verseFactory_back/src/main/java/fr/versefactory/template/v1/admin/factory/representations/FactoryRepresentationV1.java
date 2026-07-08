package fr.versefactory.template.v1.admin.factory.representations;

import fr.versefactory.template.storage.tables.records.FactoryRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FactoryRepresentationV1 {
    private FactoryRecord factory;
}
