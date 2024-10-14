package com.example.db.error;

public enum ErrorCode {

    BAD_REQUEST(400, "Bad Request!!"),
    UNAUTHORIZED(401, "Unauthorized!!"),
    FORBIDDEN(403, "Access Denied!!"),
    NOT_FOUND(404, "Resource Not Found!!"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed!!"),
    CONFLICT(409, "Conflict!!"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error!!"),
    BAD_GATEWAY(502, "Bad Gateway!!"),
    SERVICE_UNAVAILABLE(503, "Service Unavailable!!"),
    GATEWAY_TIMEOUT(504, "Gateway Timeout!!");

    private final int status;
    private final String message;

    ErrorCode(int status, String message){
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage(){
        return message;
    }

    @Override
    public String toString() {
        return "Status: " + status + ", Message: " + message;
    }
}
