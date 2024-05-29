package com.stellarisapi.stellarisapigateway;


import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.alibaba.nacos.shaded.com.google.errorprone.annotations.Var;
//import com.stellaris.manager.RedisLimiterManager;
import com.stellaris.stellarisapicommon.model.entity.InterfaceInfo;
import com.stellaris.stellarisapicommon.model.entity.User;
import com.stellaris.stellarisapicommon.service.InnerInterfaceInfoService;
import com.stellaris.stellarisapicommon.service.InnerUserInterfaceInfoService;
import com.stellaris.stellarisapicommon.service.InnerUserService;
import com.stellarisapi.adapter.AdapterRegistry;
import com.stellarisapi.adapter.ParameterAdapter;
import com.stellarisapi.manager.RedisLimiterManager;
import com.stellarisapi.manager.RedisManager;
import com.stellarisapi.manager.SingManager;
import com.stellarisapi.model.dto.RequestAdapterDTO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;


/**
 * 全局过滤
 */
@Slf4j
@Data
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    @DubboReference
    private InnerUserService innerUserService;

    @DubboReference
    private InnerInterfaceInfoService innerInterfaceInfoService;

    @DubboReference
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;

    @Resource
    private RedisManager redisManager;

    @Resource
    private AdapterRegistry adapterRegistry;

    @Resource
    private RedisLimiterManager redisLimiterManager;

    @PostConstruct
    void initialize() {
        System.out.println("加載測試1");
    }

    private static final List<String> IP_WHITE_LIST = Arrays.asList("127.0.0.1");

    private static final String INTERFACE_HOST = "http://localhost:8090";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1. 请求日志
        ServerHttpRequest request = exchange.getRequest();
        String path = INTERFACE_HOST + request.getPath().value();
        String method = request.getMethod().toString();
        log.info("请求唯一标识：" + request.getId());
        log.info("请求路径：" + path);
        log.info("请求方法：" + method);
        log.info("请求参数：" + request.getQueryParams());
        String sourceAddress = request.getLocalAddress().getHostString();
        log.info("请求来源地址：" + sourceAddress);
        log.info("请求来源地址：" + request.getRemoteAddress());
        ServerHttpResponse response = exchange.getResponse();
        RedisLimiterManager.RateLimiterKeyInfo rateLimiterKeyInfo = new RedisLimiterManager.RateLimiterKeyInfo();
        rateLimiterKeyInfo.setUrl(path);
