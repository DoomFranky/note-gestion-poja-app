INSERT INTO "user" (id, ref, first_name, last_name, email, password, role, track, promotion_id) VALUES
('u1', NULL, 'Admin', 'HEI', 'admin@hei.school', '$2a$10$e8R6.V2Uj3R7qHnF/8Jd0O8yZ00M9yM3F4P4z2x9K8w2x1y0Z.O4G', 'ADMIN', NULL, NULL),
('t1', NULL, 'Alice', 'Randria', 'alice.randria@hei.school', '$2a$10$e8R6.V2Uj3R7qHnF/8Jd0O8yZ00M9yM3F4P4z2x9K8w2x1y0Z.O4G', 'TEACHER', NULL, NULL),
('t2', NULL, 'Bryan', 'Rakotomalala', 'bryan.rakotomalala@hei.school', '$2a$10$e8R6.V2Uj3R7qHnF/8Jd0O8yZ00M9yM3F4P4z2x9K8w2x1y0Z.O4G', 'TEACHER', NULL, NULL),
('t3', NULL, 'Cynthia', 'Nirina', 'cynthia.nirina@hei.school', '$2a$10$e8R6.V2Uj3R7qHnF/8Jd0O8yZ00M9yM3F4P4z2x9K8w2x1y0Z.O4G', 'TEACHER', NULL, NULL),
('t4', NULL, 'David', 'Feno', 'david.feno@hei.school', '$2a$10$e8R6.V2Uj3R7qHnF/8Jd0O8yZ00M9yM3F4P4z2x9K8w2x1y0Z.O4G', 'TEACHER', NULL, NULL),
('s1', 'STD24001', 'Jean', 'Rakoto', 'jean.rakoto@hei.school', '$2a$10$e8R6.V2Uj3R7qHnF/8Jd0O8yZ00M9yM3F4P4z2x9K8w2x1y0Z.O4G', 'STUDENT', 'EL', 'p2024'),
('s2', 'STD24002', 'Miora', 'Andrianina', 'miora.andrianina@hei.school', '$2a$10$e8R6.V2Uj3R7qHnF/8Jd0O8yZ00M9yM3F4P4z2x9K8w2x1y0Z.O4G', 'STUDENT', 'EL', 'p2024'),
('s3', 'STD24003', 'Nomena', 'Ratsimbazafy', 'nomena.ratsimbazafy@hei.school', '$2a$10$e8R6.V2Uj3R7qHnF/8Jd0O8yZ00M9yM3F4P4z2x9K8w2x1y0Z.O4G', 'STUDENT', 'EL', 'p2024'),
('s4', 'STD24004', 'Sahondra', 'Ravelo', 'sahondra.ravelo@hei.school', '$2a$10$e8R6.V2Uj3R7qHnF/8Jd0O8yZ00M9yM3F4P4z2x9K8w2x1y0Z.O4G', 'STUDENT', 'TN', 'p2024'),
('s5', 'STD24005', 'Toky', 'Herimanana', 'toky.herimanana@hei.school', '$2a$10$e8R6.V2Uj3R7qHnF/8Jd0O8yZ00M9yM3F4P4z2x9K8w2x1y0Z.O4G', 'STUDENT', 'TN', 'p2024'),
('s6', 'STD24006', 'Volana', 'Rabearison', 'volana.rabearison@hei.school', '$2a$10$e8R6.V2Uj3R7qHnF/8Jd0O8yZ00M9yM3F4P4z2x9K8w2x1y0Z.O4G', 'STUDENT', 'TN', 'p2024'),
('s7', 'STD26001', 'Lova', 'Andria', 'lova.andria@hei.school', '$2a$10$e8R6.V2Uj3R7qHnF/8Jd0O8yZ00M9yM3F4P4z2x9K8w2x1y0Z.O4G', 'STUDENT', 'EL', 'p2026'),
('s8', 'STD26002', 'Fanja', 'Rasolofoniaina', 'fanja.rasolofoniaina@hei.school', '$2a$10$e8R6.V2Uj3R7qHnF/8Jd0O8yZ00M9yM3F4P4z2x9K8w2x1y0Z.O4G', 'STUDENT', 'EL', 'p2026'),
('s9', 'STD26003', 'Hery', 'Rajaonarison', 'hery.rajaonarison@hei.school', '$2a$10$e8R6.V2Uj3R7qHnF/8Jd0O8yZ00M9yM3F4P4z2x9K8w2x1y0Z.O4G', 'STUDENT', 'TN', 'p2026'),
('s10', 'STD26004', 'Tiana', 'Rakotondrabe', 'tiana.rakotondrabe@hei.school', '$2a$10$e8R6.V2Uj3R7qHnF/8Jd0O8yZ00M9yM3F4P4z2x9K8w2x1y0Z.O4G', 'STUDENT', 'TN', 'p2026')
    ON CONFLICT (id) DO UPDATE SET password = EXCLUDED.password;