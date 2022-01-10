package poll.service;

public class VoteNotFoundException extends RuntimeException {

    private final String message;

    public VoteNotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return "VoteNotFoundException: " + message;
    }
}
