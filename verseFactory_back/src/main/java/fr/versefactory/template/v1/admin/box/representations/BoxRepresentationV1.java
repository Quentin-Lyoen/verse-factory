package fr.versefactory.template.v1.admin.box.representations;

import fr.versefactory.template.storage.tables.records.BoxRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoxRepresentationV1 {
    private BoxRecord box;
}
