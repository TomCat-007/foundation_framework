package common.config.typeHandler;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author zhangguiyuan
 * @description 类注释
 * @date 2023/3/3 13:33
 */
@Slf4j
public class JSONObjectTypeHandler extends BaseTypeHandler<ObjectNode> {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        OBJECT_MAPPER.configure(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS, false);
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, ObjectNode parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, toJSONString(parameter));
    }

    @Override
    public ObjectNode getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return toObj(rs.getString(columnName));
    }

    @Override
    public ObjectNode getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return toObj(rs.getString(columnIndex));
    }

    @Override
    public ObjectNode getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return toObj(cs.getString(columnIndex));
    }

    private String toJSONString(ObjectNode obj) {
        String value = null;
        try {
            value = OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return value;
    }

    private ObjectNode toObj(String json) {
        ObjectNode obj = null;
        if (StrUtil.isNotBlank(json)) {
            try {
                obj = OBJECT_MAPPER.readValue(json, ObjectNode.class);
            } catch (JsonProcessingException e) {
                log.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }
        }
        return obj;
    }
}
