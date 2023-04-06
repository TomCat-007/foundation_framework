package common.config.mybatis;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.weekend.Fn;

import java.util.List;

@RegisterMapper
public interface SelectByPropertyMapper<T> {

    /**
     * 根据属性及对应值进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号
     *
     * @param fn    查询属性
     * @param value 属性值
     * @return
     */
    @SelectProvider(type = SelectPropertyProvider.class, method = "dynamicSQL")
    T selectOneByProperty(@Param("fn") Fn<T, ?> fn, @Param("value") Object value);

    /**
     * 根据实体中的属性值进行查询，查询条件使用等号
     *
     * @param fn    查询属性
     * @param value 属性值
     * @return
     */
    @SelectProvider(type = SelectPropertyProvider.class, method = "dynamicSQL")
    List<T> selectByProperty(@Param("fn") Fn<T, ?> fn, @Param("value") Object value);

    /**
     * 根据实体中的属性值进行查询，查询条件使用 in
     *
     * @param fn     查询属性
     * @param values 属性值集合
     * @return
     */
    @SelectProvider(type = SelectPropertyProvider.class, method = "dynamicSQL")
    List<T> selectInByProperty(@Param("fn") Fn<T, ?> fn, @Param("values") List<?> values);

    /**
     * 根据实体中的属性值进行查询，查询条件使用 between
     *
     * @param fn 查询属性
     * @return
     */
    @SelectProvider(type = SelectPropertyProvider.class, method = "dynamicSQL")
    List<T> selectBetweenByProperty(@Param("fn") Fn<T, ?> fn, @Param("begin") Object begin, @Param("end") Object end);

    /**
     * 根据主键字段查询总数，方法参数必须包含完整的主键属性，查询条件使用等号
     *
     * @param fn    查询属性
     * @param value 属性值
     * @return
     */
    @SelectProvider(type = SelectPropertyProvider.class, method = "dynamicSQL")
    boolean existsWithProperty(@Param("fn") Fn<T, ?> fn, @Param("value") Object value);

    /**
     * 根据实体中的属性查询总数，查询条件使用等号
     *
     * @param fn    查询属性
     * @param value 属性值
     * @return
     */
    @SelectProvider(type = SelectPropertyProvider.class, method = "dynamicSQL")
    int selectCountByProperty(@Param("fn") Fn<T, ?> fn, @Param("value") Object value);

}
