CREATE TABLE versefactory.pet (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    rarity VARCHAR(20) NOT NULL,
    income_per_second decimal DEFAULT 1.0,
    base_cost decimal DEFAULT 0
);