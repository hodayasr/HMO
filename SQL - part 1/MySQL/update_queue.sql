CREATE DEFINER=`root`@`localhost` PROCEDURE `update_queue`( IN stIDdoctor INT ,IN stName varchar(50) )
BEGIN  
   update `healthmaintenance`.`queue` set  
`time` = current_time()
WHERE `queue_id` in (select queue_id from queue_reserved where pastient_id in
 (select pastient_id from patients where pastient_name=stName) and doctor_id=stIDdoctor);
END