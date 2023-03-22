INSERT INTO staff_user (id, username, password,dtype) VALUES (1,'admin1', 'e00cf25ad42683b3df678c61f42c6bda', 'Admin');
INSERT INTO staff_user (id, username, password,dtype) VALUES (2,'admin2', 'c84258e9c39059a89ab77d846ddab909', 'Admin');
INSERT INTO staff_user (id, username, password,dtype) VALUES (3,'cashier1', '136989baac262ea3f560297aab280c8d', 'Cashier');

INSERT INTO band (name, genre) VALUES
('Metallica', 'Metal'),
('The Beatles', 'Rock'),
('Queen', 'Rock'),
('Led Zeppelin', 'Rock');


INSERT INTO concert (name, description, date_and_time, max_tickets) VALUES
('Metallica World Tour', 'The biggest Metallica tour ever', '2023-06-01', 50),
('The Beatles Reunion Concert', 'The Fab Four are back together', '2023-08-23', 100000),
('Queen Tribute Concert', 'A tribute to the legendary band', '2023-10-15', 20000),
('Led Zeppelin Reunion Show', 'The legendary band reunites for one night', '2024-03-07', 50000);


INSERT INTO ticket (price, concert_id, person, seats) VALUES
(100.00, 1, 'John Doe', 2),
(100.00, 2, 'John Doe', 2),
(75.00, 2, 'Jane Smith', 1),
(50.00, 3, 'Bob Johnson', 4),
(125.00, 4, 'Sarah Lee', 2);

INSERT INTO concert_bands (concert_id, bands_id) VALUES (1, 1);
INSERT INTO concert_bands (concert_id, bands_id) VALUES (2, 2);
INSERT INTO concert_bands (concert_id, bands_id) VALUES (3, 3);
INSERT INTO concert_bands (concert_id, bands_id) VALUES (4, 4);

