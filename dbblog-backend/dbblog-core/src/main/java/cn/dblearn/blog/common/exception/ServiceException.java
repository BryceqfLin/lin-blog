package cn.dblearn.blog.common.exception;

import cn.dblearn.blog.common.Result;
import cn.dblearn.blog.common.exception.enums.IWarnEnum;

public class ServiceException extends MyException{
    public ServiceException(){

    }

    public ServiceException(IWarnEnum warnEnum){
        super(Result.exception(warnEnum.getCode(),warnEnum.getDescription()));
    }


    public ServiceException(String  warnMsg){
        super(Result.exception(500,warnMsg));
    }
}
