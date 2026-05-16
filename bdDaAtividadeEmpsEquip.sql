CREATE DATABASE controle_laboratorio;
USE controle_laboratorio;


CREATE TABLE aluno (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    matricula VARCHAR(20) NOT NULL UNIQUE
);


CREATE TABLE equipamento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(100) NOT NULL,
    disponivel BOOLEAN NOT NULL DEFAULT TRUE
);


CREATE TABLE emprestimo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_aluno INT NOT NULL,
    id_equipamento INT NOT NULL,
    data_emprestimo DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_devolucao DATETIME,
    status VARCHAR(20) NOT NULL DEFAULT 'ATIVO',
    
    -- Chaves Estrangeiras (Garantindo o relacionamento)
    FOREIGN KEY (id_aluno) REFERENCES aluno(id),
    FOREIGN KEY (id_equipamento) REFERENCES equipamento(id)
);

INSERT INTO aluno (nome, matricula) VALUES
('Carlos Mendes', '20261001'),
('Ana Julia', '20261002');

INSERT INTO equipamento (descricao, disponivel) VALUES
('Notebook HP G8 16GB RAM', TRUE),
('Projetor DataShow', TRUE),
('Alicate de Crimpagem', FALSE); 

INSERT INTO emprestimo (id_aluno, id_equipamento, data_emprestimo, status) 
VALUES (1, 3, NOW(), 'ATIVO');