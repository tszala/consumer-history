INSERT INTO consumers(id, firstname, surname) VALUES(1, 'John','Doe');
INSERT INTO consumers(id, firstname, surname) VALUES(2, 'Frank', 'Smith');

INSERT INTO orders(id, consumer_id, order_value, order_date) VALUES(1, 1, 100.0, '2018-12-30 01:00:00');
INSERT INTO orders(id, consumer_id, order_value, order_date) VALUES(2, 1, 300.0, '2019-01-30 01:00:00');
INSERT INTO orders(id, consumer_id, order_value, order_date) VALUES(3, 1, 400.5, '2018-11-30 01:00:00');
INSERT INTO orders(id, consumer_id, order_value, order_date) VALUES(4, 1, 50.7, '2019-02-28 01:00:00');