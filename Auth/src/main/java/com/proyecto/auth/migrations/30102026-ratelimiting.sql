ALTER TABLE usuarios 
ADD COLUMN intentos_fallidos INT NOT NULL DEFAULT 0,
ADD COLUMN cuenta_bloqueada_hasta TIMESTAMP;