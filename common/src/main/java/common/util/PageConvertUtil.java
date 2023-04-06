package common.util;

import com.github.pagehelper.PageInfo;
import common.config.api.base.Page;

import java.util.List;

public class PageConvertUtil {

    /**
     * PageHelper的PageInfo转成自定义的Page对象
     *
     * @param values
     * @return
     */
    public static Page convert(List<?> values) {
        if (values == null) {
            return new Page();
        }
        PageInfo<?> pageInfo = new PageInfo<>(values);
        return new Page()
                .setCurrent(pageInfo.getPageNum())
                .setSize(pageInfo.getPageSize())
                .setTotal(pageInfo.getTotal());
    }
}