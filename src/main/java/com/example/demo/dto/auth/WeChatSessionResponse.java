package com.example.demo.dto.auth;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class WeChatSessionResponse {
	private String sessionKey;
	private String unionid;
	private String errmsg;
	private String openid;
	private Integer errcode;
}
