CREATE TABLE staff (
    staff_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INTEGER NOT NULL,
    salary DECIMAL(10, 2) NOT NULL,

    staff_type VARCHAR(20) NOT NULL CHECK (staff_type in ('CASHIER', 'GUARD')),

    -- Cashier specific
    charisma INTEGER,
    -- Guard specific
    equipment VARCHAR(100)
);

-- Add indexes for better performance
CREATE INDEX idx_staff_type ON staff(staff_type);
CREATE INDEX idx_staff_name ON staff(name);

INSERT INTO staff (name, age, salary, staff_type, charisma, equipment)
VALUES
    ('Mukhammed', 20, 25000.00, 'CASHIER', 40, NULL);

INSERT INTO staff (name, age, salary, staff_type, charisma, equipment)
VALUES
    ('Artur', 35, 90000.00, 'GUARD', NULL, 'pistol');

-- Verify data
SELECT * FROM staff ORDER BY staff_id;

SELECT COUNT(*) as total_staff FROM staff;
SELECT * FROM staff WHERE staff_type = 'CASHIER';
SELECT * FROM staff WHERE staff_type = 'GUARD';
SELECT * FROM staff WHERE salary > 100000 ORDER BY salary DESC;
SELECT * FROM staff WHERE age > 30 ORDER BY age DESC;

-- avg salary
SELECT staff_type, AVG(salary) as avg_salary, COUNT(*) as count
FROM staff
GROUP BY staff_type;

