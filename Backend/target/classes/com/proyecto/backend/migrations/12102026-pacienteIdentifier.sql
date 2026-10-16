ALTER TABLE pacientes 
ADD COLUMN identificacion VARCHAR(50) NOT NULL DEFAULT '',
ADD COLUMN fecha_nacimiento DATE,
ADD COLUMN direccion VARCHAR(255);