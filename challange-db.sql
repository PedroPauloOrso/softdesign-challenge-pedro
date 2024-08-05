
CREATE DATABASE softdesign_challenge;

use softdesign_challenge;

-- Tables
CREATE TABLE pauta (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(400) NOT NULL,
    total_run_time BIGINT NOT NULL,
    is_started BOOLEAN NOT NULL DEFAULT FALSE,
	start_date TIMESTAMP NULL,
    create_date TIMESTAMP NOT NULL,
    modification_date TIMESTAMP NOT NULL,
    CONSTRAINT tb_pauta_sequence UNIQUE (id)
);

CREATE TABLE associado (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(400) NOT NULL,
    cpf VARCHAR(20) NOT NULL UNIQUE,
    status VARCHAR(300) NOT NULL,
    create_date TIMESTAMP NOT NULL,
    modification_date TIMESTAMP NOT NULL,
    CONSTRAINT tb_associado_sequence UNIQUE (id)
);

CREATE TABLE voto (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    vote_option BOOLEAN NOT NULL DEFAULT FALSE,
    associado_id BIGINT NOT NULL, 
    pauta_id BIGINT NOT NULL,
    create_date TIMESTAMP NOT NULL,
    modification_date TIMESTAMP NOT NULL,
    CONSTRAINT tb_voto_sequence UNIQUE (id),
    FOREIGN KEY (associado_id) REFERENCES associado(id) ON DELETE CASCADE,
    FOREIGN KEY (pauta_id) REFERENCES pauta(id) ON DELETE CASCADE
);

-- Sequences
CREATE TABLE tb_pauta_sequence (
    next_val BIGINT NOT NULL
);
INSERT INTO tb_pauta_sequence (next_val) VALUES (1);

CREATE TABLE tb_associado_sequence (
    next_val BIGINT NOT NULL
);
INSERT INTO tb_associado_sequence (next_val) VALUES (1);

CREATE TABLE tb_voto_sequence (
    next_val BIGINT NOT NULL
);
INSERT INTO tb_voto_sequence (next_val) VALUES (1);

------------

select * from pauta;

select * from associado;

select * from voto;


insert into voto (associado,create_date,modification_date,option,pauta_id,id) values (?,?,?,?,?,?)


DROP DATABASE softdesign_challenge;
