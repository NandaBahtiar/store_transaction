-- data.sql

-- Users
INSERT INTO users (username, email, role) VALUES
('admin_user', 'admin@example.com', 'ADMIN'),
('staff_one', 'staff1@example.com', 'STAFF'),
('staff_two', 'staff2@example.com', 'STAFF'),
('customer_one', 'customer1@example.com', 'CUSTOMER'),
('customer_two', 'customer2@example.com', 'CUSTOMER');

-- Customers
INSERT INTO customers (name, birthdate, birthplace, created_by) VALUES
('John Doe', '1990-05-15', 'New York', 1),
('Jane Smith', '1988-11-22', 'Los Angeles', 1),
('Peter Jones', '1992-03-01', 'Chicago', 2),
('Alice Brown', '1985-07-30', 'Houston', 2),
('Bob White', '1995-01-10', 'Miami', 1),
('Charlie Green', '1980-09-03', 'Seattle', 3),
('Diana Prince', '1993-04-12', 'London', 3),
('Eve Adams', '1987-06-25', 'Paris', 1),
('Frank Black', '1991-02-18', 'Berlin', 2),
('Grace Kelly', '1983-10-07', 'Rome', 3),
('Harry Potter', '1990-07-31', 'Godric''s Hollow', 1),
('Hermione Granger', '1979-09-19', 'London', 2),
('Ron Weasley', '1980-03-01', 'Ottery St Catchpole', 3),
('Albus Dumbledore', '1881-08-01', 'Mould-on-the-Wold', 1),
('Minerva McGonagall', '1925-10-04', 'Caithness', 2);

-- Products
INSERT INTO products (name, base_price) VALUES
('Laptop', 1200.00),
('Mouse', 25.00),
('Keyboard', 75.00),
('Monitor', 300.00),
('Webcam', 50.00),
('Headphones', 150.00),
('Microphone', 80.00),
('Desk Chair', 250.00),
('Desk', 400.00),
('External SSD 1TB', 100.00),
('USB Hub', 20.00),
('Printer', 180.00),
('Router', 90.00),
('Smartphone', 800.00),
('Tablet', 450.00),
('Smartwatch', 200.00),
('Fitness Tracker', 70.00),
('E-Reader', 120.00),
('Gaming Console', 500.00),
('VR Headset', 350.00);

-- Taxes
INSERT INTO taxes (name, percentage) VALUES
('VAT 20%', 20.00),
('Sales Tax 8%', 8.00),
('Luxury Tax 10%', 10.00),
('Eco Tax 2%', 2.00),
('Service Tax 5%', 5.00),
('Import Duty 15%', 15.00),
('Digital Tax 3%', 3.00),
('Local Tax 1%', 1.00),
('Federal Tax 7%', 7.00),
('State Tax 6%', 6.00);

-- Product-Taxes (Example relationships)
INSERT INTO product_taxes (product_id, tax_id) VALUES
(1, 1), (1, 3), (1, 7), -- Laptop: VAT, Luxury, Digital
(2, 2), (2, 8),         -- Mouse: Sales, Local
(3, 1), (3, 2),         -- Keyboard: VAT, Sales
(4, 1), (4, 3),         -- Monitor: VAT, Luxury
(5, 2),                 -- Webcam: Sales
(6, 1), (6, 5),         -- Headphones: VAT, Service
(7, 2),                 -- Microphone: Sales
(8, 1), (8, 4),         -- Desk Chair: VAT, Eco
(9, 1),                 -- Desk: VAT
(10, 2), (10, 7),       -- External SSD: Sales, Digital
(11, 2),                -- USB Hub: Sales
(12, 1), (12, 5),       -- Printer: VAT, Service
(13, 2),                -- Router: Sales
(14, 1), (14, 3), (14, 7), -- Smartphone: VAT, Luxury, Digital
(15, 1), (15, 7),       -- Tablet: VAT, Digital
(16, 2), (16, 7),       -- Smartwatch: Sales, Digital
(17, 2),                -- Fitness Tracker: Sales
(18, 1), (18, 7),       -- E-Reader: VAT, Digital
(19, 1), (19, 3), (19, 7), -- Gaming Console: VAT, Luxury, Digital
(20, 1), (20, 3), (20, 7); -- VR Headset: VAT, Luxury, Digital

