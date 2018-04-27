CREATE 
    ALGORITHM = UNDEFINED 
    DEFINER = `root`@`localhost` 
    SQL SECURITY DEFINER
VIEW `healthmaintenance`.`current_pastients_list` AS
    (SELECT 
        `healthmaintenance`.`queue_reserved`.`pastient_id` AS `pastient_id`,
        `healthmaintenance`.`queue_reserved`.`queue_time` AS `queue_time`,
        `healthmaintenance`.`queue`.`time` AS `time`,
        `healthmaintenance`.`queue_reserved`.`doctor_id` AS `doctor_id`
    FROM
        (`healthmaintenance`.`queue`
        JOIN `healthmaintenance`.`queue_reserved`)
    WHERE
        (`healthmaintenance`.`queue_reserved`.`queue_id` = `healthmaintenance`.`queue`.`queue_id`)
    ORDER BY TIMEDIFF(`healthmaintenance`.`queue`.`time`,
            `healthmaintenance`.`queue_reserved`.`queue_time`) DESC
    LIMIT 10)