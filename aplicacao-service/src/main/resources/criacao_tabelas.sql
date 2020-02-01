CREATE TABLE IF NOT EXISTS categoria_material( 
   id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
   nome VARCHAR(255) NOT NULL) ENGINE = InnoDB;
   
   CREATE TABLE IF NOT EXISTS cidade( 
   id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
   nome VARCHAR(255) NOT NULL) ENGINE = InnoDB;
  
  
  CREATE TABLE IF NOT EXISTS estado( 
   id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
   nome VARCHAR(255) NOT NULL) ENGINE = InnoDB;
   
   CREATE TABLE IF NOT EXISTS material( 
   id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
   nome VARCHAR(255) NOT NULL) ENGINE = InnoDB;
   
     
   CREATE TABLE IF NOT EXISTS empresa( 
   id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
   nome VARCHAR(255) NOT NULL,
   endereco VARCHAR(255) NOT NULL,
   bairro VARCHAR(255) NOT NULL,
   telefone VARCHAR(255) NOT NULL,
   cep VARCHAR(255) NOT NULL,
   tipo_empresa VARCHAR(255) NOT NULL,
   id_cidade INT,
   id_estado INT,
   CONSTRAINT tb_fk_id_cidade FOREIGN KEY (id_cidade) REFERENCES cidade(id),
   CONSTRAINT tb_fk_id_estado FOREIGN KEY (id_estado) REFERENCES estado(id)
   ) ENGINE = InnoDB;
   
   CREATE TABLE empresa_material (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    material_id INTEGER NOT NULL,
    empresa_id INTEGER NOT NULL,
    FOREIGN KEY (material_id) REFERENCES material(id) ON DELETE CASCADE,
    FOREIGN KEY (empresa_id) REFERENCES empresa (id) ON DELETE CASCADE
) ENGINE = InnoDB;
   
   CREATE TABLE empresa_categoria_material (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    categoria_material_id INTEGER NOT NULL,
    empresa_id INTEGER NOT NULL,
    FOREIGN KEY (categoria_material_id) REFERENCES categoria_material (id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (empresa_id) REFERENCES empresa (id) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB;
   