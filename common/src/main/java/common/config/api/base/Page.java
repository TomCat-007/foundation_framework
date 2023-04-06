package common.config.api.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 分页对象
 *
 * @author zhanghuiyuan
 * @description 类注释
 * @date 2023/3/3 13:33
 */
@Getter
@Setter
@Accessors(chain = true)
@Schema
public class Page implements Serializable {

    /**
     * 当前页
     *
     * @mock 1
     */
    @Schema(description = "当前页", type = "int", defaultValue = "1")
    private Integer current = 1;

    /**
     * 每页数据条数
     *
     * @mock 10
     */
    @Schema(description = "每页数据条数", type = "int", defaultValue = "10")
    private Integer size = 10;

    /**
     * 总数据条数
     *
     * @mock 1024
     */
    @Schema(description = "总数据条数", type = "long", defaultValue = "0")
    private Long total = 0L;
}
