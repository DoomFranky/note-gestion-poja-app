ALTER TABLE exam ADD COLUMN type VARCHAR(20) NOT NULL DEFAULT 'CONTINUOUS';
UPDATE exam SET type = 'FINAL' WHERE label = 'Examen final';

CREATE TABLE exam_grade_history (
    id VARCHAR(50) PRIMARY KEY,
    exam_grade_id VARCHAR(50) NOT NULL REFERENCES exam_grade(id) ON DELETE CASCADE,
    previous_score DOUBLE PRECISION NOT NULL,
    new_score DOUBLE PRECISION NOT NULL,
    reason TEXT NOT NULL,
    modified_by VARCHAR(50) NOT NULL REFERENCES "user"(id),
    modified_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_exam_grade_history_exam_grade ON exam_grade_history(exam_grade_id);