-- Sample data for medicine inventory. Run manually if needed.
INSERT INTO medicines (name, company_name, composition, type, price, stock_quantity, expiry_date, medicine_class, shelf_location, description, low_stock_threshold)
SELECT 'Paracetamol 500', 'ABC Pharma', 'Paracetamol 500mg', 'Tablet', 20.00, 120, '2027-12-31', 'Painkiller', 'A1', 'Used for fever and mild pain.', 20
WHERE NOT EXISTS (SELECT 1 FROM medicines WHERE name = 'Paracetamol 500');

INSERT INTO medicines (name, company_name, composition, type, price, stock_quantity, expiry_date, medicine_class, shelf_location, description, low_stock_threshold)
SELECT 'Amoxicillin', 'MediLife', 'Amoxicillin 250mg', 'Capsule', 65.00, 8, '2026-03-15', 'Antibiotic', 'B3', 'Broad-spectrum antibiotic.', 10
WHERE NOT EXISTS (SELECT 1 FROM medicines WHERE name = 'Amoxicillin');

INSERT INTO medicines (name, company_name, composition, type, price, stock_quantity, expiry_date, medicine_class, shelf_location, description, low_stock_threshold)
SELECT 'CoughRelief Syrup', 'CureWell', 'Dextromethorphan + Chlorpheniramine', 'Syrup', 95.50, 0, '2026-01-10', 'Cough Suppressant', 'C2', 'Relief from dry cough.', 5
WHERE NOT EXISTS (SELECT 1 FROM medicines WHERE name = 'CoughRelief Syrup');

INSERT INTO medicines (name, company_name, composition, type, price, stock_quantity, expiry_date, medicine_class, shelf_location, description, low_stock_threshold)
SELECT 'Vitamin C', 'HealthPlus', 'Ascorbic Acid 500mg', 'Tablet', 150.00, 40, '2025-01-01', 'Supplement', 'D4', 'Immunity support.', 15
WHERE NOT EXISTS (SELECT 1 FROM medicines WHERE name = 'Vitamin C');
