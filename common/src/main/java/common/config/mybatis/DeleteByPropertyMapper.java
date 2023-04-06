package common.config.mybatis;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.weekend.Fn;

@RegisterMapper
public interface DeleteByPropertyMapper<T> {

    /**
     * 根据实体中的属性删除，条件使用等号
     *
     * @param fn    属性
     * @param value 属性值
     * @return
     */
    @DeleteProvider(type = DeletePropertyProvider.class, method = "dynamicSQL")
    int deleteByProperty(@Param("fn") Fn<T, ?> fn, @Param("value") Object value);

    /**
     * 根据实体中的属性删除，条件使用 in
     *
     * @param fn    属性
     * @param value 属性值
     * @return
     */
    @DeleteProvider(type = DeletePropertyProvider.class, method = "dynamicSQL")
    int deleteInByProperty(@Param("fn") Fn<T, ?> fn, @Param("values") Object value);

}
