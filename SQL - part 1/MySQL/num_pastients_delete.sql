//trigger - delete on queue table
DELIMITER $$
CREATE TRIGGER num_pastients_delete
after delete ON queue 
FOR EACH ROW 
BEGIN 
    UPDATE queue_summery SET pastient_num=pastient_num-1 where doctor_id in
     (select doctor_id from queue_reserved where queue_reserved.queue_id=old.queue_id);
END$$
DELIMITER ;
