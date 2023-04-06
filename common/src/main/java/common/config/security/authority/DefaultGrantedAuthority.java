package common.config.security.authority;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author zhanghuiyuan
 * @description 类注释
 * @date 2023/3/3 13:33
 */
public class DefaultGrantedAuthority implements GrantedAuthority {

    /**
     * 统一资源名称
     * Uniform Resource Name
     */
    private String arn;

    /**
     * 资源路径
     */
    private String path;

    public DefaultGrantedAuthority() {
    }

    public DefaultGrantedAuthority(String arn, String path) {
        this.arn = arn;
        this.path = path;
    }

    @Override
    public String getAuthority() {
        return arn;
    }

    public String getArn() {
        return arn;
    }

    public void setArn(String arn) {
        this.arn = arn;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
