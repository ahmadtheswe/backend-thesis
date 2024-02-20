package com.justahmed99.authapp.provider.userinfo;

public class UserException extends RuntimeException {
  private final int statusCode;
  public UserException(int statusCode, String message) {
    super(message);
    this.statusCode = statusCode;
  }

  public UserException(int statusCode, String message, Throwable cause) {
    super(message, cause);
    this.statusCode = statusCode;
  }

  public int getStatusCode() {
    return statusCode;
  }
}
