ALTER TABLE versefactory.box
ADD COLUMN event BOOLEAN NOT NULL DEFAULT false;

INSERT INTO versefactory.pet (id, name, rarity, income_per_second, base_cost) VALUES
('74eebc99-9c0b-4ef8-bb6d-6bb9bd380a20', 'Champignon', 'RARE', 80.0, 800.0),
('84eebc99-9c0b-4ef8-bb6d-6bb9bd380a20', 'Feuille', 'EPIC', 150.0, 1500.0),
('94eebc99-9c0b-4ef8-bb6d-6bb9bd380a20', 'Citrouille', 'LEGENDARY', 200.0, 2000.0);

INSERT INTO versefactory.pet (id, name, rarity, income_per_second, base_cost) VALUES
('a4eebc99-9c0b-4ef8-bb6d-6bb9bd380a20', 'Vampire', 'RARE', 100.0, 1000.0),
('beebc999-9c0b-4ef8-bb6d-6bb9bd380a20', 'Fantôme', 'EPIC', 180.0, 1800.0),
('ceebc999-9c0b-4ef8-bb6d-6bb9bd380a20', 'Sorcière', 'LEGENDARY', 250.0, 2500.0);

INSERT INTO versefactory.box (id, name, description, price, event) VALUES
('70eebc99-9c0b-4ef8-bb6d-6bb9bd380a20', 'Boîte Automne', 'Une boîte remplie de créatures automne.', 1000.0, true),
('80eebc99-9c0b-4ef8-bb6d-6bb9bd380a20', 'Boîte Halloween', 'Une boîte remplie de créatures halloween.', 1500.0, true);

INSERT INTO versefactory.box_pet (id, box_id, pet_id, drop_chance) VALUES
('71eebc99-9c0b-4ef8-bb6d-6bb9bd380a21', '70eebc99-9c0b-4ef8-bb6d-6bb9bd380a20', '74eebc99-9c0b-4ef8-bb6d-6bb9bd380a20', 55.00),
('72eebc99-9c0b-4ef8-bb6d-6bb9bd380a22', '70eebc99-9c0b-4ef8-bb6d-6bb9bd380a20', '84eebc99-9c0b-4ef8-bb6d-6bb9bd380a20', 40.00),
('73eebc99-9c0b-4ef8-bb6d-6bb9bd380a23', '70eebc99-9c0b-4ef8-bb6d-6bb9bd380a20', '94eebc99-9c0b-4ef8-bb6d-6bb9bd380a20', 5.00);

INSERT INTO versefactory.box_pet (id, box_id, pet_id, drop_chance) VALUES
('81eebc99-9c0b-4ef8-bb6d-6bb9bd380a21', '80eebc99-9c0b-4ef8-bb6d-6bb9bd380a20', 'a4eebc99-9c0b-4ef8-bb6d-6bb9bd380a20', 55.00),
('82eebc99-9c0b-4ef8-bb6d-6bb9bd380a22', '80eebc99-9c0b-4ef8-bb6d-6bb9bd380a20', 'beebc999-9c0b-4ef8-bb6d-6bb9bd380a20', 40.00),
('83eebc99-9c0b-4ef8-bb6d-6bb9bd380a23', '80eebc99-9c0b-4ef8-bb6d-6bb9bd380a20', 'ceebc999-9c0b-4ef8-bb6d-6bb9bd380a20', 5.00);