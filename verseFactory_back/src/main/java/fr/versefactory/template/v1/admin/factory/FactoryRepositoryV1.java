package fr.versefactory.template.v1.admin.factory;

import fr.versefactory.template.v1.core.TemplateRepositoryV1;
import fr.versefactory.template.v1.admin.factory.representations.FactoryRepresentationV1;
import fr.versefactory.template.storage.Tables;
import org.springframework.stereotype.Repository;

import fr.versefactory.template.storage.tables.records.FactoryUpgradeRecord;
import fr.versefactory.template.storage.tables.records.UpgradeRecord;
import fr.versefactory.template.v1.admin.factory.representations.FactoryUpgradeRepresentationV1;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class FactoryRepositoryV1 extends TemplateRepositoryV1 {

    public Optional<FactoryRepresentationV1> findByUserId(UUID userId) {
        return dslContext.selectFrom(Tables.FACTORY)
                .where(Tables.FACTORY.USER_ID.eq(userId))
                .fetchOptional()
                .map(FactoryRepresentationV1::new);
    }

    public Optional<FactoryRepresentationV1> findById(UUID id) {
        return dslContext.selectFrom(Tables.FACTORY)
                .where(Tables.FACTORY.ID.eq(id))
                .fetchOptional()
                .map(FactoryRepresentationV1::new);
    }

    public void updateBalance(UUID id, BigDecimal balance) {
        dslContext.update(Tables.FACTORY)
                .set(Tables.FACTORY.BALANCE, balance)
                .set(Tables.FACTORY.LAST_UPDATED_AT, LocalDateTime.now())
                .where(Tables.FACTORY.ID.eq(id))
                .execute();
    }

    public List<FactoryUpgradeRepresentationV1> findUpgradesByFactoryId(UUID factoryId) {
        return dslContext.select(
                        Tables.UPGRADE.asterisk(),
                        Tables.FACTORY_UPGRADE.asterisk()
                )
                .from(Tables.UPGRADE)
                .leftJoin(Tables.FACTORY_UPGRADE)
                .on(Tables.UPGRADE.ID.eq(Tables.FACTORY_UPGRADE.UPGRADE_ID)
                        .and(Tables.FACTORY_UPGRADE.FACTORY_ID.eq(factoryId)))
                .fetch(record -> {
                    UpgradeRecord upgradeRecord = record.into(Tables.UPGRADE);
                    FactoryUpgradeRecord factoryUpgradeRecord = record.into(Tables.FACTORY_UPGRADE);
                    if (factoryUpgradeRecord.getFactoryId() == null && factoryUpgradeRecord.getUpgradeId() == null) {
                        factoryUpgradeRecord = null;
                    }
                    return FactoryUpgradeRepresentationV1.builder()
                            .upgrade(upgradeRecord)
                            .factoryUpgrade(factoryUpgradeRecord)
                            .build();
                });
    }
}
