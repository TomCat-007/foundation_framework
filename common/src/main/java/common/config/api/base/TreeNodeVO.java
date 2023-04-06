package common.config.api.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhanghuiyuan
 * @description 类注释
 * @date 2023/3/3 13:33
 */
@Data
@Accessors(chain = true)
@Schema
public class TreeNodeVO implements Serializable {

    /**
     * 节点 ID
     *
     * @mock 0
     */
    @Schema(description = "节点 ID", type = "long")
    private Long key;

    /**
     * 节点名
     */
    @Schema(description = "节点名")
    private String title;

    /**
     * 前驱节点 ID
     *
     * @mock 0
     * @apiNote 为 0 时，则当前节点为头节点
     */
    @Schema(description = "前驱节点 ID")
    private Long prevId;

    /**
     * 子节点列表
     */
    @Schema(description = "子节点列表")
    List<TreeNodeVO> children;
}
