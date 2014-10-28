SELECT * FROM VoteItem;
DELETE FROM VoteItem;
drop TABLE VoteItem;
INSERT into VoteItem(name,yCount,nCount) values('Are you confident about the Australian economy?','43','34')

CREATE TABLE VoteItem (
		id int NOT NULL AUTO_INCREMENT,
		name VARCHAR(200) NOT NULL,
		yCount VARCHAR(2000) NOT NULL,
		nCount  VARCHAR(20) NOT NULL,
		PRIMARY KEY (id)
	);
	
	CREATE TABLE USERMESSAGE (
		id int NOT NULL AUTO_INCREMENT,
		transaction_time timestamp,
		username VARCHAR(200) ,
		user_entered_time VARCHAR(200),
		message VARCHAR(3000),
		PRIMARY KEY (id)
	);


