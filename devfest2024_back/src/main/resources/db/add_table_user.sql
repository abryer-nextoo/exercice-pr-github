CREATE TYPE HOUSE AS ENUM ('BACK','FRONT','CICD','MOBILE');

CREATE TABLE users (
    id UUID DEFAULT gen_random_uuid() UNIQUE,
    phone VARCHAR(14) UNIQUE NOT NULL ,
    email VARCHAR(100) NOT NULL,
    firstname VARCHAR(50) NOT NULL ,
    lastname VARCHAR(50) NOT NULL,
    house HOUSE NOT NULL,
    score INT NOT NULL
);