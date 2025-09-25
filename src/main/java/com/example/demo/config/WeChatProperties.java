package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
/**
 * 微信配置属性类
 * 前缀为 wechat，用于绑定 application.properties 中以 wechat 开头的配置属性
 */
@ConfigurationProperties(prefix = "wechat")
public class WeChatProperties {
    private String appid;
    private String secret;
    private String grantType;
    private String codeToSessionUrl;

    public String getAppid() { return appid; }
    public void setAppid(String appid) { this.appid = appid; }

    public String getSecret() { return secret; }
    public void setSecret(String secret) { this.secret = secret; }

    public String getGrantType() { return grantType; }
    public void setGrantType(String grantType) { this.grantType = grantType; }

    public String getCodeToSessionUrl() { return codeToSessionUrl; }
    public void setCodeToSessionUrl(String codeToSessionUrl) { this.codeToSessionUrl = codeToSessionUrl; }
}