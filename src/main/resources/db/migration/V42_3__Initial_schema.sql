CREATE TYPE user_role AS ENUM ('STUDENT', 'TEACHER', 'ADMIN');

CREATE TABLE "user" (
                        id VARCHAR(50) PRIMARY KEY,
                        first_name VARCHAR(100) NOT NULL,
                        last_name VARCHAR(100) NOT NULL,
                        email VARCHAR(255) NOT NULL UNIQUE,
                        role user_role NOT NULL,
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
                        ref VARCHAR(20) NOT NULL UNIQUE,
                        title VARCHAR(100) NOT NULL,
                        credits INT NOT NULL DEFAULT 0
);

-- Table: Attribution teacher <-> course (N.N)
CREATE TABLE course_teacher (
                                course_id VARCHAR(50) NOT NULL REFERENCES course(id),
                                teacher_id VARCHAR(50) NOT NULL REFERENCES "user"(id),
                                PRIMARY KEY (course_id, teacher_id)
);

-- Table: Affectation course <-> group
CREATE TABLE course_group (
                              course_id VARCHAR(50) NOT NULL REFERENCES course(id),
                              group_id VARCHAR(50) NOT NULL REFERENCES "group"(id),
                              PRIMARY KEY (course_id, group_id)
);

-- Table: grade / grade of the student
CREATE TABLE grade (
                       id VARCHAR(50) PRIMARY KEY,
                       student_id VARCHAR(50) NOT NULL REFERENCES "user"(id),
                       course_id VARCHAR(50) NOT NULL REFERENCES course(id),
                       score DOUBLE PRECISION NOT NULL CHECK (score >= 0 AND score <= 20),
                       updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                       updated_by VARCHAR(50) NOT NULL REFERENCES "user"(id),
                       CONSTRAINT unique_student_course UNIQUE (student_id, course_id)
);

-- Table: history of grade
CREATE TABLE grade_history (
                               id VARCHAR(50) PRIMARY KEY,
                               grade_id VARCHAR(50) NOT NULL REFERENCES grade(id) ON DELETE CASCADE,
                               previous_score DOUBLE PRECISION NOT NULL,
                               new_score DOUBLE PRECISION NOT NULL,
                               reason TEXT NOT NULL,
                               modified_by VARCHAR(50) NOT NULL REFERENCES "user"(id),
                               modified_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Optimisation
CREATE INDEX idx_student_group_student ON student_group(student_id);
CREATE INDEX idx_grade_student ON grade(student_id);
CREATE INDEX idx_grade_course ON grade(course_id);
CREATE INDEX idx_grade_history_grade ON grade_history(grade_id);