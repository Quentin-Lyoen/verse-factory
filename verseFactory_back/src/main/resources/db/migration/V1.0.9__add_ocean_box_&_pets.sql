INSERT INTO versefactory.pet (id, name, rarity, income_per_second, base_cost) VALUES
('51eebc99-9c0b-4ef8-bb6d-6bb9bd380a20', 'Requin', 'EPIC', 80.0, 8000.0),
('52eebc99-9c0b-4ef8-bb6d-6bb9bd380a20', 'Poisson-Clown', 'RARE', 30.0, 3000.0),
('53eebc99-9c0b-4ef8-bb6d-6bb9bd380a20', 'Poisson', 'COMMON', 15.0, 1500.0);

INSERT INTO versefactory.box (id, name, description, price) VALUES
('60eebc99-9c0b-4ef8-bb6d-6bb9bd380a20', 'Boîte Océan', 'Une boîte remplie de créatures marines.', 300.0);

INSERT INTO versefactory.box_pet (id, box_id, pet_id, drop_chance) VALUES
('61eebc99-9c0b-4ef8-bb6d-6bb9bd380a27', '60eebc99-9c0b-4ef8-bb6d-6bb9bd380a20', '51eebc99-9c0b-4ef8-bb6d-6bb9bd380a20', 5.00),
('62eebc99-9c0b-4ef8-bb6d-6bb9bd380a28', '60eebc99-9c0b-4ef8-bb6d-6bb9bd380a20', '52eebc99-9c0b-4ef8-bb6d-6bb9bd380a20', 40.00),
('63eebc99-9c0b-4ef8-bb6d-6bb9bd380a29', '60eebc99-9c0b-4ef8-bb6d-6bb9bd380a20', '53eebc99-9c0b-4ef8-bb6d-6bb9bd380a20', 55.00);