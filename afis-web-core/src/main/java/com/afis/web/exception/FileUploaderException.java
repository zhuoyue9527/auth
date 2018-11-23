package com.afis.web.exception;

/**
 * Created by hsw on 2017/3/2.
 */
public class FileUploaderException extends WebBusinessException {

    public static final int DESTINATION_PATH_NOT_SETTING = 50015;
    public static final int FILE_TYPE_NOT_SUPPORTED = 50016;
    /**
     * 上传文件大小不能超过{0}
     */
    public static final int FILE_SIZE_OUT_LIMIT = 50017;
    public static final int OUT_LIMIT_MAX_UPLOAD_PATH_DEPTH = 50018;
    /**
     * 上传失败 {0}
     */
    public static final int UPLOAD_FAILED = 50019;

    public FileUploaderException(int errorCode) {
        super(errorCode);
    }

    public FileUploaderException(int errorCode, String message) {
        super(errorCode, message);
    }

    public FileUploaderException(int errorCode, Throwable throwable) {
        super(errorCode, throwable);
    }

    public FileUploaderException(int errorCode, String message, Throwable throwable) {
        super(errorCode, message, throwable);
    }

    public FileUploaderException() {
    }

    public FileUploaderException(String message) {
        super(message);
    }

    public FileUploaderException(Throwable throwable) {
        super(throwable);
    }

    public FileUploaderException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
