ALTER TABLE group_x_user DROP FOREIGN KEY user_id;
ALTER TABLE group_x_user MODIFY user_id INT;
ALTER TABLE tg_user MODIFY chat_id INT;
ALTER TABLE group_x_user
    ADD CONSTRAINT user_id
        FOREIGN KEY (user_id) REFERENCES tg_user (chat_id);