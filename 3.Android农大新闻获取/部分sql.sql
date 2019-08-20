create database school;

create table student(
	id char(20) primary key,
	password char(50),
	name char(20),
	college char(30),
	major char(30),
	class char(30)
)default character set utf8 collate utf8_general_ci;
insert into student values('2014234060301','123','吕雪杰','信息科学与技术学院','软件工程','软件1402班');
create table news(
	id int primary key,
	title char(100),
	content_url char(100)
)default character set utf8 collate utf8_general_ci;

insert into news values(1,'党委常委李彤到动物医学院（中兽医学院）调研指导工作','http://www.hebau.edu.cn/art/2016/12/16/art_2_17468.html');
insert into news values(2,'我校传达学习河北省第九次党代会精神','http://www.hebau.edu.cn/art/2016/12/14/art_2_17465.html');
insert into news values(3,'郭素萍 李保国家庭荣获第一届全国文明家庭称号','http://www.hebau.edu.cn/art/2016/12/13/art_2_17452.html');
insert into news values(4,'校友李英贤研究员当选国际宇航科学院院士','http://www.hebau.edu.cn/art/2016/12/13/art_2_17453.html');