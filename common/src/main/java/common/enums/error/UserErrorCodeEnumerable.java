package common.enums.error;

import common.constant.ErrorCodeType;


public interface UserErrorCodeEnumerable extends ErrorCodeEnumerable {

    @Override
    default String errorType() {
        return ErrorCodeType.USER;
    }
}
