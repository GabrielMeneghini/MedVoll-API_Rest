RENAME TABLE paciente TO pacientes;

ALTER TABLE pacientes ADD email varchar(100) NOT NULL UNIQUE;