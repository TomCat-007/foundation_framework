package common.config.typeHandler;


public interface OrganAware {

    /**
     * 当前身份所属的机构 ID
     *
     * @return
     */
    Long currentOrganId();
}
