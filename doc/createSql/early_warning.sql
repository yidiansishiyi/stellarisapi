create table early_warning
(
    id            bigint auto_increment comment 'id'
        primary key,
    userId        varchar(256)                       not null comment '用户ID',
    userMailbox   varchar(512)                       not null comment '用户邮箱',
    warningText   text                               null comment '预警文本',
    sendingStatus varchar(1024)                      null comment '发送状态,0 成功 1 失败',
    createTime    datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime    datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete      tinyint  default 0                 not null comment '是否删除',
    createUserId  bigint                             null
)
    comment '接口预警记录表' collate = utf8mb4_unicode_ci;

INSERT INTO yidiansishiyi.early_warning (id, userId, userMailbox, warningText, sendingStatus, createTime, updateTime, isDelete, createUserId) VALUES (1, '1685290200697200642', '1537617893@qq.com', '<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>欠费通知</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            background-image: url(\'background-image-url.jpg\'); /* 替换为实际图片的 URL */
            background-size: cover;
            background-position: center;
            font-family: Arial, sans-serif;
        }
        .container {
            max-width: 600px;
            margin: 20px auto;
            padding: 20px;
            background-color: rgba(255, 255, 255, 0.8); /* 透明度设置 */
            border-radius: 10px;
        }
        .header {
            color: #333;
            text-align: center;
            margin-bottom: 20px;
        }
        .content {
            color: #666;
            margin-top: 20px;
            line-height: 1.6;
        }
        .highlight {
            color: #ff6600;
            font-weight: bold;
        }
        .button {
            display: inline-block;
            padding: 10px 20px;
            background-color: #ff6600;
            color: #fff;
            text-decoration: none;
            border-radius: 5px;
        }
        .footer {
            margin-top: 30px;
            text-align: center;
            color: #999;
            font-size: 14px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h2>尊敬的用户 <span class="highlight">oj 测试账号</span>，您好！</h2>
        </div>
        <div class="content">
            <p>    您在 stellaris 开放平台订阅的 <span class="highlight">图片存储</span> 调用量已不足。如需继续使用,请联系管理员以免影响您的正常使用。</p>
            <p>我们非常感谢您一直以来对我们的支持与信任。</p>
            <p>如有任何疑问或需要帮助，请随时联系我们的管理员。</p>
                                                    <p>一点四十一</p>
            <p>祝您一切顺利！</p>
        </div>
        <div class="footer">
            <p>此邮件为系统自动发送，请勿回复。</p>
        </div>
    </div>
</body>
</html>
', '0', '2024-04-17 22:53:18', '2024-04-17 22:53:18', 0, 0);
INSERT INTO yidiansishiyi.early_warning (id, userId, userMailbox, warningText, sendingStatus, createTime, updateTime, isDelete, createUserId) VALUES (2, '1685290200697200642', '1537617893@qq.com', '<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>欠费通知</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            background-image: url(\'background-image-url.jpg\'); /* 替换为实际图片的 URL */
            background-size: cover;
            background-position: center;
            font-family: Arial, sans-serif;
        }
        .container {
            max-width: 600px;
            margin: 20px auto;
            padding: 20px;
            background-color: rgba(255, 255, 255, 0.8); /* 透明度设置 */
            border-radius: 10px;
        }
        .header {
            color: #333;
            text-align: center;
            margin-bottom: 20px;
        }
        .content {
            color: #666;
            margin-top: 20px;
            line-height: 1.6;
        }
        .highlight {
            color: #ff6600;
            font-weight: bold;
        }
        .button {
            display: inline-block;
            padding: 10px 20px;
            background-color: #ff6600;
            color: #fff;
            text-decoration: none;
            border-radius: 5px;
        }
        .footer {
            margin-top: 30px;
            text-align: center;
            color: #999;
            font-size: 14px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h2>尊敬的用户 <span class="highlight">oj 测试账号</span>，您好！</h2>
        </div>
        <div class="content">
            <p>    您在 stellaris 开放平台订阅的 <span class="highlight">ai 数据可视化分析</span> 调用量已不足。如需继续使用,请联系管理员以免影响您的正常使用。</p>
            <p>我们非常感谢您一直以来对我们的支持与信任。</p>
            <p>如有任何疑问或需要帮助，请随时联系我们的管理员。</p>
                                                    <p>一点四十一</p>
            <p>祝您一切顺利！</p>
        </div>
        <div class="footer">
            <p>此邮件为系统自动发送，请勿回复。</p>
        </div>
    </div>
</body>
</html>
', '0', '2024-05-18 10:21:47', '2024-05-18 10:21:47', 0, 0);
INSERT INTO yidiansishiyi.early_warning (id, userId, userMailbox, warningText, sendingStatus, createTime, updateTime, isDelete, createUserId) VALUES (3, '1672600130722787330', 'nuomibaicha@sina.com', '<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>欠费通知</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            background-image: url(\'background-image-url.jpg\'); /* 替换为实际图片的 URL */
            background-size: cover;
            background-position: center;
            font-family: Arial, sans-serif;
        }
        .container {
            max-width: 600px;
            margin: 20px auto;
            padding: 20px;
            background-color: rgba(255, 255, 255, 0.8); /* 透明度设置 */
            border-radius: 10px;
        }
        .header {
            color: #333;
            text-align: center;
            margin-bottom: 20px;
        }
        .content {
            color: #666;
            margin-top: 20px;
            line-height: 1.6;
        }
        .highlight {
            color: #ff6600;
            font-weight: bold;
        }
        .button {
            display: inline-block;
            padding: 10px 20px;
            background-color: #ff6600;
            color: #fff;
            text-decoration: none;
            border-radius: 5px;
        }
        .footer {
            margin-top: 30px;
            text-align: center;
            color: #999;
            font-size: 14px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h2>尊敬的用户 <span class="highlight">三七</span>，您好！</h2>
        </div>
        <div class="content">
            <p>    您在 stellaris 开放平台订阅的 <span class="highlight">聊天接口API </span> 调用量已不足。如需继续使用,请联系管理员以免影响您的正常使用。</p>
            <p>我们非常感谢您一直以来对我们的支持与信任。</p>
            <p>如有任何疑问或需要帮助，请随时联系我们的管理员。</p>
                                                    <p>一点四十一</p>
            <p>祝您一切顺利！</p>
        </div>
        <div class="footer">
            <p>此邮件为系统自动发送，请勿回复。</p>
        </div>
    </div>
</body>
</html>
', '0', '2024-05-18 10:24:35', '2024-05-18 10:24:35', 0, 0);
INSERT INTO yidiansishiyi.early_warning (id, userId, userMailbox, warningText, sendingStatus, createTime, updateTime, isDelete, createUserId) VALUES (4, '1672600130722787330', 'nuomibaicha@sina.com', '<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>欠费通知</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            background-image: url(\'background-image-url.jpg\'); /* 替换为实际图片的 URL */
            background-size: cover;
            background-position: center;
            font-family: Arial, sans-serif;
        }
        .container {
            max-width: 600px;
            margin: 20px auto;
            padding: 20px;
            background-color: rgba(255, 255, 255, 0.8); /* 透明度设置 */
            border-radius: 10px;
        }
        .header {
            color: #333;
            text-align: center;
            margin-bottom: 20px;
        }
        .content {
            color: #666;
            margin-top: 20px;
            line-height: 1.6;
        }
        .highlight {
            color: #ff6600;
            font-weight: bold;
        }
        .button {
            display: inline-block;
            padding: 10px 20px;
            background-color: #ff6600;
            color: #fff;
            text-decoration: none;
            border-radius: 5px;
        }
        .footer {
            margin-top: 30px;
            text-align: center;
            color: #999;
            font-size: 14px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h2>尊敬的用户 <span class="highlight">三七</span>，您好！</h2>
        </div>
        <div class="content">
            <p>    您在 stellaris 开放平台订阅的 <span class="highlight">铭感词频率检测</span> 调用量已不足。如需继续使用,请联系管理员以免影响您的正常使用。</p>
            <p>我们非常感谢您一直以来对我们的支持与信任。</p>
            <p>如有任何疑问或需要帮助，请随时联系我们的管理员。</p>
                                                    <p>一点四十一</p>
            <p>祝您一切顺利！</p>
        </div>
        <div class="footer">
            <p>此邮件为系统自动发送，请勿回复。</p>
        </div>
    </div>
</body>
</html>
', '0', '2024-05-26 23:01:28', '2024-05-26 23:01:28', 0, 0);
