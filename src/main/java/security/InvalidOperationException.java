package security;

public class InvalidOperationException extends Exception {
    private String message;
    private String operation;
    private String userId;

    public InvalidOperationException(String userId, String operation, String message){
        super(message);
        this.message = message;
        this.userId = userId;
        this.operation = operation;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getOperation() {
        return operation;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer("");
        sb.append("Exception: Invalid Operation - ").append(this.operation).append(" by user ").append(this.userId);

        return sb.toString();
    }
}
