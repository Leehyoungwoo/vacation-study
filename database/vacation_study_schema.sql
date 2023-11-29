use vacationStudy;

DROP TABLE IF EXISTS `Members`;

CREATE TABLE Members (
    member_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(45) UNIQUE NOT NULL,
    name VARCHAR(45) NOT NULL,
    password VARCHAR(1000) NOT NULL,
    role ENUM('user', 'admin') NOT NULL DEFAULT 'user',
    email VARCHAR(45) UNIQUE NOT NULL,
    nickname VARCHAR(45) UNIQUE NOT NULL,
    is_deleted BOOLEAN DEFAULT FALSE
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;

DROP TABLE IF EXISTS `Boards`;

CREATE TABLE Boards (
    board_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    content TEXT NOT NULL,
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    member_id INT NOT NULL,
	is_deleted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (member_id) REFERENCES Members(member_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `Comments`;

CREATE TABLE Comments (
    comment_id INT PRIMARY KEY AUTO_INCREMENT,
    content TEXT NOT NULL,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    board_id INT NOT NULL,
    member_id INT NOT NULL,
    is_deleted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (board_id) REFERENCES Boards(board_id),
    FOREIGN KEY (member_id) REFERENCES Members(member_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `Board_likes`;

CREATE TABLE `Board_likes` (
  `board_id` INT NOT NULL,
  `member_id` INT NOT NULL,
  PRIMARY KEY (`board_id`, `member_id`),
  KEY `fk_board_like_member1_idx` (`member_id`),
  CONSTRAINT `fk_board_like_board1` FOREIGN KEY (`board_id`) REFERENCES `Boards` (`board_id`),
  CONSTRAINT `fk_board_like_member1` FOREIGN KEY (`member_id`) REFERENCES `Members` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `Comment_likes`;

CREATE TABLE `Comment_likes` (
  `comment_id` INT NOT NULL,
  `member_id` INT NOT NULL,
  PRIMARY KEY (`comment_id`, `member_id`),
  KEY `fk_comment_likes_member_idx` (`member_id`),
  CONSTRAINT `fk_comment_likes_comment` FOREIGN KEY (`comment_id`) REFERENCES `Comments` (`comment_id`),
  CONSTRAINT `fk_comment_likes_member` FOREIGN KEY (`member_id`) REFERENCES `Members` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

