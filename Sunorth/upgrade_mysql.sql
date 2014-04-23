

--2014/4/21
alter table userlog add column lid BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY;
alter table userlog add column ltype char(5) default 0;
update userlog set status=1;
--2014/4/20

alter table category_en add column description varchar(1000);
alter table category_en modify column rate char(10) default 0;
alter table category_zh add column description varchar(1000);
alter table category_zh modify column rate char(10)  default 0;
update category_en set rate=0;
update category_zh set rate=0;

--2014/4/15
alter table users add column title varchar(100);
alter table users add column profile varchar(1000);
alter table users add column resume MEDIUMTEXT;

--2014/4/13
CREATE TABLE userlog(
	UID BIGINT DEFAULT 0 NOT NULL,
	LTIME TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
	TAG VARCHAR(100),
	SUBJECT VARCHAR(1000) NOT NULL,
	ULOG MEDIUMTEXT NOT NULL

)ENGINE=InnoDB CHARACTER SET=utf8;

--2014/4/9
alter table todo modify column note text;
alter table comment_en modify column comment text;
alter table comment_en modify column comment text;
--2014/4/8

alter table question_en drop column qtime;
alter table question_zh drop column qtime;
alter table answer_en drop column atime;
alter table answer_zh drop column atime;
alter table comment_en drop column ctime;
alter table comment_zh drop column ctime;
alter table todo drop column ttime;
alter table todo drop column deadline;
alter table users drop column regtime;

ALTER TABLE question_en add column  qtime TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL;
ALTER TABLE question_zh add column  qtime TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL;

ALTER TABLE answer_en add column  atime TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL;
ALTER TABLE answer_zh add column  atime TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL;

ALTER TABLE comment_en add column  ctime TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL;
ALTER TABLE comment_zh add column  ctime TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL;

ALTER TABLE todo add column  ttime TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL;
ALTER TABLE todo add column  deadline TIMESTAMP DEFAULT '0000-00-00 00:00:00' NOT NULL;

ALTER TABLE users add column regtime TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL;

ALTER TABLE answer_en add column  isanswer CHAR(5) DEFAULT 0;
ALTER TABLE answer_zh add column  isanswer CHAR(5) DEFAULT 0;

