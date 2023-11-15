INSERT INTO address (address_id, city, postal_code, street)
VALUES ('1', 'Warszawa', '00-001', 'ul. Przykladowa 1');

INSERT INTO address (address_id, city, postal_code, street)
VALUES ('2', 'Krakow', '30-002', 'ul. Testowa 2');

INSERT INTO address (address_id, city, postal_code, street)
VALUES ('3', 'Gdansk', '80-003', 'ul. Przykladowa 3');

INSERT INTO address (address_id, city, postal_code, street)
VALUES ('4', 'Poznan', '60-004', 'ul. Testowa 4');

INSERT INTO address (address_id, city, postal_code, street)
VALUES ('5', 'Wroclaw', '50-005', 'ul. Przykladowa 5');

INSERT INTO phone_number (phone_number_id, number)
VALUES ('101', '+48 123 456 789');

INSERT INTO phone_number (phone_number_id, number)
VALUES ('102', '+48 987 654 321');

INSERT INTO phone_number (phone_number_id, number)
VALUES ('103', '+48 555 123 456');

INSERT INTO phone_number (phone_number_id, number)
VALUES ('104', '+48 777 987 654');

INSERT INTO phone_number (phone_number_id, number)
VALUES ('105', '+48 333 111 222');

INSERT INTO person (id, first_name, last_name, address_id, phone_number_id)
VALUES ('1001', 'Jan', 'Kowalski', '1', '101');

INSERT INTO person (id, first_name, last_name, address_id, phone_number_id)
VALUES ('1002', 'Anna', 'Nowak', '2', '102');

INSERT INTO person (id, first_name, last_name, address_id, phone_number_id)
VALUES ('1003', 'Marta', 'Wisniewska', '3', '103');

INSERT INTO person (id, first_name, last_name, address_id, phone_number_id)
VALUES ('1004', 'Piotr', 'Zawadzki', '4', '104');

INSERT INTO person (id, first_name, last_name, address_id, phone_number_id)
VALUES ('1005', 'Agnieszka', 'Szymanska', '5', '105');
