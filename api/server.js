const express = require('express');
const cors = require('cors');
const dotenv = require('dotenv');
const mysql = require('mysql2/promise');
const jwt = require('jsonwebtoken');

dotenv.config();

const app = express();
const PORT = process.env.PORT || 3000;

// Middleware
app.use(cors());
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

// Database connection pool
const pool = mysql.createPool({
    host: process.env.DB_HOST,
    user: process.env.DB_USER,
    password: process.env.DB_PASSWORD,
    database: process.env.DB_NAME,
    waitForConnections: true,
    connectionLimit: 10,
    queueLimit: 0
});

// Test database connection
async function testConnection() {
    try {
        const connection = await pool.getConnection();
        console.log('✅ MySQL database connected successfully');
        connection.release();
        return true;
    } catch (error) {
        console.error('❌ MySQL connection failed:', error.message);
        return false;
    }
}

// ========== HEALTH CHECK ==========
app.get('/health', (req, res) => {
    res.json({ status: 'OK', timestamp: new Date().toISOString() });
});

app.get('/', (req, res) => {
    res.json({ 
        message: 'Property Management API is running!',
        endpoints: {
            health: 'GET /health',
            users: 'GET /api/users',
            properties: 'GET /api/properties',
            tenants: 'GET /api/tenants',
            appointments: 'GET /api/appointments',
            comments: 'GET /api/comments',
            login: 'POST /api/auth/login',
            register: 'POST /api/auth/register'
        }
    });
});

// ========== USERS ENDPOINT (NEW) ==========
// Get all users
app.get('/api/users', async (req, res) => {
    try {
        const [rows] = await pool.query('SELECT username FROM users');
        res.json({
            success: true,
            count: rows.length,
            data: rows
        });
    } catch (error) {
        console.error('Error fetching users:', error);
        res.status(500).json({ 
            success: false, 
            error: 'Database error',
            message: error.message 
        });
    }
});

// Get single user by username
app.get('/api/users/:username', async (req, res) => {
    const { username } = req.params;
    try {
        const [rows] = await pool.query(
            'SELECT username FROM users WHERE username = ?',
            [username]
        );
        
        if (rows.length === 0) {
            return res.status(404).json({ 
                success: false, 
                message: 'User not found' 
            });
        }
        
        res.json({
            success: true,
            data: rows[0]
        });
    } catch (error) {
        console.error('Error fetching user:', error);
        res.status(500).json({ 
            success: false, 
            error: 'Database error',
            message: error.message 
        });
    }
});

// ========== AUTHENTICATION ==========
app.post('/api/auth/login', async (req, res) => {
    const { username, password } = req.body;
    
    if (!username || !password) {
        return res.status(400).json({ 
            success: false, 
            message: 'Username and password required' 
        });
    }
    
    try {
        const [users] = await pool.query(
            'SELECT * FROM users WHERE username = ? AND password = ?',
            [username, password]
        );
        
        if (users.length === 0) {
            return res.status(401).json({ 
                success: false, 
                message: 'Invalid credentials' 
            });
        }
        
        const token = jwt.sign(
            { username: users[0].username },
            process.env.JWT_SECRET,
            { expiresIn: process.env.JWT_EXPIRE }
        );
        
        res.json({
            success: true,
            message: 'Login successful',
            token,
            user: { username: users[0].username }
        });
    } catch (error) {
        console.error('Login error:', error);
        res.status(500).json({ 
            success: false, 
            message: 'Login failed' 
        });
    }
});

// Register new user
app.post('/api/auth/register', async (req, res) => {
    const { username, password } = req.body;
    
    if (!username || !password) {
        return res.status(400).json({ 
            success: false, 
            message: 'Username and password required' 
        });
    }
    
    try {
        // Check if user exists
        const [existing] = await pool.query(
            'SELECT * FROM users WHERE username = ?',
            [username]
        );
        
        if (existing.length > 0) {
            return res.status(409).json({ 
                success: false, 
                message: 'Username already exists' 
            });
        }
        
        // Insert new user
        await pool.query(
            'INSERT INTO users (username, password) VALUES (?, ?)',
            [username, password]
        );
        
        res.status(201).json({
            success: true,
            message: 'User registered successfully'
        });
    } catch (error) {
        console.error('Registration error:', error);
        res.status(500).json({ 
            success: false, 
            message: 'Registration failed' 
        });
    }
});

// ========== PROPERTIES ENDPOINTS ==========
// Get all properties
app.get('/api/properties', async (req, res) => {
    try {
        const [rows] = await pool.query('SELECT * FROM property ORDER BY property_id');
        res.json({
            success: true,
            count: rows.length,
            data: rows
        });
    } catch (error) {
        console.error('Error fetching properties:', error);
        res.status(500).json({ 
            success: false, 
            message: 'Error fetching properties' 
        });
    }
});

