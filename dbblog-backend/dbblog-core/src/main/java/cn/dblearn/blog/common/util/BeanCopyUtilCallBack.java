/**   
* 
* @Description: 函数式接口
* @Package: com.idea.platform.common.functional 
* @author: leiyangjun   
* @date: 2020年7月28日 下午6:52:41 
*/
package cn.dblearn.blog.common.util;

/**   
* Copyright: Copyright (c) 2020 
* 
* @ClassName: BeanCopyUtilCallBack.java
* @Description: 函数式接口,用于beanutils
*
* @version: v1.0.0
* @author: leiyangjun
* @date: 2020年7月28日 
*
* Modification History:
* Date              Author               Version            Description
*---------------------------------------------------------*
* 2020年7月28日          leiyangjun               v1.0.0               修改原因
*/
@FunctionalInterface
public interface BeanCopyUtilCallBack <S, T> {

    /**
     * 定义默认回调方法
     * @param t
     * @param s
     */
    void callBack(S t, T s);
}
