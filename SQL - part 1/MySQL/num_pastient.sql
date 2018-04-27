//trigger - insert on queue table

DELIMITER $$
CREATE TRIGGER num_pastients
after INSERT ON queue 
FOR EACH ROW 
BEGIN 
    UPDATE queue_summery SET pastient_num= (SELECT count(queue_id) 
    FROM queue WHERE time<= now() and queue_id   in 
    (select queue_id from queue_reserved where doctor_id=queue_summery.doctor_id))
    where doctor_id  in 
    (select doctor_id from queue_reserved where queue_reserved.queue_id=NEW.queue_id);
END$$
DELIMITER ;
