INSERT INTO promotion (id, label) VALUES
('p2024', '2024'),
('p2026', '2026');

INSERT INTO "user" (id, ref, first_name, last_name, email, role, track, promotion_id) VALUES
('u1', NULL, 'Admin', 'HEI', 'admin@hei.school', 'ADMIN', NULL, NULL),
('t1', NULL, 'Alice', 'Randria', 'alice.randria@hei.school', 'TEACHER', NULL, NULL),
('t2', NULL, 'Bryan', 'Rakotomalala', 'bryan.rakotomalala@hei.school', 'TEACHER', NULL, NULL),
('t3', NULL, 'Cynthia', 'Nirina', 'cynthia.nirina@hei.school', 'TEACHER', NULL, NULL),
('t4', NULL, 'David', 'Feno', 'david.feno@hei.school', 'TEACHER', NULL, NULL),
('s1', 'STD24001', 'Jean', 'Rakoto', 'jean.rakoto@hei.school', 'STUDENT', 'EL', 'p2024'),
('s2', 'STD24002', 'Miora', 'Andrianina', 'miora.andrianina@hei.school', 'STUDENT', 'EL', 'p2024'),
('s3', 'STD24003', 'Nomena', 'Ratsimbazafy', 'nomena.ratsimbazafy@hei.school', 'STUDENT', 'EL', 'p2024'),
('s4', 'STD24004', 'Sahondra', 'Ravelo', 'sahondra.ravelo@hei.school', 'STUDENT', 'TN', 'p2024'),
('s5', 'STD24005', 'Toky', 'Herimanana', 'toky.herimanana@hei.school', 'STUDENT', 'TN', 'p2024'),
('s6', 'STD24006', 'Volana', 'Rabearison', 'volana.rabearison@hei.school', 'STUDENT', 'TN', 'p2024'),
('s7', 'STD26001', 'Lova', 'Andria', 'lova.andria@hei.school', 'STUDENT', 'EL', 'p2026'),
('s8', 'STD26002', 'Fanja', 'Rasolofoniaina', 'fanja.rasolofoniaina@hei.school', 'STUDENT', 'EL', 'p2026'),
('s9', 'STD26003', 'Hery', 'Rajaonarison', 'hery.rajaonarison@hei.school', 'STUDENT', 'TN', 'p2026'),
('s10', 'STD26004', 'Tiana', 'Rakotondrabe', 'tiana.rakotondrabe@hei.school', 'STUDENT', 'TN', 'p2026');

INSERT INTO "group" (id, name, academic_year) VALUES
('k1', 'K1', '2024-2025'),
('k2', 'K2', '2024-2025'),
('k3', 'K3', '2024-2025'),
('k4', 'K4', '2024-2025'),
('k5', 'K5', '2025-2026'),
('k6', 'K6', '2025-2026'),
('k7', 'K7', '2025-2026'),
('k8', 'K8', '2025-2026'),
('k9', 'K9', '2026-2027'),
('k10', 'K10', '2026-2027'),
('k11', 'K11', '2026-2027'),
('k12', 'K12', '2026-2027');

INSERT INTO course (id, code, name, credits, track) VALUES
('c1', 'PROG1', 'Programmation 1', 6, 'COMMON'),
('c2', 'MATH1', 'Mathématiques 1', 5, 'COMMON'),
('c3', 'ANG1', 'Anglais 1', 3, 'COMMON'),
('c4', 'SYS1', 'Systèmes et réseaux 1', 5, 'COMMON'),
('c5', 'BD1', 'Bases de données 1', 5, 'COMMON'),
('c6', 'ALGO1', 'Algorithmique', 5, 'COMMON'),
('c7', 'PROG2', 'Programmation 2', 6, 'COMMON'),
('c8', 'MATH2', 'Mathématiques 2', 5, 'COMMON'),
('c9', 'ANG2', 'Anglais 2', 3, 'COMMON'),
('c10', 'RES1', 'Réseaux 1', 5, 'COMMON'),
('c11', 'STAT1', 'Statistiques', 4, 'COMMON'),
('c12', 'PROJ1', 'Projet fil rouge 1', 7, 'COMMON'),
('c13', 'POO1', 'Programmation orientée objet', 6, 'COMMON'),
('c14', 'BD2', 'Bases de données 2', 5, 'COMMON'),
('c15', 'ANG3', 'Anglais 3', 3, 'COMMON'),
('c16', 'GENI', 'Génie logiciel', 6, 'COMMON'),
('c17', 'WEB1', 'Développement web 1', 5, 'EL'),
('c18', 'PROG3', 'Programmation avancée', 5, 'EL'),
('c19', 'WEB2', 'Développement web 2', 8, 'EL'),
('c20', 'MOB1', 'Développement mobile', 6, 'EL'),
('c21', 'TN1', 'Réseaux de télécoms 1', 5, 'TN'),
('c22', 'TN2', 'Traitement du signal', 5, 'TN'),
('c23', 'TN3', 'Télécoms 2', 8, 'TN'),
('c24', 'TN4', 'Antennes et propagation', 6, 'TN'),
('c25', 'PROJ3', 'Projet fil rouge 3', 8, 'COMMON'),
('c26', 'ANG5', 'Anglais 5', 3, 'COMMON'),
('c27', 'ETHI', 'Éthique et professionnalisme', 3, 'COMMON'),
('c28', 'AI1', 'Intelligence artificielle', 8, 'EL'),
('c29', 'CLOUD1', 'Cloud et DevOps', 8, 'EL'),
('c30', 'AI2', 'IA avancée', 8, 'EL'),
('c31', 'CLOUD2', 'Microservices', 4, 'EL'),
('c32', 'TN5', 'Télécoms 3', 8, 'TN'),
('c33', 'TN6', 'Réseaux optiques', 8, 'TN'),
('c34', 'TN7', 'Systèmes embarqués', 8, 'TN'),
('c35', 'TN8', 'Sécurité des télécoms', 4, 'TN');

