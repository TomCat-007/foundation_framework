package common.validate.code.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * The class Validate code exception.
 *
 * @author
 */
public class ValidateCodeException extends AuthenticationException {

    /**
     * Instantiates a new Validate code exception.
     *
     * @param msg the msg
     */
    public ValidateCodeException(String msg) {
        super(msg);
    }

}