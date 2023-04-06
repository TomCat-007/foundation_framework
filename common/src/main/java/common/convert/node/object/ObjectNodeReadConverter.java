package common.convert.node.object;

import com.fasterxml.jackson.databind.node.ObjectNode;
import common.util.JsonUtil;
import org.springframework.core.convert.converter.Converter;

/**
 * @author zhanghuiyuan
 * @description
 * @date 2023/3/3 13:33
 */
public class ObjectNodeReadConverter implements Converter<String, ObjectNode> {

    @Override
    public ObjectNode convert(String source) {
        return JsonUtil.toObj(source, ObjectNode.class);
    }
}
