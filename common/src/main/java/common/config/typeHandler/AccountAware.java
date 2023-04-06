package common.config.typeHandler;


public interface AccountAware {

    /**
     * 当前身份的账户 ID
     *
     * @return
     */
    Long currentAccountId();
}
