
-- Tabela Clinics
CREATE TABLE clinic (
     id UUID PRIMARY KEY NOT NULL,
     name VARCHAR(255) NOT NULL,
     owner_id UUID,
     created_at TIMESTAMP NOT NULL,
     CONSTRAINT fk_owner FOREIGN KEY (owner_id) REFERENCES users(id) ON DELETE SET NULL
);