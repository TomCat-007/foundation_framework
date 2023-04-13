package common.config.snowflake;

import cn.hutool.core.lang.generator.SnowflakeGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangguiyuan
 * @description 类注释
 * @date 2023/3/3 13:33
 */
@Configuration
public class SnowflakeGeneratorConfig {

    @Bean
    public SnowflakeGenerator snowflakeGenerator() {
        return new SnowflakeGenerator(1, 1);
    }

//    @Bean
//    public Ip2RegionSearch ip2RegionSearch() throws Exception {
//        DbConfig config = new DbConfig();
//        DbSearcher searcher = new DbSearcher(config, rocketMQProperties.getIp2RegionFilePath());
//        return new Ip2RegionSearch(searcher);
//    }

}
