package com.sparrows.school.security.exception.handling;

import com.sparrows.school.security.exception.BusinessException;
import com.sparrows.school.security.exception.SecurityErrorCode;

public class JwtAuthenticationException extends BusinessException {

    public JwtAuthenticationException(SecurityErrorCode errorCode) {
        super(errorCode);
    }

}
