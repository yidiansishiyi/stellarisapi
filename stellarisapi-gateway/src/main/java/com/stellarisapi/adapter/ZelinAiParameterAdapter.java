package com.stellarisapi.adapter;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import com.stellarisapi.model.dto.RequestAdapterDTO;
import org.springframework.stereotype.Component;


import java.util.*;

@Component
public class ZelinAiParameterAdapter implements ParameterAdapter{

    private String appkey = "c9bac52057094ec08a0cd96af0d2b664";
    private String appsecret = "dd4c5ee16c7f41888f3a633203f93e52";

    @Override
    public RequestAdapterDTO parameterAdapter(Map<String, Object> originalParameters) {
        Map<String, String> adaptedParameters = new HashMap<>();

        // 遍历原始参数，将值转换为字符串类型，并放入适配后的Map中
        for (Map.Entry<String, Object> entry : originalParameters.entrySet()) {
            adaptedParameters.put(entry.getKey(), entry.getValue().toString());
        }

        // 添加额外的参数
        adaptedParameters.put("appkey", appkey);
        String nonce= RandomUtil.randomNumbers(16);
        adaptedParameters.put("nonce", nonce);
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        adaptedParameters.put("timestamp", timestamp);
        adaptedParameters.put("signature", generateNonce(adaptedParameters, appsecret));
        adaptedParameters.put("Content-Type", "application/json;charset=UTF-8");

        return new RequestAdapterDTO(adaptedParameters);
    }

    public static String generateNonce(Map<String,String> body, String appsecret) {
        List<String> sortedKeys = new ArrayList<>(body.keySet());
        Collections.sort(sortedKeys);
        // 拼接键值对成字符串s1
        StringBuilder s1Builder = new StringBuilder();
        for (String key : sortedKeys) {
            String value = body.get(key).toString();
            if (value != null && !value.isEmpty()) {
                if (s1Builder.length() > 0) {
                    s1Builder.append("&");
                }
                s1Builder.append(key).append("=").append(value);
            }
        }
        String s1 = s1Builder.toString();
        String s2 = s1 + appsecret;
        String md5Sign = SecureUtil.md5(s2.toString());
        return md5Sign.toLowerCase();
    }
}
