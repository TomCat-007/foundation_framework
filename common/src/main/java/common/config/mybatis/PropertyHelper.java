package common.config.mybatis;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import tk.mybatis.mapper.MapperException;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.entity.EntityTable;
import tk.mybatis.mapper.mapperhelper.EntityHelper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class PropertyHelper {

    /**
     * 根据实体Class和属性名获取对应的表字段名
     *
     * @param entityClass 实体Class对象
     * @param property    属性名
     * @return
     */
    public static String getColumnByProperty(Class<?> entityClass, String property) {
        EntityTable entityTable = EntityHelper.getEntityTable(entityClass);
        EntityColumn entityColumn = entityTable.getPropertyMap().get(property);
        return entityColumn.getColumn();
    }

    public static boolean isEmpty(Object value, boolean notEmpty) {
        boolean empty = false;
        if (ObjectUtil.isNull(value)) {
            empty = true;
        } else {
            if (value instanceof LocalDateTime || value instanceof LocalDate || value instanceof LocalTime) {
                empty = false;
            }
            if (notEmpty) {
                empty = StrUtil.isEmptyIfStr(value);
            }
        }
        return empty;
    }

    public static boolean isNull(Object value, boolean safeDelete) {
        boolean isNull = false;
        if (safeDelete) {
            if (ObjectUtil.isNull(value)) {
                throw new MapperException("安全删除模式下，不允许执行不带查询条件的 delete 方法");
            }
        } else {
            if (ObjectUtil.isNull(value)) {
                isNull = true;
            }
        }
        return isNull;
    }
}
