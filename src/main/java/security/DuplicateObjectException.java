package security;

public class DuplicateObjectException extends Exception {
    private String duplicateObjectId;
    private String errorText;

    public DuplicateObjectException(String message, String duplicateObjectId) {
        super(message);
        this.duplicateObjectId = duplicateObjectId;
        this.errorText = message;
    }

    public String getDuplicateObjectId() {
        return duplicateObjectId;
    }

    public void setDuplicateObjectId(String duplicateObjectId) {
        this.duplicateObjectId = duplicateObjectId;
    }

    @Override
    public String getMessage() {
        return errorText;
    }

    public void setMessage(String message) {
        this.errorText = message;
    }
}
