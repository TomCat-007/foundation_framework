package common.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhangguiyuan
 * @description
 * @date 2023/3/3 13:33
 */
@Data
@ApiModel
public class DefaultLoginRequest implements Serializable {
//
//    public static final String LOGIN_TYPE_PARAMETER = "authentication.loginType";
//
//    public static final String AUTHENTICATION_SOURCE = "authentication.authenticationSource";
//
//    public static final String AUTHENTICATION_SCENE = "authentication.authenticationScene";

    @ApiModelProperty(value = "登录名", required = true, example = "PHL0YwLkdl9uj4a0e4kHUVhxqCZ1XnEM")
    private String username;

    @ApiModelProperty(value = "验证码 ID（登录类型是 password 时必填）", example = "fe544239-c68d-4838-a91c-6d7dae0bb88d")
    private String uuid;

    @ApiModelProperty(value = "密码（登录类型是 password 时必填）", example = "Xg0ZNh2ym2GVc9J9HuZQjoP3lbyklFfDsVmLR2cLN3yzhxYKy1rCYacEAUUNcIZe")
    private String password;

    @ApiModelProperty(value = "登录类型（password：密码登录；sms：短信；agent：代理；social：社交登录）", required = true, example = "password")
    private String loginType;

//    @ApiModelProperty(value = "认证来源（wechat：微信；dingtalk：钉钉；）", required = true, example = "wechat")
//    private String authenticationSource;

//    @ApiModelProperty(value = "认证场景（qrcode_login：微信场景码登录；h5_authorize_login：H5 网页授权登录；）", required = true, example = "qrcode_login")
//    private String authenticationScene;
}
