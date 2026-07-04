package app.exception;

import lombok.Getter;


@Getter
public class FundamentError extends RuntimeException {

    private final ListErrors listErrors;
    private final String message;
    private final Object[] args;
    private final Throwable originalError;

    public FundamentError(ListErrors error) {

        super(error.getBaseMessage());
        this.listErrors = error;
        this.message =  error.getBaseMessage();
        this.args = new Object[0];
        this.originalError = null;

    }

    public FundamentError(ListErrors error, String massage, Object... args) {

        super(error.format(args));
        this.listErrors = error;
        this.message = massage;
        this.args = args;
        this.originalError = null;

    }

    public FundamentError(ListErrors error, String message, Throwable cause) {

        super(error.format());
        this.listErrors = error;
        this.args = new Object[0];
        this.message = message;
        this.originalError = cause;

    }

    public FundamentError(ListErrors error, Object... args) {

        super(error.format(args));
        this.listErrors = error;
        this.message = error.getBaseMessage();
        this.args = args;
        this.originalError = null;

    }

    public FundamentError(ListErrors error, String message, Throwable cause, Object... args) {

        super(error.format(args), cause);

        this.listErrors = error;
        this.message = message;
        this.args = args;
        this.originalError = cause;

    }

    public FundamentError(ListErrors error, String message) {

        super(error.format(message));
        this.listErrors = error;
        this.message = message;
        this.args = new Object[0];
        this.originalError = null;

    }

    public FundamentError(ListErrors errors, Throwable cause) {

        super(errors.format(cause.getMessage()));
        this.listErrors = errors;
        this.message = null;
        this.args = new Object[0];
        this.originalError = cause;

    }

}
