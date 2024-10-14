-- 사용자 데이터 삽입
INSERT INTO `users` (`user_name`, `user_password`, `user_email`, `user_status`) VALUES
        ('testUser1', 'password!', 'testuser1@example.com', 'REGISTER'),
('testUser2', 'password!', 'testuser2@example.com', 'REGISTER'),
        ('testUser3', 'password!', 'testuser3@example.com', 'WITHDRAW');

-- 프로젝트 데이터 삽입
INSERT INTO `projects` (`project_name`, `project_status`) VALUES
        ('Project 1', 'ACTIVE'),
('Project 2', 'ACTIVE'),
        ('Project 3', 'END');

-- 프로젝트 멤버 데이터 삽입
INSERT INTO `project_members` (`user_id`, `project_id`, `member_role`) VALUES
        (1, 1, 'MEMBER'),
        (2, 1, 'ADMIN'),
        (3, 1, 'MEMBER'),
        (3, 2, 'MEMBER'),
        (1, 2, 'ADMIN'),
        (3, 3, 'ADMIN');

        -- 마일스톤 데이터 삽입
INSERT INTO `milestones` (`project_id`, `milestone_name`) VALUES
        (1, 'Milestone1'),
(2, 'Milestone2'),
        (1, 'Milestone3');

        -- 태그 데이터 삽입
INSERT INTO `tags` (`project_id`, `tag_name`) VALUES
        (1, 'Development'),
(1, 'Testing'),
        (2, 'Design'),
        (3, 'Documentation');

        -- 작업 데이터 삽입
INSERT INTO `tasks` (`project_id`, `milestone_id`, `user_id`, `task_name`, `created_at`, `task_content`) VALUES
        (1, 1, 1, 'Task 1 for Project 1', '2024-10-01 10:00:00', 'Content for Task 1'),
        (1, 1, 2, 'Task 2 for Project 2', '2024-10-01 11:00:00', 'Content for Task 2'),
        (2, 2, 3, 'Task 1 for Project 3', '2024-10-02 10:00:00', 'Content for Task 3');

        -- 프로젝트 태그 데이터 삽입
INSERT INTO `project_tags` ( `tag_id`, `task_id`) VALUES
        (1, 1),
        (2, 1),
        (3, 2),
        (4, 3);

-- 댓글 데이터 삽입
INSERT INTO `comments` (`task_id`, `user_id`, `content`, `created_at`) VALUES
        (1, 1, 'This is a comment on Task 1', '2024-10-01 10:00:00'),
         (1, 1, 'This is a comment on Task 2', '2024-10-01 10:00:00'),
        (2, 2, 'This is a comment on Task 3', '2024-10-01 11:00:00');