-- Transactions (simplified for data.sql, actual calculation done by service)
-- Note: net_amount, total_amount, total_tax will be calculated by the application
INSERT INTO transactions (customer_id, transaction_time, payment_status, payment_method, staff_id) VALUES
(1, '2024-01-10 10:00:00', 'PAID', 'CARD', 2),
(2, '2024-01-11 11:30:00', 'NOT_PAID', 'CASH', 3),
(3, '2024-01-12 14:00:00', 'PAID', 'TRANSFER', 2),
(1, '2024-02-01 09:00:00', 'PAID', 'CARD', 2),
(4, '2024-02-05 16:00:00', 'NOT_PAID', 'CASH', 3),
(5, '2024-03-15 10:00:00', 'PAID', 'CARD', 2),
(6, '2024-03-20 11:30:00', 'PAID', 'TRANSFER', 3),
(7, '2024-04-01 14:00:00', 'NOT_PAID', 'CASH', 2),
(8, '2024-04-10 09:00:00', 'PAID', 'CARD', 3),
(9, '2024-05-01 16:00:00', 'PAID', 'TRANSFER', 2),
(10, '2024-05-10 10:00:00', 'NOT_PAID', 'CASH', 3),
(11, '2024-06-01 11:30:00', 'PAID', 'CARD', 2),
(12, '2024-06-10 14:00:00', 'PAID', 'TRANSFER', 3),
(13, '2024-07-01 09:00:00', 'NOT_PAID', 'CASH', 2),
(14, '2024-07-10 16:00:00', 'PAID', 'CARD', 3),
(15, '2024-08-01 10:00:00', 'PAID', 'TRANSFER', 2),
(1, '2024-08-05 11:30:00', 'PAID', 'CASH', 3),
(2, '2024-09-01 14:00:00', 'NOT_PAID', 'CARD', 2),
(3, '2024-09-10 09:00:00', 'PAID', 'TRANSFER', 3),
(4, '2024-10-01 16:00:00', 'PAID', 'CASH', 2),
(5, '2024-10-10 10:00:00', 'NOT_PAID', 'CARD', 3),
(6, '2024-11-01 11:30:00', 'PAID', 'TRANSFER', 2),
(7, '2024-11-10 14:00:00', 'PAID', 'CASH', 3),
(8, '2024-12-01 09:00:00', 'NOT_PAID', 'CARD', 2),
(9, '2024-12-10 16:00:00', 'PAID', 'TRANSFER', 3),
(10, '2025-01-01 10:00:00', 'PAID', 'CASH', 2),
(11, '2025-01-05 11:30:00', 'NOT_PAID', 'CARD', 3),
(12, '2025-02-01 14:00:00', 'PAID', 'TRANSFER', 2),
(13, '2025-02-05 09:00:00', 'PAID', 'CASH', 3),
(14, '2025-03-01 16:00:00', 'NOT_PAID', 'CARD', 2);

-- Transaction Details (simplified, actual calculation by service)
-- Transaction 1 (customer 1, staff 2): Laptop, Mouse
INSERT INTO transaction_details (transaction_id, product_id, quantity, unit_price, subtotal, tax_amount) VALUES
(1, 1, 1, 1200.00, 1200.00, 384.00), -- Laptop (VAT 20%, Luxury 10%, Digital 3%) = 33%
(1, 2, 2, 25.00, 50.00, 4.50);    -- Mouse (Sales 8%, Local 1%) = 9%

-- Transaction 2 (customer 2, staff 3): Keyboard, Monitor
INSERT INTO transaction_details (transaction_id, product_id, quantity, unit_price, subtotal, tax_amount) VALUES
(2, 3, 1, 75.00, 75.00, 21.00),   -- Keyboard (VAT 20%, Sales 8%) = 28%
(2, 4, 1, 300.00, 300.00, 90.00); -- Monitor (VAT 20%, Luxury 10%) = 30%

