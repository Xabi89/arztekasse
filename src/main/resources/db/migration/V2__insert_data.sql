INSERT INTO places (id, label, location) VALUES
(1, 'Le Café du Marché', 'Rue de Conthey 17, 1950 Sion'),
(2, 'Casa Ferlin', 'Stampfenbachstrasse 38, 8006 Zürich');

INSERT INTO opening_hours (day_of_week, start_time, end_time, place_id) VALUES
(2, '11:30', '15:00', 1),
(2, '18:30', '00:00', 1),
(3, '11:30', '15:00', 1),
(3, '18:30', '00:00', 1),
(4, '11:30', '15:00', 1),
(4, '18:30', '00:00', 1),
(5, '11:30', '15:00', 1),
(5, '18:30', '00:00', 1),
(6, '18:00', '00:00', 1),
(7, '11:30', '15:00', 1);

INSERT INTO opening_hours (day_of_week, start_time, end_time, place_id) VALUES
(1, '11:30', '14:00', 2),
(1, '18:30', '22:00', 2),
(2, '11:30', '14:00', 2),
(2, '18:30', '22:00', 2),
(3, '11:30', '14:00', 2),
(3, '18:30', '22:00', 2),
(4, '11:30', '14:00', 2),
(4, '18:30', '22:00', 2),
(5, '11:30', '14:00', 2),
(5, '18:30', '22:00', 2);