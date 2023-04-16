
--Populate students table
INSERT INTO student (name, email, token, used_token, group_number) VALUES('John Doe', 'john.doe@example.com', '7c6a180b36896a0a8c02787eeafb0e4c', false,1);

INSERT INTO student ( name, email, token, used_token, password, group_number, hobby) VALUES('Jane Smith', 'jane.smith@example.com', 'efgh5678', true, '6cb75f652a9b52798eb6cf2201057c73', 1, 'Swimming');

INSERT INTO student (name, email, token, used_token, password, group_number, hobby) VALUES ('Alice Lee', 'alice.lee@example.com', 'mnop3456', true, '819b0643d6b89dc9b579fdfc9094f28e', 2, 'Dancing');

INSERT INTO student ( name, email, token, used_token, password, group_number, hobby) VALUES ( 'Bob Johnson', 'bob.johnson@example.com', 'addfv456', true, '819b0643d6b89dc9b579fdfc9094f28e', 2, 'Singing');

-- Populate submissions table
INSERT INTO submission (student_id, submission_link, submission_date, grade) VALUES
( 1, 'https://example.com/submission1', '2022-03-20', 9.5),
(2, 'https://example.com/submission2', '2022-03-21', 7.0),
(3, 'https://example.com/submission3', '2022-03-22', 0);

-- Populate assignments table
INSERT INTO assignment ( name, deadline, description) VALUES
( 'Lab Activity 1', '2022-04-10', 'Introduction to programming'),
( 'Lab Activity 2', '2022-04-17', 'Data structures and algorithms'),
( 'Lab Activity 3', '2022-04-24', 'Object-oriented programming'),
( 'Lab Activity 4', '2022-05-01', 'Software engineering principles');

-- Populate laboratories table
INSERT INTO laboratory (week_number, title, date, curricula, description, assignment_id) VALUES
( 1, 'Introduction to Java', '2022-04-05', 'Java basics and syntax', 'This lab activity introduces students to the Java programming language and covers the basic syntax and data types used in Java programming.', 1),
( 2, 'Arrays and Lists', '2022-04-12', 'Working with arrays and lists', 'This lab activity covers the use of arrays and lists in Java programming and provides students with hands-on experience in working with these data structures.', 2),
( 3, 'Object-Oriented Programming Concepts', '2022-04-19', 'Object-oriented programming concepts', 'This lab activity covers the fundamental concepts of object-oriented programming, including classes, objects, inheritance, and polymorphism.', 3),
( 4, 'Software Design and Testing', '2022-04-26', 'Software design and testing', 'This lab activity covers the principles of software design and testing, including software architecture, design patterns, and testing methodologies.', 4);


INSERT INTO assignment_submissions (assignment_id, submissions_id) VALUES (1, 1);
INSERT INTO assignment_submissions (assignment_id, submissions_id) VALUES (2, 2);
INSERT INTO assignment_submissions (assignment_id, submissions_id) VALUES (2, 3);

INSERT INTO teacher ( name, email, password) VALUES
('John King', 'john@gmail.com', '7c6a180b36896a0a8c02787eeafb0e4c'),
('Lola Smith', 'lolasmith@yahoo.com', '6cb75f652a9b52798eb6cf2201057c73'),
('Maria Klare', 'maryK@company.com', '819b0643d6b89dc9b579fdfc9094f28e');


INSERT INTO teacher_labs (teacher_id, labs_id) VALUES
(1, 1),
(1, 2),
(2, 3);

INSERT INTO laboratory_attendance (laboratory_id, attendance_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 2),
       (2, 4),
       (3, 1),
       (3, 3);