INSERT INTO course_teacher (id, course_id, teacher_id)
SELECT 'ct_' || c.id || '_' || t.id, c.id, t.id
FROM course c
CROSS JOIN (VALUES ('t1'), ('t2'), ('t3'), ('t4')) AS t(id)
WHERE (t.id = 't1' AND c.id IN ('c1','c2','c3','c4','c5','c6','c7','c8','c9','c10','c11','c12'))
   OR (t.id = 't2' AND c.id IN ('c13','c14','c15','c16','c17','c18','c19','c20'))
   OR (t.id = 't3' AND c.id IN ('c13','c14','c15','c16','c21','c22','c23','c24'))
   OR (t.id = 't4' AND c.id IN ('c25','c26','c27','c28','c29','c30','c31','c32','c33','c34','c35'));

INSERT INTO course_group (id, course_id, group_id)
SELECT 'cg_' || c.id || '_' || g.id, c.id, g.id
FROM course c
CROSS JOIN "group" g
WHERE (c.id IN ('c1','c2','c3','c4','c5','c6','c7','c8','c9','c10','c11','c12') AND g.id IN ('k1','k2','k3','k4'))
   OR (c.id IN ('c13','c14','c15','c16') AND g.id IN ('k5','k6','k7','k8'))
   OR (c.id IN ('c17','c18','c19','c20') AND g.id IN ('k5','k6'))
   OR (c.id IN ('c21','c22','c23','c24') AND g.id IN ('k7','k8'))
   OR (c.id IN ('c25','c26','c27') AND g.id IN ('k9','k10','k11','k12'))
   OR (c.id IN ('c28','c29','c30','c31') AND g.id IN ('k9','k10'))
   OR (c.id IN ('c32','c33','c34','c35') AND g.id IN ('k11','k12'));

INSERT INTO student_group (id, student_id, group_id, joined_at) VALUES
('sg1_1', 's1', 'k1', '2024-09-02'),
('sg1_2', 's1', 'k5', '2025-09-01'),
('sg1_3', 's1', 'k9', '2026-09-01'),
('sg2_1', 's2', 'k1', '2024-09-02'),
('sg2_2', 's2', 'k5', '2025-09-01'),
('sg2_3', 's2', 'k9', '2026-09-01'),
('sg3_1', 's3', 'k1', '2024-09-02'),
('sg3_2', 's3', 'k5', '2025-09-01'),
('sg3_3', 's3', 'k9', '2026-09-01'),
('sg4_1', 's4', 'k1', '2024-09-02'),
('sg4_2', 's4', 'k7', '2025-09-01'),
('sg4_3', 's4', 'k11', '2026-09-01'),
('sg5_1', 's5', 'k1', '2024-09-02'),
('sg5_2', 's5', 'k7', '2025-09-01'),
('sg5_3', 's5', 'k11', '2026-09-01'),
('sg6_1', 's6', 'k1', '2024-09-02'),
('sg6_2', 's6', 'k7', '2025-09-01'),
('sg6_3', 's6', 'k11', '2026-09-01'),
('sg7', 's7', 'k2', '2026-09-01'),
('sg8', 's8', 'k3', '2026-09-01'),
('sg9', 's9', 'k4', '2026-09-01'),
('sg10', 's10', 'k4', '2026-09-01');

