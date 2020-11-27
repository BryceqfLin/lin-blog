package cn.dblearn.blog.common.exception.enums;

public interface IWarnEnum {
    default Integer getCode() {
        return null;
    }


    default String getDescription() {
        return null;
    }


    default void setCode(String code) {

    }

    default void setDescription(String description) {

    }
}
