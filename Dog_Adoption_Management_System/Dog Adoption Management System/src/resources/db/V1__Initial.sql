

CREATE TABLE Users (
                       id VARCHAR(36)  NOT NULL,
                       oib BIGINT NOT NULL,
                       role VARCHAR(20) NOT NULL,
                       username VARCHAR(20) NOT NULL,
                       email NCHAR(40) NOT NULL,
                       password NCHAR(20) NOT NULL,
                       phone_number VARCHAR(15) NOT NULL,
                       name VARCHAR(20) NOT NULL,
                       surname VARCHAR(20) NOT NULL,
                       gender VARCHAR(10) NOT NULL,
                       date_of_birth DATE NOT NULL,
                       city VARCHAR(20) NOT NULL,
                       address VARCHAR(30) NOT NULL,
                       PRIMARY KEY (id)
);

CREATE TABLE Dogs (
                      id VARCHAR(36) NOT NULL,
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

CREATE TABLE Vaccines (
                      vaccine_name VARCHAR(25) NOT NULL,
                      PRIMARY KEY (vaccine_name)
);

CREATE TABLE Dog_Vaccine (
                             dog_id VARCHAR(36) NOT NULL,
                             vaccine_name VARCHAR(25) NOT NULL,
                             PRIMARY KEY (vaccine_name, dog_id),
                             FOREIGN KEY (dog_id) REFERENCES Dogs(id),
                             FOREIGN KEY (vaccine_name) REFERENCES Vaccines(vaccine_name)
);

CREATE TABLE Volunteers (
                            id VARCHAR(36) NOT NULL,
                            user_id VARCHAR(36) NOT NULL,
                            dog_id VARCHAR(36) NOT NULL,
                            job VARCHAR(30) NOT NULL,
                            start_time DATETIME NOT NULL,
                            stop_time DATETIME NOT NULL,
                            PRIMARY KEY (id),
                            FOREIGN KEY (user_id) REFERENCES Users(id),
                            FOREIGN KEY (dog_id) REFERENCES Dogs(id)
);

CREATE TABLE Waiting_List (
                              id VARCHAR(36) NOT NULL,
                              user_id VARCHAR(36) NOT NULL,
                              dog_id VARCHAR(36) NOT NULL,
                              date_of_application DATE NOT NULL,
                              PRIMARY KEY (id),
                              FOREIGN KEY (user_id) REFERENCES Users(id),
                              FOREIGN KEY (dog_id) REFERENCES Dogs(id)
);

CREATE TABLE Contract (
                          id VARCHAR(36) NOT NULL,
                          user_id VARCHAR(36) NOT NULL,
                          dog_id VARCHAR(36) NULL,
                          date_of_contract DATE NOT NULL,
                          status VARCHAR(20) NOT NULL,
                          PRIMARY KEY (id),
                          FOREIGN KEY (user_id) REFERENCES Users(id),
                          FOREIGN KEY (dog_id) REFERENCES Dogs(id)
);