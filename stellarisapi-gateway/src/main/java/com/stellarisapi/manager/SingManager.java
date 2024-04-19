package com.stellarisapi.manager;

import cn.hutool.crypto.digest.DigestUtil;
import org.apache.catalina.security.SecurityUtil;

public class SingManager {
    public static String genSign(String nonce, String timestamp, String body, String accessKey, String secretKey){
        String sgin = accessKey + nonce + timestamp  + body  +  secretKey;
        return DigestUtil.md5Hex(sgin);
    }
}
