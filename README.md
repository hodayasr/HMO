# HMO

ex. 2 -DataBase course

#SQL + JAVA - part 1 : HMO DataBase

using MySQL Connector
java / App - 
	// insert doctor

	// question 1 - select doctor's timetable

	// question 2 - update current queue time for pastient by pastient's name by store procedure

		/*  stored procedure :
		 * CREATE DEFINER=`root`@`localhost` PROCEDURE `update_queue`(IN stName varchar(50))
		 * BEGIN  
		 * update `healthmaintenance`.`queue` set  
		 * `time` = current_time()
		 * WHERE `queue_id` in (select queue_id from queue_reserved where pastient_id in
		 * (select pastient_id from patients where pastient_name=stName));
		 * END
		 */

	// question 3 - view 

#part 2 : DB Normalization 