-- Transaction 3 (customer 3, staff 2): Webcam, Headphones
INSERT INTO transaction_details (transaction_id, product_id, quantity, unit_price, subtotal, tax_amount) VALUES
(3, 5, 1, 50.00, 50.00, 4.00),    -- Webcam (Sales 8%)
(3, 6, 1, 150.00, 150.00, 37.50); -- Headphones (VAT 20%, Service 5%) = 25%

-- Transaction 4 (customer 1, staff 2): External SSD, USB Hub
INSERT INTO transaction_details (transaction_id, product_id, quantity, unit_price, subtotal, tax_amount) VALUES
(4, 10, 1, 100.00, 100.00, 11.00), -- External SSD (Sales 8%, Digital 3%) = 11%
(4, 11, 3, 20.00, 60.00, 4.80);   -- USB Hub (Sales 8%)

-- Transaction 5 (customer 4, staff 3): Printer, Router
INSERT INTO transaction_details (transaction_id, product_id, quantity, unit_price, subtotal, tax_amount) VALUES
(5, 12, 1, 180.00, 180.00, 45.00), -- Printer (VAT 20%, Service 5%) = 25%
(5, 13, 1, 90.00, 90.00, 7.20);   -- Router (Sales 8%)

-- Transaction 6 (customer 5, staff 2): Smartphone, Tablet
INSERT INTO transaction_details (transaction_id, product_id, quantity, unit_price, subtotal, tax_amount) VALUES
(6, 14, 1, 800.00, 800.00, 264.00), -- Smartphone (VAT 20%, Luxury 10%, Digital 3%) = 33%
(6, 15, 1, 450.00, 450.00, 103.50); -- Tablet (VAT 20%, Digital 3%) = 23%

-- Transaction 7 (customer 6, staff 3): Smartwatch, Fitness Tracker
INSERT INTO transaction_details (transaction_id, product_id, quantity, unit_price, subtotal, tax_amount) VALUES
(7, 16, 1, 200.00, 200.00, 22.00), -- Smartwatch (Sales 8%, Digital 3%) = 11%
(7, 17, 1, 70.00, 70.00, 5.60);   -- Fitness Tracker (Sales 8%)

-- Transaction 8 (customer 7, staff 2): E-Reader, Gaming Console
INSERT INTO transaction_details (transaction_id, product_id, quantity, unit_price, subtotal, tax_amount) VALUES
(8, 18, 1, 120.00, 120.00, 27.60), -- E-Reader (VAT 20%, Digital 3%) = 23%
(8, 19, 1, 500.00, 500.00, 165.00); -- Gaming Console (VAT 20%, Luxury 10%, Digital 3%) = 33%

-- Transaction 9 (customer 8, staff 3): VR Headset, Laptop
INSERT INTO transaction_details (transaction_id, product_id, quantity, unit_price, subtotal, tax_amount) VALUES
(9, 20, 1, 350.00, 350.00, 115.50), -- VR Headset (VAT 20%, Luxury 10%, Digital 3%) = 33%
(9, 1, 1, 1200.00, 1200.00, 384.00); -- Laptop (VAT 20%, Luxury 10%, Digital 3%) = 33%

-- Transaction 10 (customer 9, staff 2): Mouse, Keyboard
INSERT INTO transaction_details (transaction_id, product_id, quantity, unit_price, subtotal, tax_amount) VALUES
(10, 2, 1, 25.00, 25.00, 2.25),   -- Mouse (Sales 8%, Local 1%) = 9%
(10, 3, 1, 75.00, 75.00, 21.00);  -- Keyboard (VAT 20%, Sales 8%) = 28%

-- Transaction 11 (customer 10, staff 3): Monitor, Webcam
INSERT INTO transaction_details (transaction_id, product_id, quantity, unit_price, subtotal, tax_amount) VALUES
(11, 4, 1, 300.00, 300.00, 90.00), -- Monitor (VAT 20%, Luxury 10%) = 30%
(11, 5, 1, 50.00, 50.00, 4.00);   -- Webcam (Sales 8%)

-- Transaction 12 (customer 11, staff 2): Headphones, Microphone
INSERT INTO transaction_details (transaction_id, product_id, quantity, unit_price, subtotal, tax_amount) VALUES
(12, 6, 1, 150.00, 150.00, 37.50), -- Headphones (VAT 20%, Service 5%) = 25%
(12, 7, 1, 80.00, 80.00, 6.40);   -- Microphone (Sales 8%)

