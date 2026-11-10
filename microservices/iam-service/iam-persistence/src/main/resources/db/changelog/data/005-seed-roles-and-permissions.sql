-- liquibase formatted sql
-- changeset istad:005-seed-roles-and-permissions

-- 1. Seed Permissions
INSERT INTO permissions (id, uuid, name, resource, action, description, is_system, is_active, is_deleted, created_at, created_by, updated_at, updated_by, version)
VALUES
    (1, '5d1c2f0e-2b8a-4c3e-9f61-7e2d9a0b4c01', 'user:create', 'user', 'create', 'Create users', TRUE, TRUE, FALSE, NOW(), 'SYSTEM', NOW(), 'SYSTEM', 0),
    (2, '5d1c2f0e-2b8a-4c3e-9f61-7e2d9a0b4c02', 'user:read', 'user', 'read', 'Read users', TRUE, TRUE, FALSE, NOW(), 'SYSTEM', NOW(), 'SYSTEM', 0),
    (3, '5d1c2f0e-2b8a-4c3e-9f61-7e2d9a0b4c03', 'user:update', 'user', 'update', 'Update users', TRUE, TRUE, FALSE, NOW(), 'SYSTEM', NOW(), 'SYSTEM', 0),
    (4, '5d1c2f0e-2b8a-4c3e-9f61-7e2d9a0b4c04', 'user:delete', 'user', 'delete', 'Delete users', TRUE, TRUE, FALSE, NOW(), 'SYSTEM', NOW(), 'SYSTEM', 0),
    (5, '5d1c2f0e-2b8a-4c3e-9f61-7e2d9a0b4c05', 'user:assign', 'user', 'assign', 'Assign roles and permissions', TRUE, TRUE, FALSE, NOW(), 'SYSTEM', NOW(), 'SYSTEM', 0),
    (6, '5d1c2f0e-2b8a-4c3e-9f61-7e2d9a0b4c06', 'role:create', 'role', 'create', 'Create roles', TRUE, TRUE, FALSE, NOW(), 'SYSTEM', NOW(), 'SYSTEM', 0),
    (7, '5d1c2f0e-2b8a-4c3e-9f61-7e2d9a0b4c07', 'role:read', 'role', 'read', 'Read roles', TRUE, TRUE, FALSE, NOW(), 'SYSTEM', NOW(), 'SYSTEM', 0),
    (8, '5d1c2f0e-2b8a-4c3e-9f61-7e2d9a0b4c08', 'role:update', 'role', 'update', 'Update roles', TRUE, TRUE, FALSE, NOW(), 'SYSTEM', NOW(), 'SYSTEM', 0),
    (9, '5d1c2f0e-2b8a-4c3e-9f61-7e2d9a0b4c09', 'role:delete', 'role', 'delete', 'Delete roles', TRUE, TRUE, FALSE, NOW(), 'SYSTEM', NOW(), 'SYSTEM', 0),
    (10, '5d1c2f0e-2b8a-4c3e-9f61-7e2d9a0b4c10', 'role:assign', 'role', 'assign', 'Assign permissions to roles', TRUE, TRUE, FALSE, NOW(), 'SYSTEM', NOW(), 'SYSTEM', 0),
    (11, '5d1c2f0e-2b8a-4c3e-9f61-7e2d9a0b4c11', 'permission:create', 'permission', 'create', 'Create permissions', TRUE, TRUE, FALSE, NOW(), 'SYSTEM', NOW(), 'SYSTEM', 0),
    (12, '5d1c2f0e-2b8a-4c3e-9f61-7e2d9a0b4c12', 'permission:read', 'permission', 'read', 'Read permissions', TRUE, TRUE, FALSE, NOW(), 'SYSTEM', NOW(), 'SYSTEM', 0),
    (13, '5d1c2f0e-2b8a-4c3e-9f61-7e2d9a0b4c13', 'permission:update', 'permission', 'update', 'Update permissions', TRUE, TRUE, FALSE, NOW(), 'SYSTEM', NOW(), 'SYSTEM', 0),
    (14, '5d1c2f0e-2b8a-4c3e-9f61-7e2d9a0b4c14', 'permission:delete', 'permission', 'delete', 'Delete permissions', TRUE, TRUE, FALSE, NOW(), 'SYSTEM', NOW(), 'SYSTEM', 0)
    ON CONFLICT (id) DO NOTHING;

