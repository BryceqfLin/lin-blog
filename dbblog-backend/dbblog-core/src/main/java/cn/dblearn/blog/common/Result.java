package cn.dblearn.blog.common;

import cn.dblearn.blog.common.exception.enums.ErrorEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.http.HttpStatus;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Result
 *
 * @author bobbi
 * @date 2018/10/07 13:28
 * @email 571002217@qq.com
 * @description 通用返回类
 */
@JsonIgnoreProperties({"handler"})
@Data
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "状态码,S0014011001,其中S001:服务编码,401:错误级别代码参考httpstatus,1001:详细错误代码", required = false)
    private Integer code;
    @ApiModelProperty(value = "状态描述", required = false)
    private String msg;
    @ApiModelProperty(value = "业务数据", required = false)
    private T data;

    public Result(){}

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    public static Result<?> sysSuccess() {
        return new Result(HttpStatus.SC_OK, "success");
    }

    public static <T> Result<T> sysSuccess(T data) {
        return new Result(HttpStatus.SC_OK, "success", data);
    }

    public static Result<?> exception() {
        return exception(ErrorEnum.UNKNOWN);
    }

    public static Result<?> exception(ErrorEnum eEnum) {
        return new Result(eEnum.getCode(), eEnum.getMsg());
    }

    public static Result<?> exception(String msg) {
        return new Result(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
    }

    public static Result<?> exception(Integer code, String msg) {
        return new Result(code, msg);
    }
}
