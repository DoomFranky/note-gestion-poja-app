CREATE TABLE promotion (
    id VARCHAR(50) PRIMARY KEY,
    label VARCHAR(50) NOT NULL UNIQUE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE "user" (
    id VARCHAR(50) PRIMARY KEY,
    ref VARCHAR(20) UNIQUE,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    role VARCHAR(20) NOT NULL,
    track VARCHAR(10),
    promotion_id VARCHAR(50) REFERENCES promotion(id),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE "group" (
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    academic_year VARCHAR(20) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE student_group (
    id VARCHAR(50) PRIMARY KEY,
    student_id VARCHAR(50) NOT NULL REFERENCES "user"(id),
    group_id VARCHAR(50) NOT NULL REFERENCES "group"(id),
    joined_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    left_at TIMESTAMP WITH TIME ZONE
);

CREATE TABLE course (
    id VARCHAR(50) PRIMARY KEY,
    code VARCHAR(20) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    credits INT NOT NULL DEFAULT 0,
    track VARCHAR(10),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE course_teacher (
    id VARCHAR(50) PRIMARY KEY,
    course_id VARCHAR(50) NOT NULL REFERENCES course(id),
    teacher_id VARCHAR(50) NOT NULL REFERENCES "user"(id),
    CONSTRAINT unique_course_teacher UNIQUE (course_id, teacher_id)
);

CREATE TABLE course_group (
    id VARCHAR(50) PRIMARY KEY,
    course_id VARCHAR(50) NOT NULL REFERENCES course(id),
    group_id VARCHAR(50) NOT NULL REFERENCES "group"(id),
    CONSTRAINT unique_course_group UNIQUE (course_id, group_id)
);

CREATE TABLE exam (
    id VARCHAR(50) PRIMARY KEY,
    course_id VARCHAR(50) NOT NULL REFERENCES course(id),
    academic_year INT NOT NULL,
    label VARCHAR(100) NOT NULL,
    exam_datetime TIMESTAMP WITH TIME ZONE NOT NULL,
    coefficient_num INT NOT NULL,
    coefficient_den INT NOT NULL,
    CONSTRAINT check_coefficient_den CHECK (coefficient_den > 0)
);

CREATE TABLE exam_grade (
    id VARCHAR(50) PRIMARY KEY,
    exam_id VARCHAR(50) NOT NULL REFERENCES exam(id),
    student_id VARCHAR(50) NOT NULL REFERENCES "user"(id),
    score DOUBLE PRECISION NOT NULL CHECK (score >= 0 AND score <= 20),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(50) NOT NULL REFERENCES "user"(id),
    CONSTRAINT unique_exam_student UNIQUE (exam_id, student_id)
);

CREATE TABLE grade (
    id VARCHAR(50) PRIMARY KEY,
    student_id VARCHAR(50) NOT NULL REFERENCES "user"(id),
    course_id VARCHAR(50) NOT NULL REFERENCES course(id),
    academic_year INT NOT NULL,
    score DOUBLE PRECISION NOT NULL CHECK (score >= 0 AND score <= 20),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(50) NOT NULL REFERENCES "user"(id),
    CONSTRAINT unique_student_course_year UNIQUE (student_id, course_id, academic_year)
);

CREATE TABLE grade_history (
    id VARCHAR(50) PRIMARY KEY,
    grade_id VARCHAR(50) NOT NULL REFERENCES grade(id) ON DELETE CASCADE,
    previous_score DOUBLE PRECISION NOT NULL,
    new_score DOUBLE PRECISION NOT NULL,
    reason TEXT NOT NULL,
    modified_by VARCHAR(50) NOT NULL REFERENCES "user"(id),
    modified_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_student_group_student ON student_group(student_id);
CREATE INDEX idx_grade_student ON grade(student_id);
CREATE INDEX idx_grade_course ON grade(course_id);
CREATE INDEX idx_grade_history_grade ON grade_history(grade_id);
CREATE INDEX idx_exam_course ON exam(course_id);
CREATE INDEX idx_exam_grade_exam ON exam_grade(exam_id);
CREATE INDEX idx_exam_grade_student ON exam_grade(student_id);