// Get single property
app.get('/api/properties/:id', async (req, res) => {
    const { id } = req.params;
    try {
        const [rows] = await pool.query(
            'SELECT * FROM property WHERE property_id = ?',
            [id]
        );
        
        if (rows.length === 0) {
            return res.status(404).json({ 
                success: false, 
                message: 'Property not found' 
            });
        }
        
        res.json({
            success: true,
            data: rows[0]
        });
    } catch (error) {
        console.error('Error fetching property:', error);
        res.status(500).json({ 
            success: false, 
            message: 'Error fetching property' 
        });
    }
});

// Create property (requires authentication)
app.post('/api/properties', async (req, res) => {
    const { property_id, address, owner_id, owner_no, p_type, rooms } = req.body;
    
    // Check for auth token in headers
    const authHeader = req.headers['authorization'];
    if (!authHeader) {
        return res.status(401).json({ 
            success: false, 
            message: 'Authentication required' 
        });
    }
    
    if (!property_id || !address) {
        return res.status(400).json({ 
            success: false, 
            message: 'Property ID and Address are required' 
        });
    }
    
    try {
        const [result] = await pool.query(
            'INSERT INTO property (property_id, address, owner_id, owner_no, p_type, rooms) VALUES (?, ?, ?, ?, ?, ?)',
            [property_id, address, owner_id || null, owner_no || null, p_type || null, rooms || null]
        );
        
        res.status(201).json({
            success: true,
            message: 'Property created successfully',
            data: { property_id, address, owner_id, owner_no, p_type, rooms }
        });
    } catch (error) {
        if (error.code === 'ER_DUP_ENTRY') {
            return res.status(409).json({ 
                success: false, 
                message: 'Property ID already exists' 
            });
        }
        console.error('Error creating property:', error);
        res.status(500).json({ 
            success: false, 
            message: 'Error creating property' 
        });
    }
});

// Update property
app.put('/api/properties/:id', async (req, res) => {
    const { id } = req.params;
    const { address, owner_id, owner_no, p_type, rooms } = req.body;
    
    const authHeader = req.headers['authorization'];
    if (!authHeader) {
        return res.status(401).json({ 
            success: false, 
            message: 'Authentication required' 
        });
    }
    
    try {
        const [result] = await pool.query(
            `UPDATE property 
             SET address = COALESCE(?, address),
                 owner_id = COALESCE(?, owner_id),
                 owner_no = COALESCE(?, owner_no),
                 p_type = COALESCE(?, p_type),
                 rooms = COALESCE(?, rooms)
             WHERE property_id = ?`,
            [address, owner_id, owner_no, p_type, rooms, id]
        );
        
        if (result.affectedRows === 0) {
            return res.status(404).json({ 
                success: false, 
                message: 'Property not found' 
            });
        }
        
        res.json({
            success: true,
            message: 'Property updated successfully'
        });
    } catch (error) {
        console.error('Error updating property:', error);
        res.status(500).json({ 
            success: false, 
            message: 'Error updating property' 
        });
    }
});

// Delete property
app.delete('/api/properties/:id', async (req, res) => {
    const { id } = req.params;
    
    const authHeader = req.headers['authorization'];
    if (!authHeader) {
        return res.status(401).json({ 
            success: false, 
            message: 'Authentication required' 
        });
    }
    
    try {
        const [result] = await pool.query(
            'DELETE FROM property WHERE property_id = ?',
            [id]
        );
        
        if (result.affectedRows === 0) {
            return res.status(404).json({ 
                success: false, 
                message: 'Property not found' 
            });
        }
        
        res.json({
            success: true,
            message: 'Property deleted successfully'
        });
    } catch (error) {
        console.error('Error deleting property:', error);
        res.status(500).json({ 
            success: false, 
            message: 'Error deleting property' 
        });
    }
});

// ========== TENANTS ENDPOINTS ==========
app.get('/api/tenants', async (req, res) => {
    try {
        const [rows] = await pool.query('SELECT * FROM tenant ORDER BY tenant_id');
        res.json({
            success: true,
            count: rows.length,
            data: rows
        });
    } catch (error) {
        console.error('Error fetching tenants:', error);
        res.status(500).json({ 
            success: false, 
            message: 'Error fetching tenants' 
        });
    }
});

app.get('/api/tenants/:id', async (req, res) => {
    const { id } = req.params;
    try {
        const [rows] = await pool.query(
            'SELECT * FROM tenant WHERE tenant_id = ?',
            [id]
        );
        
        if (rows.length === 0) {
            return res.status(404).json({ 
                success: false, 
                message: 'Tenant not found' 
            });
        }
        
        res.json({
            success: true,
            data: rows[0]
        });
    } catch (error) {
        console.error('Error fetching tenant:', error);
        res.status(500).json({ 
            success: false, 
            message: 'Error fetching tenant' 
        });
    }
});

