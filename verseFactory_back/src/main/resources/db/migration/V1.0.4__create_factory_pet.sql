CREATE TABLE versefactory.factory_pet (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    factory_id UUID NOT NULL,
    pet_id UUID NOT NULL,
    acquired_at timestamp DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_factory_pet_factory FOREIGN KEY (factory_id) REFERENCES versefactory.factory(id),
    CONSTRAINT fk_factory_pet_pet FOREIGN KEY (pet_id) REFERENCES versefactory.pet(id)
);

CREATE INDEX idx_factory_pet_factory_id ON versefactory.factory_pet(factory_id);
CREATE INDEX idx_factory_pet_pet_id ON versefactory.factory_pet(pet_id);