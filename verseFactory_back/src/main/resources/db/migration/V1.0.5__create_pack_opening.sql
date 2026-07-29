-- 1. Table des boîtes / packs
CREATE TABLE versefactory.box (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL DEFAULT 0.0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. Table de liaison boîte <-> pet avec taux de drop (pourcentage de chance)
CREATE TABLE versefactory.box_pet (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    box_id UUID NOT NULL,
    pet_id UUID NOT NULL,
    drop_chance DECIMAL(5, 2) NOT NULL,

    CONSTRAINT fk_box_pet_box FOREIGN KEY (box_id) REFERENCES versefactory.box(id) ON DELETE CASCADE,
    CONSTRAINT fk_box_pet_pet FOREIGN KEY (pet_id) REFERENCES versefactory.pet(id) ON DELETE CASCADE,
    CONSTRAINT uk_box_pet UNIQUE (box_id, pet_id)
);

CREATE INDEX idx_box_pet_box_id ON versefactory.box_pet(box_id);
CREATE INDEX idx_box_pet_pet_id ON versefactory.box_pet(pet_id);