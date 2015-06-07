package org.wordcloud.objects;


/**
 * Error message that is sent to the clients in case of errors
 */
public class ErrorMessage {

    private String message;
    private long timestamp;

    public ErrorMessage(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ErrorMessage that = (ErrorMessage) o;

        if (timestamp != that.timestamp) return false;
        return !(message != null ? !message.equals(that.message) : that.message != null);

    }

    @Override
    public int hashCode() {
        int result = message != null ? message.hashCode() : 0;
        result = 31 * result + (int) (timestamp ^ (timestamp >>> 32));
        return result;
    }
}
