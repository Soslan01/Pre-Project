package jm.task.core.jdbc.support;

public class CommonDaoException extends Exception{
    public CommonDaoException(String message){
        super(message);
    }
    public CommonDaoException(Throwable cause){
        super(cause);
    }
    public CommonDaoException(String message, Throwable cause){
        super(message, cause);
    }
    public CommonDaoException(String message, Throwable cause, boolean enableSupression, boolean writableStackTrace){
        super(message, cause, enableSupression, writableStackTrace);
    }
}
