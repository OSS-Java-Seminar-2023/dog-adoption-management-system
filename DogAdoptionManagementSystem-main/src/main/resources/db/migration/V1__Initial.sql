CREATE TABLE "user" (
                        id UUID NOT NULL,
                        oib BIGINT,
                        role VARCHAR(20) NOT NULL,
                        email VARCHAR(40) NOT NULL,
                        password VARCHAR(200) NOT NULL,
                        phone_number VARCHAR(15) NOT NULL,
                        name VARCHAR(20) NOT NULL,
                        surname VARCHAR(20) NOT NULL,
                        gender VARCHAR(10) NOT NULL,
                        date_of_birth DATE,
                        city VARCHAR(20) NOT NULL,
                        address VARCHAR(30) NOT NULL,
                        PRIMARY KEY (id)
);

CREATE TABLE dog (
                     id UUID NOT NULL,
                     microchip BIGINT NOT NULL,
                     name VARCHAR(20) NOT NULL,
                     gender VARCHAR(10) NOT NULL,
                     date_of_birth DATE NOT NULL,
                     breed VARCHAR(30) NOT NULL,
                     size VARCHAR(15) NOT NULL,
                     sterilised VARCHAR(5) NOT NULL,
                     castrated VARCHAR(5) NOT NULL,
                     note VARCHAR(200),
                     adoption_status VARCHAR(20) NOT NULL,
                     image VARCHAR(120) NOT NULL,
                     PRIMARY KEY (id)
);

CREATE TABLE vaccine (
                         vaccine_name VARCHAR(25) NOT NULL,
                         PRIMARY KEY (vaccine_name)
);

CREATE TABLE dog_vaccine (
                             id UUID NOT NULL,
                             dog_id UUID NOT NULL,
                             vaccine_name VARCHAR(25) NOT NULL,
                             PRIMARY KEY (id),
                             FOREIGN KEY (dog_id) REFERENCES dog(id),
                             FOREIGN KEY (vaccine_name) REFERENCES vaccine(vaccine_name)
);

CREATE TABLE volunteer (
                           id UUID NOT NULL,
                           user_id UUID NOT NULL,
                           dog_id UUID NULL,
                           job VARCHAR(30) NOT NULL,
                           start_time timestamp NOT NULL,
                           stop_time timestamp NOT NULL,
                           PRIMARY KEY (id),
                           FOREIGN KEY (user_id) REFERENCES "user"(id),
                           FOREIGN KEY (dog_id) REFERENCES dog(id)
);

CREATE TABLE waiting_list (
                              id UUID NOT NULL,
                              user_id UUID NOT NULL,
                              dog_id UUID NOT NULL,
                              date_of_application DATE NOT NULL,
                              PRIMARY KEY (id),
                              FOREIGN KEY (user_id) REFERENCES "user"(id),
                              FOREIGN KEY (dog_id) REFERENCES dog(id)
);

CREATE TABLE contract (
                          id UUID NOT NULL,
                          user_id UUID NOT NULL,
                          dog_id UUID NULL,
                          date_of_contract DATE NOT NULL,
                          status VARCHAR(20) NOT NULL,
                          PRIMARY KEY (id),
                          FOREIGN KEY (user_id) REFERENCES "user"(id),
                          FOREIGN KEY (dog_id) REFERENCES dog(id)
);