-- Transaction 13 (customer 12, staff 3): Desk Chair, Desk
INSERT INTO transaction_details (transaction_id, product_id, quantity, unit_price, subtotal, tax_amount) VALUES
(13, 8, 1, 250.00, 250.00, 55.00), -- Desk Chair (VAT 20%, Eco 2%) = 22%
(13, 9, 1, 400.00, 400.00, 80.00); -- Desk (VAT 20%)

-- Transaction 14 (customer 13, staff 2): External SSD, USB Hub
INSERT INTO transaction_details (transaction_id, product_id, quantity, unit_price, subtotal, tax_amount) VALUES
(14, 10, 1, 100.00, 100.00, 11.00), -- External SSD (Sales 8%, Digital 3%) = 11%
(14, 11, 1, 20.00, 20.00, 1.60);   -- USB Hub (Sales 8%)

-- Transaction 15 (customer 14, staff 3): Printer, Router
INSERT INTO transaction_details (transaction_id, product_id, quantity, unit_price, subtotal, tax_amount) VALUES
(15, 12, 1, 180.00, 180.00, 45.00), -- Printer (VAT 20%, Service 5%) = 25%
(15, 13, 1, 90.00, 90.00, 7.20);   -- Router (Sales 8%)

-- Transaction 16 (customer 15, staff 2): Smartphone, Tablet
INSERT INTO transaction_details (transaction_id, product_id, quantity, unit_price, subtotal, tax_amount) VALUES
(16, 14, 1, 800.00, 800.00, 264.00), -- Smartphone (VAT 20%, Luxury 10%, Digital 3%) = 33%
(16, 15, 1, 450.00, 450.00, 103.50); -- Tablet (VAT 20%, Digital 3%) = 23%

-- Transaction 17 (customer 1, staff 3): Smartwatch, Fitness Tracker
INSERT INTO transaction_details (transaction_id, product_id, quantity, unit_price, subtotal, tax_amount) VALUES
(17, 16, 1, 200.00, 200.00, 22.00), -- Smartwatch (Sales 8%, Digital 3%) = 11%
(17, 17, 1, 70.00, 70.00, 5.60);   -- Fitness Tracker (Sales 8%)

-- Transaction 18 (customer 2, staff 2): E-Reader, Gaming Console
INSERT INTO transaction_details (transaction_id, product_id, quantity, unit_price, subtotal, tax_amount) VALUES
(18, 18, 1, 120.00, 120.00, 27.60), -- E-Reader (VAT 20%, Digital 3%) = 23%
(18, 19, 1, 500.00, 500.00, 165.00); -- Gaming Console (VAT 20%, Luxury 10%, Digital 3%) = 33%

-- Transaction 19 (customer 3, staff 3): VR Headset, Laptop
INSERT INTO transaction_details (transaction_id, product_id, quantity, unit_price, subtotal, tax_amount) VALUES
(19, 20, 1, 350.00, 350.00, 115.50), -- VR Headset (VAT 20%, Luxury 10%, Digital 3%) = 33%
(19, 1, 1, 1200.00, 1200.00, 384.00); -- Laptop (VAT 20%, Luxury 10%, Digital 3%) = 33%

-- Transaction 20 (customer 4, staff 2): Mouse, Keyboard
INSERT INTO transaction_details (transaction_id, product_id, quantity, unit_price, subtotal, tax_amount) VALUES
(20, 2, 1, 25.00, 25.00, 2.25),   -- Mouse (Sales 8%, Local 1%) = 9%
(20, 3, 1, 75.00, 75.00, 21.00);  -- Keyboard (VAT 20%, Sales 8%) = 28%

-- Transaction 21 (customer 5, staff 3): Monitor, Webcam
INSERT INTO transaction_details (transaction_id, product_id, quantity, unit_price, subtotal, tax_amount) VALUES
(21, 4, 1, 300.00, 300.00, 90.00), -- Monitor (VAT 20%, Luxury 10%) = 30%
(21, 5, 1, 50.00, 50.00, 4.00);   -- Webcam (Sales 8%)

