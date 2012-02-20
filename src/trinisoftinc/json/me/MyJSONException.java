package trinisoftinc.json.me;

/**
 * The MyJSONException is thrown by the JSON.org classes then things are amiss.
 * @author JSON.org
 * @version 2
 */
public class MyJSONException extends Exception {
    private Throwable cause;

    /**
     * Constructs a MyJSONException with an explanatory message.
     * @param message Detail about the reason for the exception.
     */
    public MyJSONException(String message) {
        super(message);
    }

    public MyJSONException(Throwable t) {
        super(t.getMessage());
        this.cause = t;
    }

    public Throwable getCause() {
        return this.cause;
    }
}