CREATE TABLE users (
                       id UUID PRIMARY KEY,
                       username VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE tasks (
                       id UUID PRIMARY KEY,
                       user_id VARCHAR(255) NOT NULL,
                       creation_date DATE NOT NULL,
                       title VARCHAR(255) NOT NULL,
                       completed BOOLEAN NOT NULL,
                       deleted BOOLEAN NOT NULL,
                       target_date DATE,
                       CONSTRAINT fk_tasks_users FOREIGN KEY (user_id) REFERENCES users(username)
);

CREATE TABLE notifications (
                               id UUID PRIMARY KEY,
                               user_id VARCHAR(255) NOT NULL,
                               message VARCHAR(1000) NOT NULL,
                               creation_date TIMESTAMP NOT NULL,
                               read BOOLEAN NOT NULL,
                               CONSTRAINT fk_notifications_users FOREIGN KEY (user_id) REFERENCES users(username)
);
