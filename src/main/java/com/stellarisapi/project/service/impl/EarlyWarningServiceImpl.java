package com.stellarisapi.project.service.impl;

import cn.hutool.core.io.resource.ResourceUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stellaris.stellarisapicommon.common.ErrorCode;
import com.stellaris.stellarisapicommon.exception.BusinessException;
import com.stellaris.stellarisapicommon.model.entity.UserInterfaceInfo;
import com.stellarisapi.project.mapper.EarlyWarningMapper;
import com.stellarisapi.project.model.entity.EarlyWarning;
import com.stellarisapi.project.service.EarlyWarningService;
import com.stellarisapi.project.service.UserInterfaceInfoService;
import com.stellarisapi.project.utils.MailUtils;
import com.stellarisapi.project.utils.SensitiveWordUtil;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author zeroc
* @description 针对表【early_warning(接口预警记录表)】的数据库操作Service实现
* @createDate 2024-04-16 23:10:54
*/
@Service
public class EarlyWarningServiceImpl extends ServiceImpl<EarlyWarningMapper, EarlyWarning>
    implements EarlyWarningService{

    @PostConstruct
    void initialize() {
        String warring = ResourceUtil.readUtf8Str("warring.html");
        this.setStrHtmlWarning(warring);
    }

    private String strHtmlWarning;

    public String getStrHtmlWarning() {
        return strHtmlWarning;
    }

    public void setStrHtmlWarning(String strHtmlWarning) {
        this.strHtmlWarning = strHtmlWarning;
    }

    @Resource
    private EarlyWarningMapper earlyWarningMapper;

    @Resource
    private MailUtils mailUtils;

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

//    uii.userId,u.userName,u.userMailbox,ii.name

    @Override
    public void sendWarningMessage() {
        List<Map<String, Object>> warningInformation = earlyWarningMapper.getWarningInformation();
        for (Map<String, Object> stringStringMap : warningInformation) {
            String userName = stringStringMap.get("userName").toString();
            String userMailbox = stringStringMap.get("userMailbox").toString();
            String name = stringStringMap.get("name").toString();
            String userId = stringStringMap.get("userId").toString();

            String warningHtml = getStrHtmlWarning().replace("[用户姓名]", userName).replace("[接口名称]", name);
            mailUtils.sendMail(userMailbox,"接口调用额度不足提醒",warningHtml);
            EarlyWarning earlyWarning = new EarlyWarning();
            earlyWarning.setUserId(userId);
            earlyWarning.setUserMailbox(userMailbox);
            earlyWarning.setWarningText(warningHtml);
            earlyWarning.setCreateUserId("0");
            earlyWarning.setSendingStatus("0");
            earlyWarningMapper.insert(earlyWarning);
            UpdateWrapper<UserInterfaceInfo> userInterfaceInfoUpdateWrapper = new UpdateWrapper<>();
            UpdateWrapper<UserInterfaceInfo> qw = userInterfaceInfoUpdateWrapper.eq("userId", userId)
                    .eq("interfaceInfoId", stringStringMap.get("interfaceInfoId"));
            qw.set("status",1);
            userInterfaceInfoService.update(qw);
        }
    }

    @Override
    public void validEarlyWarning(EarlyWarning earlyWarning, boolean b) {
        String warningText = earlyWarning.getWarningText();
        Map<String, Integer> stringIntegerMap = SensitiveWordUtil.matchWords(warningText);
        if (stringIntegerMap.size() > 0) {
            throw new BusinessException(ErrorCode.PARAMS_SENSITIVE_ERROR);
        }
    }
}




