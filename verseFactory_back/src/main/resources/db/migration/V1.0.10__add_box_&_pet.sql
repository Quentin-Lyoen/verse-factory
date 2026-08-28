INSERT INTO versefactory.pet (id, name, rarity, income_per_second, base_cost) VALUES
('54eebc99-9c0b-4ef8-bb6d-6bb9bd380a20', 'Méduse', 'LEGENDARY', 150.0, 15000.0);

INSERT INTO versefactory.box (id, name, description, price) VALUES
('64eebc99-9c0b-4ef8-bb6d-6bb9bd380a20', 'Boîte Méduse', 'Une boîte avec une chance d''obtenir une méduse', 650.0);

INSERT INTO versefactory.box_pet (box_id, pet_id, drop_chance) VALUES
('64eebc99-9c0b-4ef8-bb6d-6bb9bd380a20', '54eebc99-9c0b-4ef8-bb6d-6bb9bd380a20', 5.0),
('64eebc99-9c0b-4ef8-bb6d-6bb9bd380a20', '51eebc99-9c0b-4ef8-bb6d-6bb9bd380a20', 40.0),
('64eebc99-9c0b-4ef8-bb6d-6bb9bd380a20', '52eebc99-9c0b-4ef8-bb6d-6bb9bd380a20', 55.0);