CREATE TABLE places (
    id BIGSERIAL PRIMARY KEY,
    label VARCHAR(255) NOT NULL,
    location VARCHAR(255)
);

-- Create business_hours table
CREATE TABLE opening_hours (
    id BIGSERIAL PRIMARY KEY,
    day_of_week INT NOT NULL CHECK (day_of_week BETWEEN 1 AND 7),
    start_time TIME,
    end_time TIME,
    place_id BIGINT NOT NULL,

    FOREIGN KEY (place_id) REFERENCES places(id) ON DELETE CASCADE
);