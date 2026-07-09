package app.exception;

public class RootAccessDeniedException extends FundamentError{

    public RootAccessDeniedException() {

        super(ListErrors.ROOT_ERROR);

    }
}