-- 2. Seed Roles
INSERT INTO roles (id, uuid, name, description, is_system, is_active, is_deleted, created_at, created_by, updated_at, updated_by, version)
VALUES
    (1, '0b9e6c1a-6a57-4a8e-9e0b-3a0f4d6f2c01', 'ADMIN', 'System Administrator with full access', TRUE, TRUE, FALSE, NOW(), 'SYSTEM', NOW(), 'SYSTEM', 0),
    (2, '0b9e6c1a-6a57-4a8e-9e0b-3a0f4d6f2c11', 'USER', 'Standard registered user', TRUE, TRUE, FALSE, NOW(), 'SYSTEM', NOW(), 'SYSTEM', 0)
    ON CONFLICT (id) DO NOTHING;

-- 3. Seed Role Permissions
-- ADMIN gets all 14 permissions
INSERT INTO role_permissions (id, role_id, permission_id, assigned_at, assigned_by)
VALUES
    (1, 1, 1, NOW(), 'SYSTEM'),
    (2, 1, 2, NOW(), 'SYSTEM'),
    (3, 1, 3, NOW(), 'SYSTEM'),
    (4, 1, 4, NOW(), 'SYSTEM'),
    (5, 1, 5, NOW(), 'SYSTEM'),
    (6, 1, 6, NOW(), 'SYSTEM'),
    (7, 1, 7, NOW(), 'SYSTEM'),
    (8, 1, 8, NOW(), 'SYSTEM'),
    (9, 1, 9, NOW(), 'SYSTEM'),
    (10, 1, 10, NOW(), 'SYSTEM'),
    (11, 1, 11, NOW(), 'SYSTEM'),
    (12, 1, 12, NOW(), 'SYSTEM'),
    (13, 1, 13, NOW(), 'SYSTEM'),
    (14, 1, 14, NOW(), 'SYSTEM')
    ON CONFLICT (id) DO NOTHING;

-- USER gets user:read and user:update
INSERT INTO role_permissions (id, role_id, permission_id, assigned_at, assigned_by)
VALUES
    (15, 2, 2, NOW(), 'SYSTEM'),
    (16, 2, 3, NOW(), 'SYSTEM')
    ON CONFLICT (id) DO NOTHING;

-- 4. Seed Default Admin User (username: admin, password: {bcrypt}Str0ng!Pass2026)
INSERT INTO users (
    id, uuid, username, email, email_verified, password_hash,
    given_name, family_name, locale,
    enabled, account_non_expired, account_non_locked, credentials_non_expired,
    created_at, created_by, updated_at, updated_by, version
) VALUES (
             1,
             '7f3d2a10-91c4-4b0e-8d6f-2a9c5e1b7d01',
             'admin',
             'admin@istad.co',
             TRUE,
             '$2a$10$w8.gZ0N7P2j6OeVJ2wMskOW8L6w1C/o9W1gD8SgW0q.sM7fOeVJ2m', -- Str0ng!Pass2026
             'System',
             'Administrator',
             'en',
             TRUE, TRUE, TRUE, TRUE,
             NOW(), 'SYSTEM', NOW(), 'SYSTEM', 0
         ) ON CONFLICT (id) DO NOTHING;

-- Assign ADMIN role to default admin user
INSERT INTO user_roles (id, user_id, role_id, assigned_at, assigned_by)
VALUES (1, 1, 1, NOW(), 'SYSTEM')
    ON CONFLICT (id) DO NOTHING;