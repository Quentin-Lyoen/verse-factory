package fr.versefactory.template.v1.admin.factory.representations;

import fr.versefactory.template.storage.tables.records.FactoryUpgradeRecord;
import fr.versefactory.template.storage.tables.records.UpgradeRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FactoryUpgradeRepresentationV1 {
    private UpgradeRecord upgrade;
    private FactoryUpgradeRecord factoryUpgrade;
}
