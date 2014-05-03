--<ScriptOptions statementTerminator=";"/>
CREATE TABLE users (
	UID BIGINT NOT NULL AUTO_INCREMENT,
	NAME VARCHAR(20) NOT NULL,
	TITLE VARCHAR(100),
	PROFILE VARCHAR(1000),
	EMAIL VARCHAR(40) NOT NULL,
	PASSWD VARCHAR(50) NOT NULL,
	CREDIT INT DEFAULT 0 NOT NULL,
	REPUTATION INT DEFAULT 0 NOT NULL,
	SKILL VARCHAR(200),
	STATUS CHAR(10) DEFAULT 0 NOT NULL,
	REGTIME TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
	RESUME MEDIUMTEXT,
	PRIMARY KEY (UID)
) ENGINE=InnoDB CHARACTER SET=utf8;

CREATE TABLE category_zh (
	CID BIGINT NOT NULL AUTO_INCREMENT,
	PID BIGINT DEFAULT 0 NOT NULL,
	CATNAME VARCHAR(100) NOT NULL,
	DESCRIPTION VARCHAR(1000),
	RATE CHAR(10) DEFAULT 0,
	PRIMARY KEY (CID)
) ENGINE=InnoDB CHARACTER SET=utf8;

CREATE TABLE category_en (
	CID BIGINT NOT NULL AUTO_INCREMENT,
	PID BIGINT DEFAULT 0 NOT NULL,
	CATNAME VARCHAR(100) NOT NULL,
	DESCRIPTION VARCHAR(1000),
	RATE CHAR(10) DEFAULT 0,
	PRIMARY KEY (CID)
) ENGINE=InnoDB;


CREATE TABLE question_en (
	QID BIGINT NOT NULL AUTO_INCREMENT,
	PID BIGINT DEFAULT 0,
	CID BIGINT DEFAULT 0,
	QUESTION VARCHAR(400) NOT NULL,
	RATE INT DEFAULT 0 NOT NULL,
	ANSWERCNT INT DEFAULT 0 NOT NULL,
	TAG VARCHAR(100),
	UID BIGINT DEFAULT 0 NOT NULL,
	DESCRIPTION TEXT,
	STATUS CHAR(10) DEFAULT 0 NOT NULL,
	qtime TIMESTAMP DEFAULT 'CURRENT_TIMESTAMP' NOT NULL,
	PRIMARY KEY (QID)
) ENGINE=InnoDB;



CREATE TABLE question_zh (
	QID BIGINT NOT NULL AUTO_INCREMENT,
	PID BIGINT DEFAULT 0,
	CID BIGINT DEFAULT 0,
	QUESTION VARCHAR(400) NOT NULL,
	RATE INT DEFAULT 0 NOT NULL,
	ANSWERCNT INT DEFAULT 0 NOT NULL,
	TAG VARCHAR(100),
	UID BIGINT DEFAULT 0 NOT NULL,
	DESCRIPTION TEXT,
	STATUS CHAR(10) DEFAULT 0 NOT NULL,
	qtime TIMESTAMP DEFAULT 'CURRENT_TIMESTAMP' NOT NULL,
	PRIMARY KEY (QID)
) ENGINE=InnoDB CHARACTER SET=utf8;


CREATE TABLE answer_zh (
	AID BIGINT NOT NULL AUTO_INCREMENT,
	QID BIGINT NOT NULL,
	ANSWER MEDIUMTEXT NOT NULL,
	RATE INT DEFAULT 0 NOT NULL,
	UID BIGINT DEFAULT 0 NOT NULL,
	atime TIMESTAMP DEFAULT 'CURRENT_TIMESTAMP' NOT NULL,
	isanswer CHAR(5) DEFAULT 0,
	PRIMARY KEY (AID)
) ENGINE=InnoDB CHARACTER SET=utf8;

CREATE TABLE answer_en (
	AID BIGINT NOT NULL AUTO_INCREMENT,
	QID BIGINT NOT NULL,
	ANSWER MEDIUMTEXT NOT NULL,
	RATE INT DEFAULT 0 NOT NULL,
	UID BIGINT DEFAULT 0 NOT NULL,
	atime TIMESTAMP DEFAULT 'CURRENT_TIMESTAMP' NOT NULL,
	isanswer CHAR(5) DEFAULT 0,
	PRIMARY KEY (AID)
) ENGINE=InnoDB;

CREATE TABLE comment_en (
	COID BIGINT NOT NULL AUTO_INCREMENT,
	QID BIGINT NOT NULL,
	COMMENT TINYTEXT NOT NULL,
	UID BIGINT DEFAULT 0 NOT NULL,
	ctime TIMESTAMP DEFAULT 'CURRENT_TIMESTAMP' NOT NULL,
	PRIMARY KEY (COID)
) ENGINE=InnoDB;

CREATE TABLE comment_zh (
	COID BIGINT NOT NULL AUTO_INCREMENT,
	QID BIGINT NOT NULL,
	COMMENT TINYTEXT NOT NULL,
	UID BIGINT DEFAULT 0 NOT NULL,
	ctime TIMESTAMP DEFAULT 'CURRENT_TIMESTAMP' NOT NULL,
	PRIMARY KEY (COID)
) ENGINE=InnoDB CHARACTER SET=utf8;


CREATE TABLE vote (
	VID BIGINT NOT NULL,
	VTYPE INT NOT NULL,
	UID BIGINT NOT NULL,
	UNIQUE(VID,VTYPE,UID)
) ENGINE=InnoDB;




CREATE TABLE todo (
	TID BIGINT NOT NULL AUTO_INCREMENT,
	TODO VARCHAR(1000) NOT NULL,
	NOTE TEXT NOT NULL,
	TTYPE CHAR(10) DEFAULT 0 NOT NULL,
	UID BIGINT DEFAULT 0 NOT NULL,
	PRIORITY CHAR(10) DEFAULT 0 NOT NULL,
	STATUS CHAR(10) DEFAULT 0 NOT NULL,
	ttime TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
	deadline TIMESTAMP DEFAULT '0000-00-00 00:00:00' NOT NULL,
	PRIMARY KEY (TID)
) ENGINE=InnoDB CHARACTER SET=utf8;


--2014/4/13
DROP TABLE userlog;
CREATE TABLE userlog(
	LID BIGINT NOT NULL AUTO_INCREMENT,
	UID BIGINT DEFAULT 0 NOT NULL,
	LTIME TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
	TAG VARCHAR(100),
	SUBJECT VARCHAR(1000) NOT NULL,
	ULOG MEDIUMTEXT NOT NULL,
	STATUS CHAR(10) DEFAULT 0,
	LTYPE CHAR(5) DEFAULT 0,
	PRIMARY KEY (LID)
)ENGINE=InnoDB CHARACTER SET=utf8;
