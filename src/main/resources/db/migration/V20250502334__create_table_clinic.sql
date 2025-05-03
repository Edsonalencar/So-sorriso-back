
-- Tabela Clinics
CREATE TABLE clinics (
     id UUID PRIMARY KEY NOT NULL,
     owner_id UUID,
     created_at TIMESTAMP NOT NULL,
     CONSTRAINT fk_owner FOREIGN KEY (owner_id) REFERENCES users(id) ON DELETE SET NULL
);