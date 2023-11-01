CREATE TYPE adoption_status_enum AS ENUM ('Adopted', 'Not adopted');
CREATE TYPE role_enum AS ENUM ('User', 'Admin', 'Employee');
CREATE TYPE jobs_enum AS ENUM ('Walking', 'Cleaning', 'Temporary adoption', 'Feeding');
CREATE TYPE status_enum AS ENUM ('First interview', 'Completed', 'Confirmed', 'Denied');

CREATE TABLE Users (
    ID UNIQUEIDENTIFIER NOT NULL,
    OIB BIGINT NOT NULL,
    Role role_enum,
    Username VARCHAR(20) NOT NULL,
    Email NCHAR(40) NOT NULL,
    Password NCHAR(20) NOT NULL,
    Phone_Number VARCHAR(15) NOT NULL,
    Name VARCHAR(20) NOT NULL,
    Surname VARCHAR(20) NOT NULL,
    Gender VARCHAR(10) NOT NULL,
    Date_Of_Birth DATE NOT NULL,
    City VARCHAR(20) NOT NULL,
    Address VARCHAR(30) NOT NULL,
    PRIMARY KEY (ID)
);

CREATE TABLE Dogs (
    ID UNIQUEIDENTIFIER NOT NULL,
    Microchip BIGINT NOT NULL,
    Name VARCHAR(20) NOT NULL,
    Gender VARCHAR(10) NOT NULL,
    Date_Of_Birth DATE NOT NULL,
    Breed VARCHAR(20) NOT NULL,
    Size INT NOT NULL,
    Sterilised VARCHAR(4) NOT NULL,
    Castrated VARCHAR(4) NOT NULL,
    Note VARCHAR(50),
    Adoption_Status adoption_status_enum,
    Image VARCHAR(120) NOT NULL,
    PRIMARY KEY (ID)
);

CREATE TABLE Vaccines (
    Vaccine_Name VARCHAR(25) NOT NULL,
    PRIMARY KEY (Vaccine_Name)
);

CREATE TABLE Dog_Vaccine (
    Dog_ID UNIQUEIDENTIFIER NOT NULL,
    Vaccine_Name VARCHAR(25) NOT NULL,
    PRIMARY KEY (Vaccine_Name, Dog_ID),
    FOREIGN KEY (Dog_ID) REFERENCES Dogs(ID),
    FOREIGN KEY (Vaccine_Name) REFERENCES Vaccines(Vaccine_Name)
);

CREATE TABLE Volunteers (
    ID UNIQUEIDENTIFIER NOT NULL,
    User_ID UNIQUEIDENTIFIER NOT NULL,
    Dog_ID UNIQUEIDENTIFIER NOT NULL,
    Job jobs_enum,
    Start_Time DATETIME NOT NULL,
    Stop_Time DATETIME NOT NULL,
    PRIMARY KEY (ID),
    FOREIGN KEY (User_ID) REFERENCES Users(ID),
    FOREIGN KEY (Dog_ID) REFERENCES Dogs(ID)
);

CREATE TABLE Waiting_List (
    User_ID UNIQUEIDENTIFIER NOT NULL,
    Dog_ID UNIQUEIDENTIFIER NOT NULL,
    Date_Of_Application DATE NOT NULL,
    PRIMARY KEY (User_ID, Dog_ID),
    FOREIGN KEY (User_ID) REFERENCES Users(ID),
    FOREIGN KEY (Dog_ID) REFERENCES Dogs(ID)
);

CREATE TABLE Contract (
    ID UNIQUEIDENTIFIER NOT NULL,
    User_ID UNIQUEIDENTIFIER NOT NULL,
    Dog_ID UNIQUEIDENTIFIER NULL,
    Date_Of_Contract DATE NOT NULL,
    Status status_enum,
    PRIMARY KEY (ID),
    FOREIGN KEY (User_ID) REFERENCES Users(ID),
    FOREIGN KEY (Dog_ID) REFERENCES Dogs(ID)
);
