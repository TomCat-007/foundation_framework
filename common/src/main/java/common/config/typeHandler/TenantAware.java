package common.config.typeHandler;


public interface TenantAware {
    /**
     * 当前身份的租户 ID
     *
     * @return
     */
    Long currentTenantId();
}
