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