app.post('/api/tenants', async (req, res) => {
    const { tenant_id, tenant_no, owner_id, property_rent, start_date, end_date, tenant_name } = req.body;
    
    const authHeader = req.headers['authorization'];
    if (!authHeader) {
        return res.status(401).json({ 
            success: false, 
            message: 'Authentication required' 
        });
    }
    
    if (!tenant_id || !tenant_name) {
        return res.status(400).json({ 
            success: false, 
            message: 'Tenant ID and Name are required' 
        });
    }
    
    try {
        await pool.query(
            `INSERT INTO tenant (tenant_id, tenant_no, owner_id, property_rent, start_date, end_date, tenant_name) 
             VALUES (?, ?, ?, ?, ?, ?, ?)`,
            [tenant_id, tenant_no || null, owner_id || null, property_rent || null, start_date || null, end_date || null, tenant_name]
        );
        
        res.status(201).json({
            success: true,
            message: 'Tenant created successfully'
        });
    } catch (error) {
        if (error.code === 'ER_DUP_ENTRY') {
            return res.status(409).json({ 
                success: false, 
                message: 'Tenant ID already exists' 
            });
        }
        console.error('Error creating tenant:', error);
        res.status(500).json({ 
            success: false, 
            message: 'Error creating tenant' 
        });
    }
});

// ========== APPOINTMENTS ENDPOINTS ==========
app.get('/api/appointments', async (req, res) => {
    try {
        const [rows] = await pool.query('SELECT * FROM appointment ORDER BY date DESC');
        res.json({
            success: true,
            count: rows.length,
            data: rows
        });
    } catch (error) {
        console.error('Error fetching appointments:', error);
        res.status(500).json({ 
            success: false, 
            message: 'Error fetching appointments' 
        });
    }
});

app.post('/api/appointments', async (req, res) => {
    const { appointment_id, date, time, client_id, p_id } = req.body;
    
    const authHeader = req.headers['authorization'];
    if (!authHeader) {
        return res.status(401).json({ 
            success: false, 
            message: 'Authentication required' 
        });
    }
    
    if (!appointment_id || !client_id) {
        return res.status(400).json({ 
            success: false, 
            message: 'Appointment ID and Client ID are required' 
        });
    }
    
    try {
        await pool.query(
            'INSERT INTO appointment (appointment_id, date, time, client_id, p_id) VALUES (?, ?, ?, ?, ?)',
            [appointment_id, date || null, time || null, client_id, p_id || null]
        );
        
        res.status(201).json({
            success: true,
            message: 'Appointment created successfully'
        });
    } catch (error) {
        if (error.code === 'ER_DUP_ENTRY') {
            return res.status(409).json({ 
                success: false, 
                message: 'Appointment ID already exists' 
            });
        }
        console.error('Error creating appointment:', error);
        res.status(500).json({ 
            success: false, 
            message: 'Error creating appointment' 
        });
    }
});

// ========== COMMENTS ENDPOINTS ==========
app.get('/api/comments', async (req, res) => {
    try {
        const [rows] = await pool.query('SELECT * FROM comments ORDER BY date DESC');
        res.json({
            success: true,
            count: rows.length,
            data: rows
        });
    } catch (error) {
        console.error('Error fetching comments:', error);
        res.status(500).json({ 
            success: false, 
            message: 'Error fetching comments' 
        });
    }
});

// 404 handler for undefined routes
app.use((req, res) => {
    res.status(404).json({ 
        error: 'Endpoint not found',
        message: `The endpoint ${req.method} ${req.url} does not exist`,
        availableEndpoints: {
            users: 'GET /api/users',
            userById: 'GET /api/users/:username',
            properties: 'GET /api/properties',
            propertyById: 'GET /api/properties/:id',
            createProperty: 'POST /api/properties',
            updateProperty: 'PUT /api/properties/:id',
            deleteProperty: 'DELETE /api/properties/:id',
            tenants: 'GET /api/tenants',
            tenantById: 'GET /api/tenants/:id',
            createTenant: 'POST /api/tenants',
            appointments: 'GET /api/appointments',
            createAppointment: 'POST /api/appointments',
            comments: 'GET /api/comments',
            login: 'POST /api/auth/login',
            register: 'POST /api/auth/register'
        }
    });
});

// Start server
const startServer = async () => {
    const dbConnected = await testConnection();
    
    if (!dbConnected) {
        console.error('❌ Cannot start server - database connection failed');
        process.exit(1);
    }
    
    app.listen(PORT, () => {
        console.log(`🚀 Server running on http://localhost:${PORT}`);
        console.log(`\n📋 Available endpoints:`);
        console.log(`   GET  http://localhost:${PORT}/api/users`);
        console.log(`   GET  http://localhost:${PORT}/api/properties`);
        console.log(`   GET  http://localhost:${PORT}/api/tenants`);
        console.log(`   GET  http://localhost:${PORT}/api/appointments`);
        console.log(`   GET  http://localhost:${PORT}/api/comments`);
        console.log(`   POST http://localhost:${PORT}/api/auth/login`);
        console.log(`   POST http://localhost:${PORT}/api/auth/register\n`);
    });
};

startServer();
