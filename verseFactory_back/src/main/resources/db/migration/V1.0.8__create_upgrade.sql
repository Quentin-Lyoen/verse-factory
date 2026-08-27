CREATE TABLE versefactory.upgrade (
    id VARCHAR(25) PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255) NOT NULL,
    type VARCHAR(50) NOT NULL,
    max_level INT DEFAULT NULL
);

INSERT INTO versefactory.upgrade (id, name, description, type, max_level) VALUES
('PET_STORAGE', 'Amélioration du stockage', 'Augmente la capacité de stockage dans la factory.', 'STORAGE', 10);


CREATE TABLE versefactory.factory_upgrade (
    factory_id UUID NOT NULL,
    upgrade_id VARCHAR(25) NOT NULL,
    level INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT factory_upgrade_pk PRIMARY KEY (factory_id, upgrade_id),
    CONSTRAINT factory_upgrade_factory_fk FOREIGN KEY (factory_id) REFERENCES versefactory.factory(id),
    CONSTRAINT factory_upgrade_upgrade_fk FOREIGN KEY (upgrade_id) REFERENCES versefactory.upgrade(id)
);

CREATE INDEX idx_factory_upgrade_factory_id ON versefactory.factory_upgrade(factory_id);
CREATE INDEX idx_factory_upgrade_upgrade_id ON versefactory.factory_upgrade(upgrade_id);

INSERT INTO versefactory.upgrade (id, name, description, type, max_level) VALUES
('BALANCE_COOLDOWN', 'Temps de recharge', 'Réduit le temps d''attente pour récolter les revenus de la factory.', 'COOLDOWN', 5);
