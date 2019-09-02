	CREATE TABLE users (
    id              INT             NOT NULL AUTO_INCREMENT,
    first_name      VARCHAR(14)     NOT NULL,
    last_name       VARCHAR(16)     		,
	user_password   VARCHAR(40) 	NOT NULL,
	mobile_number   VARCHAR(40) 	NOT NULL,
	mobile_verified BOOLEAN         NOT NULL,
	user_email      VARCHAR(40) 	NOT NULL,
    user_interests  VARCHAR(100)    ,
    user_role       VARCHAR(16)     ,
	user_type       VARCHAR(16) 	,
	device_id       VARCHAR(40) 	NOT NULL,
	PRIMARY KEY ( id )
	);