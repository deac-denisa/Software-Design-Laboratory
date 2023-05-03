INSERT INTO person (full_name, email, password, dtype) VALUES
    ('John Doe', 'john.doe@example.com', '7c6a180b36896a0a8c02787eeafb0e4c', 'Employee');
INSERT INTO person (full_name, email, password, dtype) VALUES
    ('Jane Doe', 'jane.doe@example.com', '7c6a180b36896a0a8c02787eeafb0e4c', 'Administrator');
INSERT INTO person (full_name, email, password, dtype) VALUES
    ('Alice Lee', 'alice.lee@example.com', '7c6a180b36896a0a8c02787eeafb0e4c', 'Client');
INSERT INTO person (full_name, email, password, dtype) VALUES
    ('Grad Ionut', 'gradionut.2001@gmail.com', '7c6a180b36896a0a8c02787eeafb0e4c', 'Client');

INSERT INTO vacation (dtype, name, type, description, location, price, start_date, end_date) VALUES
    ('Cruise', 'Caribbean Cruise',0, 'Cruise through the beautiful Caribbean islands', 'Caribbean', 1000.00, '2023-05-01', '2023-05-08');
INSERT INTO vacation (dtype, name, type, description, location, price, start_date, end_date) VALUES
    ('Stay', 'Safari Adventure', 1, 'Experience the African wilderness on a thrilling safari', 'Africa', 2000.00, '2023-06-01', '2023-06-08');
INSERT INTO vacation (dtype, name, type, description, location, price, start_date, end_date) VALUES
    ('Tour', 'Tour in Italy', 2,'Visiting museums', 'Italy', 800.00, '2023-06-02', '2023-06-10');

INSERT INTO sightseeing_trip (name, description, price) VALUES
    ('City Tour', 'Guided tour of the city`s landmarks and attractions', 100.00),
('Nature Hike', 'Explore the scenic countryside and enjoy nature',50.00);

INSERT INTO vacation_sightseeing_trips (stay_id, sightseeing_trips_id) VALUES (2, 2), (2,1);

INSERT INTO reservation (client_id, vacation_id) VALUES  (3, 1), (3, 2);
