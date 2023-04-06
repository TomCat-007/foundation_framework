package common.enums.error;

import common.constant.ErrorCodeType;


public interface SystemErrorCodeEnumerable extends ErrorCodeEnumerable {

    @Override
    default String errorType() {
        return ErrorCodeType.SYSTEM;
    }
}
