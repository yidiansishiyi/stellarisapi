create table user
(
    id           bigint auto_increment comment 'id'
        primary key,
    userAccount  varchar(256)                           not null comment '账号',
    userPassword varchar(512)                           not null comment '密码',
    userName     varchar(256)                           null comment '用户昵称',
    userAvatar   varchar(1024)                          null comment '用户头像',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user/admin',
    userMailbox  varchar(40)                            null comment '邮箱',
    tags         varchar(1024)                          null comment '标签 json 列表',
    accessKey    varchar(512)                           null comment 'accessKey',
    secretKey    varchar(512)                           null comment 'secretKey',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除'
)
    comment '用户' collate = utf8mb4_unicode_ci;

create index idx_userAccount
    on user (userAccount);

INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1672600130722787330, 'sanqi', 'b03d227f78c0c79334fca76e7b8eb46a', '三七', 'https://static-test.perfectdate.cn/2023/07/25/72c04f32-5ed0-41a6-8cec-cb7b2a8b3b28.webp', 'admin', 'nuomibaicha@sina.com', '["java","张三"]', 'fb15d434781e1f9fa984609b5e099014', '119716dcf08daf5ee828a98423198671', '2023-06-24 21:38:40', '2023-09-29 21:32:57', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1672600130722787331, 'momo', 'b03d227f78c0c79334fca76e7b8eb46a', '蓦蓦', 'https://static-test.perfectdate.cn/2023/07/25/72c04f32-5ed0-41a6-8cec-cb7b2a8b3b28.webp', 'admin', '1581649883@qq.com', '["java","张三","男"]', '898aea05129d3d95db03de23162fb4f5', 'f273b2ad64ade1a875e21bb97cb45332', '2023-07-05 20:13:32', '2024-05-17 21:44:36', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1672600130722787332, 'shijiahao', 'b03d227f78c0c79334fca76e7b8eb46a', '豪哥', 'https://static-test.perfectdate.cn/2023/07/25/72c04f32-5ed0-41a6-8cec-cb7b2a8b3b28.webp', 'user', 'startby2016218@gmail.com', null, 'f458c7a65c5aa89c5b34bb76df32ca45', 'bc995854c2ed667a52200846716e65f2', '2023-07-05 23:01:03', '2023-09-29 21:32:57', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1672600130722787333, 'shaonian', 'b03d227f78c0c79334fca76e7b8eb46a', '少年', 'https://static-test.perfectdate.cn/2023/07/25/72c04f32-5ed0-41a6-8cec-cb7b2a8b3b28.webp', 'user', '1328032322@qq.com', null, '006b1786b20510bb4ae5870517985fae', '359d6b37ad72fa2733515d33361d5bf6', '2023-07-05 23:04:03', '2023-09-29 21:32:57', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1685290200697200642, 'gata', '0acfb933390ae00265438f342be22e51', 'oj 测试账号', 'https://static-test.perfectdate.cn/2023/07/25/72c04f32-5ed0-41a6-8cec-cb7b2a8b3b28.webp', 'admin', '1537617893@qq.com', null, 'd184f0e56917a1613679bc918bb4dfb8', 'a18ea46e9902c764d2e4e8ca4b9a8bfa', '2023-07-29 22:04:30', '2024-04-17 01:09:22', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1707740385859612674, '张三是我兄弟', '0acfb933390ae00265438f342be22e51', null, null, 'user', null, null, '2692e1c9c5ac221a9d831fcccd0702d9', '2f3a410224bb21e4b9d3ebdcd03a8eb8', '2023-09-29 20:53:33', '2023-09-29 21:32:57', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565035839496, 'test', 'password', '测试回滚46', 'zzzz', 'user', null, null, null, null, '2023-10-27 16:40:28', '2023-10-27 16:40:28', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565035839497, 'test', 'password', '测试回滚47', 'zzzz', 'user', null, null, null, null, '2023-10-27 16:40:28', '2023-10-27 16:40:28', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565035839498, 'test', 'password', '测试回滚48', 'zzzz', 'user', null, null, null, null, '2023-10-27 16:40:28', '2023-10-27 16:40:28', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565035839499, 'test', 'password', '测试回滚49', 'zzzz', 'user', null, null, null, null, '2023-10-27 16:40:28', '2023-10-27 16:40:28', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565035839500, 'test', 'password', '测试回滚50', 'zzzz', 'user', null, null, null, null, '2023-10-27 16:40:28', '2023-10-27 16:40:28', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565035839501, 'test', 'password', '测试回滚51', 'zzzz', 'user', null, null, null, null, '2023-10-27 16:40:28', '2023-10-27 16:40:28', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565035839502, 'test', 'password', '测试回滚52', 'zzzz', 'user', null, null, null, null, '2023-10-27 16:40:28', '2023-10-27 16:40:28', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565035839503, 'test', 'password', '测试回滚53', 'zzzz', 'user', null, null, null, null, '2023-10-27 16:40:28', '2023-10-27 16:40:28', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565035839504, 'test', 'password', '测试回滚54', 'zzzz', 'user', null, null, null, null, '2023-10-27 16:40:28', '2023-10-27 16:40:28', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565035839505, 'test', 'password', '测试回滚55', 'zzzz', 'user', null, null, null, null, '2023-10-27 16:40:28', '2023-10-27 16:40:28', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565035839506, 'test', 'password', '测试回滚56', 'zzzz', 'user', null, null, null, null, '2023-10-27 16:40:28', '2023-10-27 16:40:28', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565035839507, 'test', 'password', '测试回滚57', 'zzzz', 'user', null, null, null, null, '2023-10-27 16:40:28', '2023-10-27 16:40:28', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565035839508, 'test', 'password', '测试回滚58', 'zzzz', 'user', null, null, null, null, '2023-10-27 16:40:28', '2023-10-27 16:40:28', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565035839509, 'test', 'password', '测试回滚59', 'zzzz', 'user', null, null, null, null, '2023-10-27 16:40:28', '2023-10-27 16:40:28', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565073588226, 'test', 'password', '测试回滚60', 'zzzz', 'user', null, null, null, null, '2023-10-27 16:40:28', '2023-10-27 16:40:28', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565073588227, 'test', 'password', '测试回滚61', 'zzzz', 'user', null, null, null, null, '2023-10-27 16:40:28', '2023-10-27 16:40:28', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565073588228, 'test', 'password', '测试回滚62', 'zzzz', 'user', null, null, null, null, '2023-10-27 16:40:28', '2023-10-27 16:40:28', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565073588229, 'test', 'password', '测试回滚63', 'zzzz', 'user', null, null, null, null, '2023-10-27 16:40:28', '2023-10-27 16:40:28', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565073588230, 'test', 'password', '测试回滚64', 'zzzz', 'user', null, null, null, null, '2023-10-27 16:40:28', '2023-10-27 16:40:28', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565073588231, 'test', 'password', '测试回滚65', 'zzzz', 'user', null, null, null, null, '2023-10-27 16:40:28', '2023-10-27 16:40:28', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565073588232, 'test', 'password', '测试回滚66', 'zzzz', 'user', null, null, null, null, '2023-10-27 16:40:28', '2023-10-27 16:40:28', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565073588233, 'test', 'password', '测试回滚67', 'zzzz', 'user', null, null, null, null, '2023-10-27 16:40:28', '2023-10-27 16:40:28', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565073588234, 'test', 'password', '测试回滚68', 'zzzz', 'user', null, null, null, null, '2023-10-27 16:40:28', '2023-10-27 16:40:28', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565073588235, 'test', 'password', '测试回滚69', 'zzzz', 'user', null, null, null, null, '2023-10-27 16:40:28', '2023-10-27 16:40:28', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565073588236, 'test', 'password', '测试回滚70', 'zzzz', 'user', null, null, null, null, '2023-10-27 16:40:28', '2023-10-27 16:40:28', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565073588237, 'test', 'password', '测试回滚71', 'zzzz', 'user', null, null, null, null, '2023-10-27 16:40:28', '2023-10-27 16:40:28', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565073588238, 'test', 'password', '测试回滚72', 'zzzz', 'user', null, null, null, null, '2023-10-27 16:40:28', '2023-10-27 16:40:28', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565073588239, 'test', 'password', '测试回滚73', 'zzzz', 'user', null, null, null, null, '2023-10-27 16:40:28', '2023-10-27 16:40:28', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565073588240, 'test', 'password', '测试回滚74', 'zzzz', 'user', null, null, null, null, '2023-10-27 16:40:28', '2023-10-27 16:40:28', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565073588241, 'test', 'password', '测试回滚75', 'zzzz', 'user', null, null, null, null, '2023-10-27 16:40:28', '2023-10-27 16:40:28', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565073588242, 'test', 'password', '测试回滚76', 'zzzz', 'user', null, null, null, null, '2023-10-27 16:40:28', '2023-10-27 16:40:28', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565073588243, 'test', 'password', '测试回滚77', 'zzzz', 'user', null, null, null, null, '2023-10-27 16:40:28', '2023-10-27 16:40:28', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565073588244, 'test', 'password', '测试回滚78', 'zzzz', 'user', null, null, null, null, '2023-10-27 16:40:28', '2023-10-27 16:40:28', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565073588245, 'test', 'password', '测试回滚79', 'zzzz', 'user', null, null, null, null, '2023-10-27 16:40:28', '2023-10-27 16:40:28', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565073588246, 'test', 'password', '测试回滚80', 'zzzz', 'user', null, null, null, null, '2023-10-27 16:40:28', '2023-10-27 16:40:28', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565073588247, 'test', 'password', '测试回滚81', 'zzzz', 'user', null, null, null, null, '2023-10-27 16:40:28', '2023-10-27 16:40:28', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565073588248, 'test', 'password', '测试回滚82', 'zzzz', 'user', null, null, null, null, '2023-10-27 16:40:28', '2023-10-27 16:40:28', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565073588249, 'test', 'password', '测试回滚83', 'zzzz', 'user', null, null, null, null, '2023-10-27 16:40:28', '2023-10-27 16:40:28', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565073588250, 'test', 'password', '测试回滚84', 'zzzz', 'user', null, null, null, null, '2023-10-27 16:40:28', '2023-10-27 16:40:28', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565073588251, 'test', 'password', '测试回滚85', 'zzzz', 'user', null, null, null, null, '2023-10-27 16:40:28', '2023-10-27 16:40:28', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565073588252, 'test', 'password', '测试回滚86', 'zzzz', 'user', null, null, null, null, '2023-10-27 16:40:28', '2023-10-27 16:40:28', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565073588253, 'test', 'password', '测试回滚87', 'zzzz', 'user', null, null, null, null, '2023-10-27 16:40:28', '2023-10-27 16:40:28', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565073588254, 'test', 'password', '测试回滚88', 'zzzz', 'user', null, null, null, null, '2023-10-27 16:40:28', '2023-10-27 16:40:28', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565073588255, 'test', 'password', '测试回滚89', 'zzzz', 'user', null, null, null, null, '2023-10-27 16:40:28', '2023-10-27 16:40:28', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565136502786, 'test', 'password', '测试回滚90', 'zzzz', 'user', null, null, null, null, '2023-10-27 16:40:28', '2023-10-27 16:40:28', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565136502787, 'test', 'password', '测试回滚91', 'zzzz', 'user', null, null, null, null, '2023-10-27 16:40:28', '2023-10-27 16:40:28', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565136502788, 'test', 'password', '测试回滚92', 'zzzz', 'user', null, null, null, null, '2023-10-27 16:40:28', '2023-10-27 16:40:28', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565136502789, 'test', 'password', '测试回滚93', 'zzzz', 'user', null, null, null, null, '2023-10-27 16:40:28', '2023-10-27 16:40:28', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565136502790, 'test', 'password', '测试回滚94', 'zzzz', 'user', null, null, null, null, '2023-10-27 16:40:28', '2023-10-27 16:40:28', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565136502791, 'test', 'password', '测试回滚95', 'zzzz', 'user', null, null, null, null, '2023-10-27 16:40:28', '2023-10-27 16:40:28', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565136502792, 'test', 'password', '测试回滚96', 'zzzz', 'user', null, null, null, null, '2023-10-27 16:40:28', '2023-10-27 16:40:28', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565136502793, 'test', 'password', '测试回滚97', 'zzzz', 'user', null, null, null, null, '2023-10-27 16:40:28', '2023-10-27 16:40:28', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565136502794, 'test', 'password', '测试回滚98', 'zzzz', 'user', null, null, null, null, '2023-10-27 16:40:28', '2023-10-27 16:40:28', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565136502795, 'test', 'password', '测试回滚99', 'zzzz', 'user', null, null, null, null, '2023-10-27 16:40:28', '2023-10-27 16:40:28', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565136502796, 'root', '0acfb933390ae00265438f342be22e51', null, null, 'admin', null, null, '668b7f155b0b440188f6b60f60da4b27', '92a18663e470fdaf341fcc5a2f92bbd4', '2024-04-08 23:38:57', '2024-05-17 21:44:36', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565136502797, '123456789', '0acfb933390ae00265438f342be22e51', null, null, 'user', null, null, 'd43c187aa496450d58cc2926d8f1929f', 'c989052bec9c672fc8c0f74ca5720ede', '2024-05-09 21:07:07', '2024-05-09 21:07:07', 0);
INSERT INTO yidiansishiyi.user (id, userAccount, userPassword, userName, userAvatar, userRole, userMailbox, tags, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1717823565136502798, 'zsddda', '0acfb933390ae00265438f342be22e51', null, null, 'user', null, null, 'fa3dbc8fc6a5999c88fb123943614687', 'fc16369ec32f1e3ec5e9e2337f706747', '2024-05-09 21:15:24', '2024-05-09 21:15:24', 0);
