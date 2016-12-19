
insert into students(name,password) values("朱晋宏","123456");
create table questions(
	id int auto_increment primary key,
	content varchar(300),
	answer varchar(50),
	type int
);
//题型1的表示答案只有一个
insert into questions(content,answer,type) values('请输入以下的数值得最小值：4,5,7,8,9,5,7,2,1','1',1);

insert into questions(content,answer,type) values('请输入以下的数值得最小值：4,5,7,8,9,5,7,2,10','2',1);
insert into questions(content,answer,type) values('请输入以下的数值得最小值：4,5,7,8,9,15,7','4',1);
insert into questions(content,answer,type) values('请输入以下的数值得最小值：7,8,10,65,9,5,7','5',1);
insert into questions(content,answer,type) values('请输入以下的数值得最小值：5,5,7,8,9,5,7,2,1','1',1);

insert into questions(content,answer,type) values('请输入以下的数值得最大值：4,5,7,8,9,5,7,2,1','9',1);
insert into questions(content,answer,type) values('请输入以下的数值得最大值：16,12,7,8,9,5,5,2,1','16',1);
insert into questions(content,answer,type) values('请输入以下的数值得最大值：16.5,16.7,18.1,8,9,5,5,2,1','18.1',1);

insert into questions(content,answer,type) values('请按照从小到大排列：5,4,1,2,3','1,2,3,4,5',5);
insert into questions(content,answer,type) values('请按照从小到大排列：6,4,1,2,3','1,2,3,4,6',5);
insert into questions(content,answer,type) values('请按照从小到大排列：5,4,1,4,6','1,4,4,5,6',5);

insert into questions(content,answer,type) values('请按照从大到小排列：5,4,1,2,3','5,4,3,2,1',5);
insert into questions(content,answer,type) values('请按照从大到小排列：6,4,1,2,3','6,4,3,2,1',5);
insert into questions(content,answer,type) values('请按照从大到小排列：7,4,1,2,3','7,4,3,2,1',5);

//不用对象了
create table record(
	id int auto_increment primary key,
	user blob
);

create table roughRecord(
	id int(32),
	examNum int,
	time datetime,
	score int,
	foreign key(id)
		references students(id),
	primary key(id,examNum)
);
create table detailRecord(
	id int(32),
	questionNum int,
	rightAns varchar(102),
	ifOk int;
	userAns varchar(102),
	examNum int,
	primary(id,examNum,questionNum),
	foreign key(id,examNum)
		references roughRecord(id,examNum)
);

	
