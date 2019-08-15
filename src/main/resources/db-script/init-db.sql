drop table if exists MULTI_PROC_MGR;

create table MULTI_PROC_MGR (
    ID integer not null,
    REQ_ID integer not null,
    MULTI_PROC_NO integer not null,
    PROC_TARGET varchar(100) not null,
    PROC_TYPE varchar(100) not null,
    STATES varchar(100) not null,
    error_info varchar(100)
);

insert into MULTI_PROC_MGR
    values  ( 1, 1, 1, '01', '@@@', 'IN', null),
            ( 2, 1, 1, '02', '@@@', 'IN', null),
            ( 3, 1, 1, '03', '@@@', 'IN', null),
            ( 4, 1, 1, '04', '@@@', 'IN', null),
            ( 5, 1, 1, '05', '@@@', 'IN', null),
            ( 6, 1, 1, '06', '@@@', 'IN', null),
            ( 7, 1, 2, '07', '@@@', 'IN', null),
            ( 8, 1, 2, '08', '@@@', 'IN', null),
            ( 9, 1, 2, '09', '@@@', 'IN', null),
            (10, 1, 2, '10', '@@@', 'IN', null),
            (11, 1, 2, '11', '@@@', 'IN', null),
            (12, 1, 2, '12', '@@@', 'IN', null),
            (13, 1, 3, '13', '@@@', 'IN', null),
            (14, 1, 3, '14', '@@@', 'IN', null),
            (15, 1, 3, '15', '@@@', 'IN', null),
            (16, 1, 3, '16', '@@@', 'IN', null),
            (17, 1, 3, '17', '@@@', 'IN', null),
            (18, 1, 3, '18', '@@@', 'IN', null),
            (19, 1, 4, '19', '@@@', 'IN', null),
            (20, 1, 4, '20', '@@@', 'IN', null),
            (21, 1, 4, '21', '@@@', 'IN', null),
            (22, 1, 4, '22', '@@@', 'IN', null),
            (23, 1, 4, '23', '@@@', 'IN', null),
            (24, 1, 4, '24', '@@@', 'IN', null),
            (25, 1, 5, '25', '@@@', 'IN', null),
            (26, 1, 5, '26', '@@@', 'IN', null),
            (27, 1, 5, '27', '@@@', 'IN', null),
            (28, 1, 5, '28', '@@@', 'IN', null),
            (29, 1, 5, '29', '@@@', 'IN', null),
            (30, 1, 5, '30', '@@@', 'IN', null),
            (31, 1, 6, '31', '@@@', 'IN', null),
            (32, 1, 6, '32', '@@@', 'IN', null),
            (33, 1, 6, '33', '@@@', 'IN', null),
            (34, 1, 6, '34', '@@@', 'IN', null),
            (35, 1, 6, '35', '@@@', 'IN', null),
            (36, 1, 6, '36', '@@@', 'IN', null),
            (37, 1, 7, '37', '@@@', 'IN', null),
            (38, 1, 7, '38', '@@@', 'IN', null),
            (39, 1, 7, '39', '@@@', 'IN', null),
            (40, 1, 7, '40', '@@@', 'IN', null),
            (41, 1, 7, '41', '@@@', 'IN', null),
            (42, 1, 7, '42', '@@@', 'IN', null),
            (43, 1, 8, '43', '@@@', 'IN', null),
            (44, 1, 8, '44', '@@@', 'IN', null),
            (45, 1, 8, '45', '@@@', 'IN', null),
            (46, 1, 8, '46', '@@@', 'IN', null),
            (47, 1, 8, '47', '@@@', 'IN', null);
