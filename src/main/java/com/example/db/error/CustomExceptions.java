package com.example.db.error;

public class CustomExceptions {

    public static class InternalServerErrorException extends CustomException {
        private final ErrorCode errorCode;

        public InternalServerErrorException(String message, Throwable cause, String detailedMessage, ErrorCode errorCode) {
            super(ErrorCode.INTERNAL_SERVER_ERROR, message, detailedMessage, cause);
            this.errorCode = errorCode;
        }

        public ErrorCode getErrorCode() {
            return errorCode;
        }

        public String getDetailedMessage() {
            return getMessage();
        }
    }


    //public static class UnauthorizedException extends CustomException {
    //  public UnauthorizedException(String message, String detailedMessage, Throwable cause) {
    //    super(ErrorCode.UNAUTHORIZED, message, detailedMessage, cause);
    // }
    //}

    public static class UnauthorizedException extends CustomException {
        private final ErrorCode errorCode;

        public UnauthorizedException(String message, Throwable cause, String detailedMessage,  ErrorCode errorCode) {
            super(ErrorCode.UNAUTHORIZED, message, detailedMessage, cause);
            this.errorCode = errorCode;
        }

        public ErrorCode getErrorCode() {
            return errorCode;
        }

        public String getDetailedMessage() {
            return getMessage();
        }
    }

    //public static class BadRequestException extends CustomException {
    //  public BadRequestException(String message, String detailedMessage, Throwable cause) {
    //    super(ErrorCode.BAD_REQUEST, message, detailedMessage, cause);
    //    }
    //}

    public static class BadRequestException extends CustomException {
        private final ErrorCode errorCode;

        public BadRequestException(String message, Throwable cause, String detailedMessage,  ErrorCode errorCode) {
            super(ErrorCode.BAD_REQUEST, message, detailedMessage, cause);
            this.errorCode = errorCode;
        }

        public ErrorCode getErrorCode() {
            return errorCode;
        }

        public String getDetailedMessage() {
            return getMessage();
        }
    }

    //public static class ForbiddenException extends CustomException {
    //  public ForbiddenException(String message, String detailedMessage, Throwable cause) {
    //    super(ErrorCode.FORBIDDEN, message, detailedMessage, cause);
    //}
    //}

    public static class ForbiddenException extends CustomException {
        private final ErrorCode errorCode;

        public ForbiddenException(String message, Throwable cause, String detailedMessage,  ErrorCode errorCode) {
            super(ErrorCode.FORBIDDEN, message, detailedMessage, cause);
            this.errorCode = errorCode;
        }

        public ErrorCode getErrorCode() {
            return errorCode;
        }

        public String getDetailedMessage() {
            return getMessage();
        }
    }

    //public static class MethodNotAllowedException extends CustomException {
    //    public MethodNotAllowedException(String message, String detailedMessage, Throwable cause) {
    //        super(ErrorCode.METHOD_NOT_ALLOWED, message, detailedMessage, cause);
    //    }
    //}

    public static class MethodNotAllowedException extends CustomException {
        private final ErrorCode errorCode;

        public MethodNotAllowedException(String message, Throwable cause, String detailedMessage,  ErrorCode errorCode) {
            super(ErrorCode.METHOD_NOT_ALLOWED, message,detailedMessage, cause);
            this.errorCode = errorCode;
        }

        public ErrorCode getErrorCode() {
            return errorCode;
        }

        public String getDetailedMessage() {
            return getMessage();
        }
    }

    //public static class ConflictException extends CustomException {
    //    public ConflictException(String message, String detailedMessage, Throwable cause) {
    //        super(ErrorCode.CONFLICT, message, detailedMessage, cause);
    //    }
    //}

    public static class ConflictException extends CustomException {
        private final ErrorCode errorCode;

        public ConflictException(String message, Throwable cause, String detailedMessage,  ErrorCode errorCode) {
            super(ErrorCode.CONFLICT, message, detailedMessage, cause);
            this.errorCode = errorCode;
        }

        public ErrorCode getErrorCode() {
            return errorCode;
        }

        public String getDetailedMessage() {
            return getMessage();
        }
    }

    //public static class BadGatewayException extends CustomException {
    //    public BadGatewayException(String message, String detailedMessage, Throwable cause) {
    //        super(ErrorCode.BAD_GATEWAY, message, detailedMessage, cause);
    //    }
    //}

    public static class BadGatewayException extends CustomException {
        private final ErrorCode errorCode;

        public BadGatewayException(String message, Throwable cause, String detailedMessage,  ErrorCode errorCode) {
            super(ErrorCode.BAD_GATEWAY, message, detailedMessage, cause);
            this.errorCode = errorCode;
        }

        public ErrorCode getErrorCode() {
            return errorCode;
        }

        public String getDetailedMessage() {
            return getMessage();
        }
    }

    //public static class ServiceUnavailableException extends CustomException {
    //    public ServiceUnavailableException(String message, String detailedMessage, Throwable cause) {
    //        super(ErrorCode.SERVICE_UNAVAILABLE, message, detailedMessage, cause);
    //    }
    //}

    public static class ServiceUnavailableException extends CustomException {
        private final ErrorCode errorCode;

        public ServiceUnavailableException(String message, Throwable cause, String detailedMessage,  ErrorCode errorCode) {
            super(ErrorCode.SERVICE_UNAVAILABLE, message, detailedMessage, cause);
            this.errorCode = errorCode;
        }

        public ErrorCode getErrorCode() {
            return errorCode;
        }

        public String getDetailedMessage() {
            return getMessage();
        }
    }

    //public static class GatewayTimeoutException extends CustomException {
    //  public GatewayTimeoutException(String message, String detailedMessage, Throwable cause) {
    //     super(ErrorCode.GATEWAY_TIMEOUT, message, detailedMessage, cause);
    // }
    //}

    public static class GatewayTimeoutException extends CustomException {
        private final ErrorCode errorCode;

        public GatewayTimeoutException(String message, Throwable cause, String detailedMessage,  ErrorCode errorCode) {
            super(ErrorCode.GATEWAY_TIMEOUT,message, detailedMessage,  cause);
            this.errorCode = errorCode;
        }

        public ErrorCode getErrorCode() {
            return errorCode;
        }

        public String getDetailedMessage() {
            return getMessage();
        }
    }

    //public static class ResourceNotFoundException extends CustomException {

    //   public ResourceNotFoundException(String message, String detailedMessage, Throwable cause) {
    //       super(ErrorCode.NOT_FOUND, message, detailedMessage, cause);
    //   }
    //}

    public static class ResourceNotFoundException extends CustomException {
        private final ErrorCode errorCode;

        public ResourceNotFoundException(String message, Throwable cause, String detailedMessage,  ErrorCode errorCode) {
            super(ErrorCode.NOT_FOUND, message, detailedMessage, cause);
            this.errorCode = errorCode;
        }

        public ErrorCode getErrorCode() {
            return errorCode;
        }

        public String getDetailedMessage() {
            return getMessage();
        }
    }



    public static abstract class CustomException extends RuntimeException {
        private final ErrorCode errorCode;
        private final String detailedMessage;

        public CustomException(ErrorCode errorCode, String message, String detailedMessage, Throwable cause) {
            super(message, cause);
            this.errorCode = errorCode;
            this.detailedMessage = detailedMessage;
        }

        public ErrorCode getErrorCode() {
            return errorCode;
        }

        public String getDetailedMessage() {
            return detailedMessage;
        }

        @Override
        public String getMessage() {
            return super.getMessage() + " [ErrorCode: " + errorCode + "]";
        }
    }
}
