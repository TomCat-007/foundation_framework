package common.enums.error;


public interface BusinessErrorCodeEnumerable extends ErrorCodeEnumerable {

    @Override
    default String errorType() {
        return "B";
    }
}
