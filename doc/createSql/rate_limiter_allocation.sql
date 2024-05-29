create table rate_limiter_allocation
(
    id               bigint auto_increment comment 'id'
        primary key,
    roadSigns        varchar(256)                       not null comment 'name–速率限制器路标',
    rate             bigint                             not null comment '率',
    rateInterval     bigint                             null comment '速率时间间隔',
    rateIntervalUnit varchar(256)                       null comment '速率时间间隔单位',
    createUserId     varchar(256)                       null comment '创建用户 id',
    createUserName   varchar(256)                       null comment '创建用户名',
    scene            int                                null comment '限流场景(1.路径 2.用户)',
    remark           varchar(1024)                      null comment '备注',
    state            varchar(128)                       null comment '状态值,可枚举(1.正常 3.拥堵 2.繁忙 4.超负载)',
    usageStatus      int      default 0                 null comment '使用状态(0.启用 1.禁用)',
    createTime       datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime       datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete         tinyint  default 0                 not null comment '是否删除'
)
    comment '限流配置' collate = utf8mb4_unicode_ci;

create index idx_userAccount
    on rate_limiter_allocation (roadSigns);

INSERT INTO yidiansishiyi.rate_limiter_allocation (id, roadSigns, rate, rateInterval, rateIntervalUnit, createUserId, createUserName, scene, remark, state, usageStatus, createTime, updateTime, isDelete) VALUES (1, 'http://localhost:8090/api/wmsensitiveInfo/check', 1, 1, 'MINUTES', '1', 'sanqi', 1, '铭感词检测过滤', '1', 0, '2024-05-26 13:42:46', '2024-05-27 20:57:35', 0);