-- Transaction 22 (customer 6, staff 2): Headphones, Microphone
INSERT INTO transaction_details (transaction_id, product_id, quantity, unit_price, subtotal, tax_amount) VALUES
(22, 6, 1, 150.00, 150.00, 37.50), -- Headphones (VAT 20%, Service 5%) = 25%
(22, 7, 1, 80.00, 80.00, 6.40);   -- Microphone (Sales 8%)

-- Transaction 23 (customer 7, staff 3): Desk Chair, Desk
INSERT INTO transaction_details (transaction_id, product_id, quantity, unit_price, subtotal, tax_amount) VALUES
(23, 8, 1, 250.00, 250.00, 55.00), -- Desk Chair (VAT 20%, Eco 2%) = 22%
(23, 9, 1, 400.00, 400.00, 80.00); -- Desk (VAT 20%)

-- Transaction 24 (customer 8, staff 2): External SSD, USB Hub
INSERT INTO transaction_details (transaction_id, product_id, quantity, unit_price, subtotal, tax_amount) VALUES
(24, 10, 1, 100.00, 100.00, 11.00), -- External SSD (Sales 8%, Digital 3%) = 11%
(24, 11, 1, 20.00, 20.00, 1.60);   -- USB Hub (Sales 8%)

-- Transaction 25 (customer 9, staff 3): Printer, Router
INSERT INTO transaction_details (transaction_id, product_id, quantity, unit_price, subtotal, tax_amount) VALUES
(25, 12, 1, 180.00, 180.00, 45.00), -- Printer (VAT 20%, Service 5%) = 25%
(25, 13, 1, 90.00, 90.00, 7.20);   -- Router (Sales 8%)

-- Transaction 26 (customer 10, staff 2): Smartphone, Tablet
INSERT INTO transaction_details (transaction_id, product_id, quantity, unit_price, subtotal, tax_amount) VALUES
(26, 14, 1, 800.00, 800.00, 264.00), -- Smartphone (VAT 20%, Luxury 10%, Digital 3%) = 33%
(26, 15, 1, 450.00, 450.00, 103.50); -- Tablet (VAT 20%, Digital 3%) = 23%

-- Transaction 27 (customer 11, staff 3): Smartwatch, Fitness Tracker
INSERT INTO transaction_details (transaction_id, product_id, quantity, unit_price, subtotal, tax_amount) VALUES
(27, 16, 1, 200.00, 200.00, 22.00), -- Smartwatch (Sales 8%, Digital 3%) = 11%
(27, 17, 1, 70.00, 70.00, 5.60);   -- Fitness Tracker (Sales 8%)

-- Transaction 28 (customer 12, staff 2): E-Reader, Gaming Console
INSERT INTO transaction_details (transaction_id, product_id, quantity, unit_price, subtotal, tax_amount) VALUES
(28, 18, 1, 120.00, 120.00, 27.60), -- E-Reader (VAT 20%, Digital 3%) = 23%
(28, 19, 1, 500.00, 500.00, 165.00); -- Gaming Console (VAT 20%, Luxury 10%, Digital 3%) = 33%

-- Transaction 29 (customer 13, staff 3): VR Headset, Laptop
INSERT INTO transaction_details (transaction_id, product_id, quantity, unit_price, subtotal, tax_amount) VALUES
(29, 20, 1, 350.00, 350.00, 115.50), -- VR Headset (VAT 20%, Luxury 10%, Digital 3%) = 33%
(29, 1, 1, 1200.00, 1200.00, 384.00); -- Laptop (VAT 20%, Luxury 10%, Digital 3%) = 33%

-- Transaction 30 (customer 14, staff 2): Mouse, Keyboard
INSERT INTO transaction_details (transaction_id, product_id, quantity, unit_price, subtotal, tax_amount) VALUES
(30, 2, 1, 25.00, 25.00, 2.25),   -- Mouse (Sales 8%, Local 1%) = 9%
(30, 3, 1, 75.00, 75.00, 21.00);  -- Keyboard (VAT 20%, Sales 8%) = 28%;
