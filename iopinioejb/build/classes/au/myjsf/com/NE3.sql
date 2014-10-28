CREATE TABLE Vote_OldItem (
		id int NOT NULL AUTO_INCREMENT,
		name VARCHAR(200) NOT NULL,
		datetime TIMESTAMP ,
		yCount VARCHAR(2000) NOT NULL,
		nCount  VARCHAR(20) NOT NULL,
		PRIMARY KEY (id)
	);
	drop TABLE Vote_OldItem_V1;
INSERT into Vote_OldItem(name,datetime,yCount,nCount) values('Should the reserve bank decrease the interest rate1?',current_timestamp(),'80','10')

SELECT * FROM Vote_OldItem_V1