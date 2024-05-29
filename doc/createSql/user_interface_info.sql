create table user_interface_info
(
    id              bigint auto_increment comment '主键'
        primary key,
    userId          bigint                             not null comment '调用用户 id',
    interfaceInfoId bigint                             not null comment '接口 id',
    totalNum        int      default 0                 not null comment '总调用次数',
    leftNum         int      default 0                 not null comment '剩余调用次数',
    status          int      default 0                 not null comment '0-正常，1-禁用',
    createTime      datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime      datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete        tinyint  default 0                 not null comment '是否删除(0-未删, 1-已删)'
)
    comment '用户调用接口关系' collate = utf8mb4_general_ci;

INSERT INTO yidiansishiyi.user_interface_info (id, userId, interfaceInfoId, totalNum, leftNum, status, createTime, updateTime, isDelete) VALUES (1, 1685290200697200642, 1695848994195935234, 100, 0, 1, '2023-09-30 10:24:02', '2024-04-20 23:11:55', 0);
INSERT INTO yidiansishiyi.user_interface_info (id, userId, interfaceInfoId, totalNum, leftNum, status, createTime, updateTime, isDelete) VALUES (2, 1685290200697200642, 1695848994195935235, 100, 0, 1, '2023-09-30 10:27:29', '2024-05-18 10:23:05', 0);
INSERT INTO yidiansishiyi.user_interface_info (id, userId, interfaceInfoId, totalNum, leftNum, status, createTime, updateTime, isDelete) VALUES (3, 1672600130722787330, 1695848994195935237, 102, 100, 1, '2023-09-30 14:52:53', '2024-05-26 21:13:25', 0);
INSERT INTO yidiansishiyi.user_interface_info (id, userId, interfaceInfoId, totalNum, leftNum, status, createTime, updateTime, isDelete) VALUES (4, 1717823565136502796, 1695848994195935237, 110, 100, 0, '2024-05-17 22:01:45', '2024-05-26 21:13:25', 0);
INSERT INTO yidiansishiyi.user_interface_info (id, userId, interfaceInfoId, totalNum, leftNum, status, createTime, updateTime, isDelete) VALUES (5, 1672600130722787330, 1695848994195935238, 210, 0, 1, '2024-05-17 22:01:45', '2024-05-26 23:01:28', 0);
