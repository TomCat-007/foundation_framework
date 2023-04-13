package common.config.api.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 无属性响应类
 *
 * @author zhangguiyuan
 * @apiNote 该类用于没有属性返回时作为解除泛型语法的限制而用
 * @description 该类用于没有属性返回时作为解除泛型语法的限制而用
 * @date 2023/3/3 13:33
 */
@Data
@Schema
@EqualsAndHashCode(callSuper = true)
public class NoPropertyResponse extends BaseResponse {

}
