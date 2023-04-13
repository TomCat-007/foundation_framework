package common.validate.code.model.generator.image;

import common.validate.code.enums.ValidateCodeType;
import common.validate.code.model.ValidateCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhangguiyuan
 * @description
 * @date 2023/4/13 10:30
 */
@Getter
@Setter
public class ImageCode extends ValidateCode {

    private String base64Str;

    /**
     * @param uid       校验码唯一 ID
     * @param code      校验码
     * @param expireIn  有效期, 单位秒
     * @param base64Str 图形校验码
     */
    public ImageCode(String uid, String code, int expireIn, String base64Str) {
        super(ValidateCodeType.IMAGE, uid, code, expireIn);
        this.base64Str = base64Str;
    }
}
