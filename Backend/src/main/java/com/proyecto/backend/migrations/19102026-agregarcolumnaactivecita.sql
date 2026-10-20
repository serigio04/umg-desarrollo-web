ALTER TABLE citas ADD COLUMN IF NOT EXISTS activo BOOLEAN DEFAULT true;

-- Asegurar que cualquier registro previo tenga el valor en true
UPDATE citas SET activo = true WHERE activo IS NULL;