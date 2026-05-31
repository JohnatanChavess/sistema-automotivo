-- 1. Criação das Tabelas
CREATE TABLE IF NOT EXISTS marca (
    id            BIGINT       NOT NULL AUTO_INCREMENT,
    nome          VARCHAR(100) NOT NULL,
    pais_origem   VARCHAR(50),
    PRIMARY KEY (id),
    CONSTRAINT uq_marca_nome UNIQUE (nome)
);

CREATE TABLE IF NOT EXISTS modelo (
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    nome       VARCHAR(100) NOT NULL,
    categoria  VARCHAR(50),
    marca_id   BIGINT       NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_modelo_marca FOREIGN KEY (marca_id)
        REFERENCES marca (id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS veiculo (
    id               BIGINT         NOT NULL AUTO_INCREMENT,
    ano_fabricacao   INT            NOT NULL,
    cor              VARCHAR(50)    NOT NULL,
    preco            DECIMAL(12, 2) NOT NULL,
    quilometragem    INT            NOT NULL DEFAULT 0,
    status           ENUM('DISPONIVEL', 'VENDIDO', 'RESERVADO') NOT NULL DEFAULT 'DISPONIVEL',
    observacoes      VARCHAR(500),
    data_cadastro    DATE           NOT NULL,
    modelo_id        BIGINT         NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_veiculo_modelo FOREIGN KEY (modelo_id)
        REFERENCES modelo (id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    CHECK (ano_fabricacao >= 1990),
    CHECK (preco > 0),
    CHECK (quilometragem >= 0)
);

-- 2. Índices para performance (Requisito RNF01)
CREATE INDEX idx_veiculo_status  ON veiculo (status);
CREATE INDEX idx_veiculo_ano     ON veiculo (ano_fabricacao);
CREATE INDEX idx_veiculo_preco   ON veiculo (preco);

-- 3. Carga Inicial de Dados
INSERT INTO marca (nome, pais_origem) VALUES
    ('Toyota', 'Japão'), ('Ford', 'EUA'), ('Honda', 'Japão');

INSERT INTO modelo (nome, categoria, marca_id) VALUES
    ('Corolla', 'Sedan', 1), ('Civic', 'Sedan', 3), ('Ranger', 'Pickup', 2);

INSERT INTO veiculo (ano_fabricacao, cor, preco, quilometragem, status, data_cadastro, modelo_id) VALUES
    (2022, 'Prata', 120000.00, 15000, 'DISPONIVEL', CURDATE(), 1),
    (2023, 'Preto', 150000.00, 5000, 'DISPONIVEL', CURDATE(), 2);