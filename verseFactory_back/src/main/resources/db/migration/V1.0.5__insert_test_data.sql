-- Seed data for app_user
INSERT INTO versefactory.app_user (id, username) VALUES
('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'alice'),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 'bob'),
('c0eebc99-9c0b-4ef8-bb6d-6bb9bd380a13', 'charlie');

-- Seed data for factory
INSERT INTO versefactory.factory (id, user_id, balance) VALUES
('d0eebc99-9c0b-4ef8-bb6d-6bb9bd380a14', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 1000.00),
('e0eebc99-9c0b-4ef8-bb6d-6bb9bd380a15', 'b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 250.50),
('f0eebc99-9c0b-4ef8-bb6d-6bb9bd380a16', 'c0eebc99-9c0b-4ef8-bb6d-6bb9bd380a13', 0.00);

-- Seed data for pet
INSERT INTO versefactory.pet (id, name, rarity, income_per_second, base_cost) VALUES
('00eebc99-9c0b-4ef8-bb6d-6bb9bd380a17', 'Chien', 'COMMON', 1.5, 50.0),
('10eebc99-9c0b-4ef8-bb6d-6bb9bd380a18', 'Chat', 'COMMON', 2.0, 75.0),
('20eebc99-9c0b-4ef8-bb6d-6bb9bd380a19', 'Dragon', 'LEGENDARY', 50.0, 5000.0),
('30eebc99-9c0b-4ef8-bb6d-6bb9bd380a20', 'Licorne', 'EPIC', 15.0, 1200.0);

-- Seed data for factory_pet
INSERT INTO versefactory.factory_pet (id, factory_id, pet_id) VALUES
('40eebc99-9c0b-4ef8-bb6d-6bb9bd380a21', 'd0eebc99-9c0b-4ef8-bb6d-6bb9bd380a14', '00eebc99-9c0b-4ef8-bb6d-6bb9bd380a17'),
('50eebc99-9c0b-4ef8-bb6d-6bb9bd380a22', 'd0eebc99-9c0b-4ef8-bb6d-6bb9bd380a14', '20eebc99-9c0b-4ef8-bb6d-6bb9bd380a19'),
('60eebc99-9c0b-4ef8-bb6d-6bb9bd380a23', 'e0eebc99-9c0b-4ef8-bb6d-6bb9bd380a15', '10eebc99-9c0b-4ef8-bb6d-6bb9bd380a18'),
('70eebc99-9c0b-4ef8-bb6d-6bb9bd380a24', 'e0eebc99-9c0b-4ef8-bb6d-6bb9bd380a15', '30eebc99-9c0b-4ef8-bb6d-6bb9bd380a20');
