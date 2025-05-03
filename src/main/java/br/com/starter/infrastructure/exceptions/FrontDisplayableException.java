package br.com.starter.infrastructure.exceptions;

import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

public class FrontDisplayableException extends RuntimeException {

    private final int status;
    @Nullable
    private final String reason;

    public FrontDisplayableException(HttpStatus status) {
        this(status, (String)null);
    }

    public FrontDisplayableException(HttpStatus status, @Nullable String reason) {
        super("");
        Assert.notNull(status, "HttpStatus is required");
        this.status = status.value();
        this.reason = reason;
    }

    public FrontDisplayableException(HttpStatus status, @Nullable String reason, @Nullable Throwable cause) {
        super((String)null, cause);
        Assert.notNull(status, "HttpStatus is required");
        this.status = status.value();
        this.reason = reason;
    }

    public FrontDisplayableException(int rawStatusCode, @Nullable String reason, @Nullable Throwable cause) {
        super((String)null, cause);
        this.status = rawStatusCode;
        this.reason = reason;
    }

    public HttpStatus getStatus() {
        return HttpStatus.valueOf(this.status);
    }

    public int getRawStatusCode() {
        return this.status;
    }

    @Nullable
    public String getReason() {
        return this.reason;
    }

    public String getMessage() {
        String msg = this.reason != null ? this.reason : "";
        return NestedExceptionUtils.buildMessage(msg, this.getCause());
    }
}
