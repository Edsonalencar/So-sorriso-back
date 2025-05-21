-- Tabela de pacientes
CREATE TABLE patients (
    id UUID PRIMARY KEY,
    profile_id UUID UNIQUE,
    clinic_id UUID,
    create_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_profile FOREIGN KEY (profile_id) REFERENCES profiles(id) ON DELETE SET NULL,
    CONSTRAINT fk_clinic FOREIGN KEY (clinic_id) REFERENCES clinic(id) ON DELETE SET NULL
);
