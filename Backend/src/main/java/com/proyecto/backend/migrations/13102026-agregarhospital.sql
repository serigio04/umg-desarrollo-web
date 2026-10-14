-- 2. Registrar el submódulo "Agregar Hospital"
INSERT INTO submodulos (id, modulo_id, nombre, ruta, icono)
VALUES (32, 3, 'Agregar Hospital', '/hospitales/agregar', '➕')
ON CONFLICT (id) DO NOTHING;

-- 3. Asignar el nuevo submódulo al rol administrador (ejemplo: rol_id = 1) en modulos_roles
INSERT INTO modulos_roles (rol_id, submodulo_id)
VALUES (1, 32)
ON CONFLICT DO NOTHING;