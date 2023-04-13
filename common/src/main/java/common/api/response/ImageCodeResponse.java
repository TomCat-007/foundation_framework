package common.api.response;

import common.config.api.base.BaseResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author zhangguiyuan
 * @description
 * @date 2023/4/13 10:35
 */
@Data
@ApiModel
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ImageCodeResponse extends BaseResponse {
    @ApiModelProperty(value = "验证码 ID", example = "792e307c-d7b0-4fd5-be12-92da55f044b0")
    private String uid;

    @ApiModelProperty(value = "验证码图片 base64", example = "data:image/png;base64,xxxxxxxxxxxx")
    private String captcha;
}
