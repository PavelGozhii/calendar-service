CREATE TABLE event
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    title           VARCHAR(50) NOT NULL,
    description     TEXT,
    start_date_time DATETIME    NOT NULL,
    end_date_time   DATETIME    NOT NULL,
    location        VARCHAR(100)
);
