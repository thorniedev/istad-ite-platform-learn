--liquibase formatted sql

--changeset developer:0001-insert-initial-orders
INSERT INTO orders (
    id,
    customer_id,
    business_id,
    price,
    order_status,
    created_at
) VALUES (
             'd290f1ee-6c54-4b01-90e6-d701748f0851',
             'c123e456-e89b-12d3-a456-426614174000',
             'b789e012-e89b-12d3-a456-426614174111',
             49.99,
             'PENDING',
             CURRENT_TIMESTAMP
         ),
         (
             'e391a2bb-7d65-4c12-91f7-e812859f1962',
             'c123e456-e89b-12d3-a456-426614174000',
             'b789e012-e89b-12d3-a456-426614174111',
             120.00,
             'PAID',
             CURRENT_TIMESTAMP
         )
    ON CONFLICT (id) DO NOTHING;

--rollback DELETE FROM orders WHERE id IN ('d290f1ee-6c54-4b01-90e6-d701748f0851', 'e391a2bb-7d65-4c12-91f7-e812859f1962');