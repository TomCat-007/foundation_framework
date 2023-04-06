package common.config.api.base;

import cn.hutool.core.util.ObjectUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分页响应类
 *
 * @author zhanghuiyuan
 * @apiNote 该类作为一个缓冲，用于一些接口没有参数的情况下
 * @description 该类作为一个缓冲，用于一些接口没有参数的情况下
 * @date 2023/3/3 13:33
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema
public class PageResponse extends BaseResponse {

    /**
     * 分页信息
     */
    @Schema(description = "分页信息")
    private Page page = new Page();

    public int currentPage() {
        return ObjectUtil.isNull(page) ? 1 : page.getCurrent();
    }

    public int currentSize() {
        return ObjectUtil.isNull(page) ? 1 : page.getSize();
    }
}
