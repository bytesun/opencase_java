--<ScriptOptions statementTerminator=";"/>

ALTER TABLE `sunorthdb`.`comment_zh` DROP PRIMARY KEY;

ALTER TABLE `sunorthdb`.`users` DROP PRIMARY KEY;

ALTER TABLE `sunorthdb`.`answer_en` DROP PRIMARY KEY;

ALTER TABLE `sunorthdb`.`vote_en` DROP PRIMARY KEY;

ALTER TABLE `sunorthdb`.`question_en` DROP PRIMARY KEY;

ALTER TABLE `sunorthdb`.`category_zh` DROP PRIMARY KEY;

ALTER TABLE `sunorthdb`.`question_zh` DROP PRIMARY KEY;

ALTER TABLE `sunorthdb`.`todo` DROP PRIMARY KEY;

ALTER TABLE `sunorthdb`.`answer_zh` DROP PRIMARY KEY;

ALTER TABLE `sunorthdb`.`comment_en` DROP PRIMARY KEY;

ALTER TABLE `sunorthdb`.`category_en` DROP PRIMARY KEY;

ALTER TABLE `sunorthdb`.`vote_zh` DROP PRIMARY KEY;

DROP TABLE `sunorthdb`.`comment_en`;

DROP TABLE `sunorthdb`.`favoriate`;

DROP TABLE `sunorthdb`.`question_en`;

DROP TABLE `sunorthdb`.`question_zh`;

DROP TABLE `sunorthdb`.`users`;

DROP TABLE `sunorthdb`.`todo`;

DROP TABLE `sunorthdb`.`vote_zh`;

DROP TABLE `sunorthdb`.`category_en`;

DROP TABLE `sunorthdb`.`answer_zh`;

DROP TABLE `sunorthdb`.`category_zh`;

DROP TABLE `sunorthdb`.`answer_en`;

DROP TABLE `sunorthdb`.`comment_zh`;


DROP TABLE `sunorthdb`.`vote_en`;

CREATE TABLE `sunorthdb`.`comment_en` (
	`COID` BIGINT NOT NULL,
	`QID` BIGINT NOT NULL,
	`CTIME` BIGINT DEFAULT 0 NOT NULL,
	`COMMENT` TINYTEXT NOT NULL,
	`UID` BIGINT DEFAULT 0 NOT NULL,
	PRIMARY KEY (`COID`)
) ENGINE=InnoDB;



CREATE TABLE `sunorthdb`.`question_en` (
	`QID` BIGINT NOT NULL,
	`PID` BIGINT DEFAULT 0,
	`CID` BIGINT DEFAULT 0,
	`QUESTION` VARCHAR(400) NOT NULL,
	`RATE` INT DEFAULT 0 NOT NULL,
	`ANSWERCNT` INT DEFAULT 0 NOT NULL,
	`TAG` VARCHAR(100),
	`UID` BIGINT DEFAULT 0 NOT NULL,
	`QTIME` BIGINT DEFAULT 0 NOT NULL,
	`DESCRIPTION` TEXT,
	`STATUS` CHAR(10) DEFAULT 0 NOT NULL,
	PRIMARY KEY (`QID`)
) ENGINE=InnoDB;

CREATE TABLE `sunorthdb`.`question_zh` (
	`QID` BIGINT NOT NULL,
	`PID` BIGINT DEFAULT 0,
	`CID` BIGINT DEFAULT 0,
	`QUESTION` VARCHAR(400) NOT NULL,
	`RATE` INT DEFAULT 0 NOT NULL,
	`ANSWERCNT` INT DEFAULT 0 NOT NULL,
	`TAG` VARCHAR(100),
	`UID` BIGINT DEFAULT 0 NOT NULL,
	`QTIME` BIGINT DEFAULT 0 NOT NULL,
	`DESCRIPTION` TEXT,
	`STATUS` CHAR(10) DEFAULT 0 NOT NULL,
	PRIMARY KEY (`QID`)
) ENGINE=InnoDB DEFAULT CHARACTER SET = utf8;

