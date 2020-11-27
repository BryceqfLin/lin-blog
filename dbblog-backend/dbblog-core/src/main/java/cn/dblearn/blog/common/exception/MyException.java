package cn.dblearn.blog.common.exception;

import cn.dblearn.blog.common.Result;
import cn.dblearn.blog.common.exception.enums.ErrorEnum;
import lombok.Data;

import java.text.MessageFormat;

/**
 * MyException
 *
 * @author bobbi
 * @date 2018/10/07 13:54
 * @email 571002217@qq.com
 * @description 自定义异常
 */
@Data
public class MyException extends RuntimeException {
    private static final long serialVersionUID = -4876823388883069092L;
    private Result<?> errorInfo;

    public MyException() {
    }

    public MyException(Result<?> errorInfo) {
        this.errorInfo = errorInfo;
    }

    public MyException(Result<?> errorInfo,Throwable e){
        super(errorInfo.getMsg(),e);
    }

    public MyException(ErrorEnum errorEnum,Throwable e){
        super(errorEnum.getDescription(),e);
    }

    public Result<?> getErrorInfo() {
        return this.errorInfo;
    }

    public Throwable fillInStackTrace() {
        return this;
    }

    public String toString() {
        return MessageFormat.format("{0}[{1}]", this.errorInfo.getCode(), this.errorInfo.getMsg());
    }
}