//        rateLimiterKeyInfo.setUserSign(sourceAddress);
        rateLimiterKeyInfo.setRequestMethod(method);
        rateLimiterKeyInfo.setScene(1);
        redisLimiterManager.interceptionAndCurrentLimiting(rateLimiterKeyInfo);
        // 将 Flux<DataBuffer> 转换为字节数组
        AtomicReference<String> bodyStringRef = new AtomicReference<>();

        Flux<DataBuffer> body = exchange.getRequest().getBody();
        body.subscribe(buffer -> {
            byte[] bytes = new byte[buffer.readableByteCount()];
            buffer.read(bytes);
            DataBufferUtils.release(buffer);
            String bodyString;
            try {
                bodyString = new String(bytes, StandardCharsets.UTF_8);
                bodyStringRef.set(bodyString);
                System.out.println(bodyString);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        String bodyString = bodyStringRef.get();
        log.info("请求参数：" + bodyString);
        // 2. 访问控制 - 黑白名单(后期可以做一个表记录每一个接口的白名单)
//        if (!IP_WHITE_LIST.contains(sourceAddress)) {
//            response.setStatusCode(HttpStatus.FORBIDDEN);
//            return response.setComplete();
//        }
        // 3. 用户鉴权（判断 ak、sk 是否合法）
        HttpHeaders headers = request.getHeaders();
        String accessKey = headers.getFirst("accessKey");
        String nonce = headers.getFirst("nonce");
        String timestamp = headers.getFirst("timestamp");
        String sign = headers.getFirst("sign");
//        String body = headers.getFirst("body");

        // todo 实际情况应该是去数据库中查是否已分配给用户
        User invokeUser = null;
        try {
            invokeUser = innerUserService.getInvokeUser(accessKey);
        } catch (Exception e) {
            log.error("getInvokeUser error", e);
        }
        if (invokeUser == null) {
            return handleNoAuth(response);
        }
//        if (!" stellaris".equals(accessKey)) {
//            return handleNoAuth(response);
//        }
        if (Long.parseLong(nonce) > 10000L) {
            return handleNoAuth(response);
        }
        // 时间和当前时间不能超过 5 分钟
        Long currentTime = System.currentTimeMillis() / 1000;
        final Long FIVE_MINUTES = 60 * 5L;
        if ((currentTime - Long.parseLong(timestamp)) >= FIVE_MINUTES) {
            return handleNoAuth(response);
        }
        // 确保一个来源的随机数五分钟内唯一(长度暂定为一个mysql text 的长度)
        if (!redisManager.storeRandomNumber(sourceAddress, nonce, 65535)) {
            return handleNoAuth(response);
        }
        // 实际情况中是从数据库中查出 secretKey
        String secretKey = invokeUser.getSecretKey();
        String serverSign = SingManager.genSign(nonce, timestamp, bodyString, accessKey, secretKey);
        if (sign == null || !sign.equals(serverSign)) {
            log.error("sing 对比失败");
            return handleNoAuth(response);
        }
        // 4. 请求的模拟接口是否存在，以及请求方法是否匹配
        InterfaceInfo interfaceInfo = null;
        try {
            interfaceInfo = innerInterfaceInfoService.getInterfaceInfo(path, method);
        } catch (Exception e) {
            log.error("getInterfaceInfo error", e);
        }
        if (interfaceInfo == null) {
            return handleNoAuth(response);
        }
        if (!innerUserInterfaceInfoService.thereIsALimit(invokeUser.getId())) {
            return handleNoAuth(response);
        }
        RequestAdapterDTO requestAdapterDTO = null;
        if (interfaceInfo.getIsEncryption() == 0) {
            ParameterAdapter prameterAdapter = adapterRegistry.getDataSourceByType(interfaceInfo.getId());
            Map map = JSONUtil.toBean(bodyString, Map.class);
            requestAdapterDTO = prameterAdapter.parameterAdapter(map);
        } else {
            requestAdapterDTO = new RequestAdapterDTO();
            Map map = JSONUtil.toBean(bodyString, Map.class);
            requestAdapterDTO.setHeaders(map);
        }

        String originalUrl = interfaceInfo.getOriginalUrl();
        Map<String, String> map = requestAdapterDTO.getHeaders();

        // 在此处转换请求路径,更改请求参数 requestAdapterDTO 内的 haaders 为 map<string,string> 请纠正写法
        // 4. 构建新的请求路径和请求参数
        HttpHeaders newHeaders = new HttpHeaders();

//        System.out.println(result);
        // 将 Map<String, String> 放入 HttpHeaders 对象中
        for (Map.Entry<String, String> entry : map.entrySet()) {
            newHeaders.add(entry.getKey(), entry.getValue());
        }

        URI uri = UriComponentsBuilder.fromUriString(originalUrl).build().toUri();

        // 问题出现在这  它 originalUrl 要求 / 开头,实际上我需要访问 https://zelinai.com/biz/v1/app/chat/sync
        ServerHttpRequest newRequest = request.mutate()
                .uri(uri)
                .headers(httpHeaders -> {
                    httpHeaders.putAll(newHeaders);
                })
                .build();

        // 5. 将新的请求路径设置到 Exchange 的属性中
        return handleResponse(exchange, chain, newRequest,interfaceInfo.getId(), invokeUser.getId());
    }

    /**
     * 处理响应
     *
     * @param exchange
     * @param chain
     * @return
     */
    public Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain,ServerHttpRequest request, long interfaceInfoId, long userId) {
        try {
            ServerHttpResponse originalResponse = exchange.getResponse();
            // 缓存数据的工厂
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            // 拿到响应码
            HttpStatus statusCode = originalResponse.getStatusCode();
            if (statusCode == HttpStatus.OK) {
                // 装饰，增强能力
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
                    // 等调用完转发的接口后才会执行
                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        log.info("body instanceof Flux: {}", (body instanceof Flux));
                        if (body instanceof Flux) {
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            // 往返回值里写数据
                            // 拼接字符串
                            return super.writeWith(
                                    fluxBody.map(dataBuffer -> {
                                        // 7. 调用成功，接口调用次数 + 1 invokeCount
                                        try {
                                            innerUserInterfaceInfoService.invokeCount(interfaceInfoId, userId);
                                        } catch (Exception e) {
                                            log.error("invokeCount error", e);
                                        }
                                        byte[] content = new byte[dataBuffer.readableByteCount()];
                                        dataBuffer.read(content);
                                        DataBufferUtils.release(dataBuffer);//释放掉内存
                                        // 构建日志
                                        StringBuilder sb2 = new StringBuilder(200);
                                        List<Object> rspArgs = new ArrayList<>();
                                        rspArgs.add(originalResponse.getStatusCode());
                                        String data = new String(content, StandardCharsets.UTF_8); //data
                                        sb2.append(data);
                                        // 打印日志
                                        log.info("响应结果：" + data);
                                        return bufferFactory.wrap(content);
                                    }));
                        } else {
                            // 8. 调用失败，返回一个规范的错误码
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                // 设置 response 对象为装饰过的
                return chain.filter(exchange.mutate().request(request).response(decoratedResponse).build());
            }
            return chain.filter(exchange); // 降级处理返回数据
        } catch (Exception e) {
            log.error("网关处理响应异常" + e);
            return chain.filter(exchange);
        }
    }

//    public static void main(String[] args) {
//
//        String bodyString = "{\"app_id\":\"aXWyoVK7zPL9FY4RozUKML\",\"request_id\":\"365bb732c4be32694a726e250d5b1e83\",\"uid\":\"365bb732c4be32694a726e250d5b1e75\",\"content\":\"太阳多大\"}";
//        Map<String, Object> map = JSONUtil.parseObj(bodyString).toBean(Map.class);
//
//        // 打印转换后的 Map 对象
//        System.out.println(map);
//
//        Long l = System.currentTimeMillis();
//        Integer randomNumber2 = RandomUtil.randomInt(100);
//        System.out.println(randomNumber2);
//        System.out.println(l);
//        String serverSign = SingManager.genSign(randomNumber2.toString(), l.toString(), bodyString, "fb15d434781e1f9fa984609b5e099014", "119716dcf08daf5ee828a98423198671");
//        System.out.println(serverSign);
//    }

    public static void main(String[] args) {
//        String bodyString = "{\"app_id\":\"aXWyoVK7zPL9FY4RozUKML\",\"request_id\":\"365bb732c4be32694a726e250d5b1e83\",\"uid\":\"365bb732c4be32694a726e250d5b1e75\",\"content\":\"太阳多大\"}";

        for (int i = 0; i < 10; i++) {
            String bodyString = "{\"keyword\": \"是史昂傻逼臭傻逼打傻逼贱女煞笔东西\"}";
            Map<String, Object> map = JSONUtil.parseObj(bodyString).toBean(Map.class);

            // 打印转换后的 Map 对象
            System.out.println(map);

            Long l = System.currentTimeMillis();
            Integer randomNumber2 = RandomUtil.randomInt(100);
            System.out.println(randomNumber2);
            System.out.println(l);
            String serverSign = SingManager.genSign(randomNumber2.toString(), l.toString(), bodyString, "fb15d434781e1f9fa984609b5e099014", "119716dcf08daf5ee828a98423198671");
            System.out.println(serverSign);

            String body = HttpRequest.post("http://localhost:8090/api/wmsensitiveInfo/check")
                    .header("accessKey", "fb15d434781e1f9fa984609b5e099014")
                    .header("nonce", randomNumber2.toString())
                    .header("timestamp", String.valueOf(l))
                    .header("sign", serverSign)
                    .body("{\"keyword\": \"是史昂傻逼臭傻逼打傻逼贱女煞笔东西\"}", "application/json")
                    .execute()
                    .body();
            System.out.println(body);
        }
    }

    @Override
    public int getOrder() {
        return -1;
    }

    public Mono<Void> handleNoAuth(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

    public Mono<Void> handleInvokeError(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return response.setComplete();
    }
}