CREATE TABLE `sunorthdb`.`users` (
	`UID` BIGINT NOT NULL,
	`NAME` VARCHAR(20) NOT NULL,
	`EMAIL` VARCHAR(40) NOT NULL,
	`PASSWD` VARCHAR(50) NOT NULL,
	`CREDIT` INT DEFAULT 0 NOT NULL,
	`REPUTATION` INT DEFAULT 0 NOT NULL,
	`REGTIME` BIGINT DEFAULT 0 NOT NULL,
	`SKILL` VARCHAR(200),
	`STATUS` CHAR(10) DEFAULT 0 NOT NULL,
	PRIMARY KEY (`UID`)
) ENGINE=InnoDB DEFAULT CHARACTER SET = utf8;

CREATE TABLE `sunorthdb`.`todo` (
	`TID` BIGINT NOT NULL,
	`TODO` VARCHAR(1000) NOT NULL,
	`NOTE` TINYTEXT NOT NULL,
	`TTIME` BIGINT DEFAULT 0 NOT NULL,
	`TTYPE` CHAR(100) DEFAULT 0 NOT NULL,
	`UID` BIGINT DEFAULT 0 NOT NULL,
	`PRIORITY` CHAR(10) DEFAULT 0 NOT NULL,
	`STATUS` CHAR(10) DEFAULT 0 NOT NULL,
	`DEADLINE` BIGINT,
	PRIMARY KEY (`TID`)
) ENGINE=InnoDB  DEFAULT CHARACTER SET = utf8;

CREATE TABLE `sunorthdb`.`vote_zh` (
	`VID` BIGINT NOT NULL,
	`QAID` BIGINT NOT NULL,
	`VTYPE` INT NOT NULL,
	`COMMENT` VARCHAR(1000) NOT NULL,
	`UID` BIGINT NOT NULL,
	PRIMARY KEY (`VID`)
) ENGINE=InnoDB DEFAULT CHARACTER SET = utf8;

CREATE TABLE `sunorthdb`.`category_en` (
	`CID` BIGINT NOT NULL,
	`PID` BIGINT DEFAULT 0 NOT NULL,
	`CATNAME` VARCHAR(100) NOT NULL,
	PRIMARY KEY (`CID`)
) ENGINE=InnoDB;

CREATE TABLE `sunorthdb`.`answer_zh` (
	`AID` BIGINT NOT NULL,
	`QID` BIGINT NOT NULL,
	`ATIME` BIGINT DEFAULT 0 NOT NULL,
	`ANSWER` MEDIUMTEXT NOT NULL,
	`RATE` INT DEFAULT 0 NOT NULL,
	`UID` BIGINT DEFAULT 0 NOT NULL,
	PRIMARY KEY (`AID`)
) ENGINE=InnoDB DEFAULT CHARACTER SET = utf8;

CREATE TABLE `sunorthdb`.`category_zh` (
	`CID` BIGINT NOT NULL,
	`PID` BIGINT DEFAULT 0 NOT NULL,
	`CATNAME` VARCHAR(100) NOT NULL,
	PRIMARY KEY (`CID`)
) ENGINE=InnoDB DEFAULT CHARACTER SET = utf8;

CREATE TABLE `sunorthdb`.`answer_en` (
	`AID` BIGINT NOT NULL,
	`QID` BIGINT NOT NULL,
	`ATIME` BIGINT DEFAULT 0 NOT NULL,
	`ANSWER` MEDIUMTEXT NOT NULL,
	`RATE` INT DEFAULT 0 NOT NULL,
	`UID` BIGINT DEFAULT 0 NOT NULL,
	PRIMARY KEY (`AID`)
) ENGINE=InnoDB;

CREATE TABLE `sunorthdb`.`comment_zh` (
	`COID` BIGINT NOT NULL,
	`QID` BIGINT NOT NULL,
	`CTIME` BIGINT DEFAULT 0 NOT NULL,
	`COMMENT` TINYTEXT NOT NULL,
	`UID` BIGINT DEFAULT 0 NOT NULL,
	PRIMARY KEY (`COID`)
) ENGINE=InnoDB DEFAULT CHARACTER SET = utf8;


CREATE TABLE `sunorthdb`.`vote_en` (
	`VID` BIGINT NOT NULL,
	`QAID` BIGINT NOT NULL,
	`VTYPE` INT NOT NULL,
	`COMMENT` VARCHAR(1000) NOT NULL,
	`UID` BIGINT NOT NULL,
	PRIMARY KEY (`VID`)
) ENGINE=InnoDB;
