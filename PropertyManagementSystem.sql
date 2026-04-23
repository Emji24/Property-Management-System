-- Drop existing tables if needed (be careful with data)
-- DROP DATABASE propertymanagementsystem;
-- CREATE DATABASE propertymanagementsystem;
USE propertymanagementsystem;

-- Table for Property (matches Java code)
CREATE TABLE IF NOT EXISTS property (
    property_id VARCHAR(50) PRIMARY KEY,
    address VARCHAR(255),
    owner_no VARCHAR(50),
    owner_id VARCHAR(50),
    p_type VARCHAR(50),
    rooms VARCHAR(10)
);

-- Table for Tenant (matches Java code)
CREATE TABLE IF NOT EXISTS tenant (
    tenant_id VARCHAR(50) PRIMARY KEY,
    tenant_no VARCHAR(50),
    owner_id VARCHAR(50),
    property_rent VARCHAR(50),
    start_date VARCHAR(50),
    end_date VARCHAR(50),
    tenant_name VARCHAR(100)
);

-- Table for Appointment (matches Java code)
CREATE TABLE IF NOT EXISTS appointment (
    appointment_id VARCHAR(50) PRIMARY KEY,
    date VARCHAR(50),
    time VARCHAR(50),
    client_id VARCHAR(50),
    p_id VARCHAR(50)
);

-- Table for Comments (matches Java code)
CREATE TABLE IF NOT EXISTS comments (
    clientid VARCHAR(50) PRIMARY KEY,
    p_id VARCHAR(50),
    date VARCHAR(50),
    comment TEXT
);

-- Table for Users login (matches Java code)
CREATE TABLE IF NOT EXISTS users (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(50)
);

-- Insert default admin user
INSERT INTO users (username, password) VALUES ('admin', 'admin123')
ON DUPLICATE KEY UPDATE username = username;

-- Insert sample data
INSERT INTO property (property_id, address, owner_no, owner_id, p_type, rooms) VALUES
('P001', '123 Main St', '555-0101', 'OWN001', 'House', '4'),
('P002', '456 Oak Ave', '555-0102', 'OWN002', 'Apartment', '2'),
('P003', '789 Pine Rd', '555-0103', 'OWN003', 'Condo', '3');

INSERT INTO tenant (tenant_id, tenant_no, owner_id, property_rent, start_date, end_date, tenant_name) VALUES
('T001', 'TEN001', 'OWN001', '2000', '2024-01-01', '2024-12-31', 'John Doe'),
('T002', 'TEN002', 'OWN002', '1500', '2024-02-01', '2025-01-31', 'Jane Smith');

INSERT INTO users (username, password) VALUES ('client1', 'client123');