WITH course_year (course_id, year) AS (
    VALUES
        ('c1', 1), ('c2', 1), ('c3', 1), ('c4', 1), ('c5', 1), ('c6', 1),
        ('c7', 1), ('c8', 1), ('c9', 1), ('c10', 1), ('c11', 1), ('c12', 1),
        ('c13', 2), ('c14', 2), ('c15', 2), ('c16', 2), ('c17', 2), ('c18', 2),
        ('c19', 2), ('c20', 2), ('c21', 2), ('c22', 2), ('c23', 2), ('c24', 2),
        ('c25', 3), ('c26', 3), ('c27', 3), ('c28', 3), ('c29', 3), ('c30', 3),
        ('c31', 3), ('c32', 3), ('c33', 3), ('c34', 3), ('c35', 3)
)
INSERT INTO exam (id, course_id, academic_year, label, exam_datetime, coefficient_num, coefficient_den)
SELECT 'e' || cy.course_id || '_1',
       cy.course_id,
       cy.year,
       'Partiel 1',
       (CASE WHEN cy.year = 1 THEN '2025-01-15 09:00:00+03'
             WHEN cy.year = 2 THEN '2025-12-15 09:00:00+03'
             ELSE '2026-12-10 09:00:00+03' END)::timestamptz,
       1,
       CASE WHEN cy.year = 1 THEN 4 ELSE 2 END
FROM course_year cy;

WITH course_year (course_id, year) AS (
    VALUES
        ('c1', 1), ('c2', 1), ('c3', 1), ('c4', 1), ('c5', 1), ('c6', 1),
        ('c7', 1), ('c8', 1), ('c9', 1), ('c10', 1), ('c11', 1), ('c12', 1),
        ('c13', 2), ('c14', 2), ('c15', 2), ('c16', 2), ('c17', 2), ('c18', 2),
        ('c19', 2), ('c20', 2), ('c21', 2), ('c22', 2), ('c23', 2), ('c24', 2),
        ('c25', 3), ('c26', 3), ('c27', 3), ('c28', 3), ('c29', 3), ('c30', 3),
        ('c31', 3), ('c32', 3), ('c33', 3), ('c34', 3), ('c35', 3)
)
INSERT INTO exam (id, course_id, academic_year, label, exam_datetime, coefficient_num, coefficient_den)
SELECT 'e' || cy.course_id || '_2',
       cy.course_id,
       cy.year,
       'Partiel 2',
       (CASE WHEN cy.year = 1 THEN '2025-02-15 09:00:00+03'
             WHEN cy.year = 2 THEN '2026-01-20 09:00:00+03'
             ELSE '2027-01-15 09:00:00+03' END)::timestamptz,
       1,
       CASE WHEN cy.year = 1 THEN 4 ELSE 2 END
FROM course_year cy;

WITH course_year (course_id, year) AS (
    VALUES
        ('c1', 1), ('c2', 1), ('c3', 1), ('c4', 1), ('c5', 1), ('c6', 1),
        ('c7', 1), ('c8', 1), ('c9', 1), ('c10', 1), ('c11', 1), ('c12', 1)
)
INSERT INTO exam (id, course_id, academic_year, label, exam_datetime, coefficient_num, coefficient_den)
SELECT 'e' || cy.course_id || '_3',
       cy.course_id,
       cy.year,
       'Examen final',
       '2025-03-10 09:00:00+03'::timestamptz,
       1,
       2
FROM course_year cy;

WITH student_base (student_id, base) AS (
    VALUES ('s1', 14.0), ('s2', 12.0), ('s3', 9.0), ('s4', 13.0),
           ('s5', 8.8), ('s6', 12.0), ('s7', 12.0), ('s8', 13.0),
           ('s9', 10.5), ('s10', 11.0)
)
INSERT INTO exam_grade (id, exam_id, student_id, score, updated_by)
SELECT 'eg_' || sg.student_id || '_' || e.id,
       e.id,
       sg.student_id,
       ROUND(GREATEST(0, LEAST(20, sb.base + (MOD(ABS(HASHTEXT(sg.student_id || e.id)), 5) - 2)))::numeric, 1)::double precision,
       'u1'
FROM student_group sg
JOIN student_base sb ON sb.student_id = sg.student_id
JOIN course_group cg ON cg.group_id = sg.group_id
JOIN exam e ON e.course_id = cg.course_id
WHERE NOT (sg.student_id = 's2' AND cg.course_id = 'c30')
  AND NOT (sg.student_id = 's10' AND cg.course_id IN ('c7', 'c9'));
