package cn.dblearn.blog.common.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>项目名称: common-base </p> 
 * <p>文件名称: BeanUtil.java </p> 
 * <p>类型描述: [去掉VO其余属性值] </p>
 * <p>创建时间: 2019年7月9日 下午4:13:10 </p>
 * @author 雷阳军
 * @version V1.0
 * @update 2019年9月6日 上午9:42:47 lifanfan 
 */
@Slf4j
public class BeanUtil extends BeanUtils {

	/**
	 * <p>功能描述: [VO去掉其他属性,如baseVO只保留 ID属性值其余为null convertToNullTenant(BaseVO,"id")
	 * s将目标对象转换为一个新的对象只包含tenant和columns属性,其余属性为null] </p>
	 * @Title convertToNull
	 * @param <T> 泛型对象.
	 * @param entity .
	 * @param columns 需要包含的属性名称.
	 * @return T
	 * @CreateDate 2019年9月6日 上午9:43:14
	 * @update 2019年9月6日 上午9:43:14  lifanfan    
	 */
	@SuppressWarnings("unchecked")
	public static <T> T convertToNull(T entity, String... columns) {
		JSONObject jsonObject = (JSONObject) JSON.toJSON(entity);
		JSONObject resultJson = new JSONObject();
		for (String column : columns) {
			if (jsonObject.get(column) != null) {
				resultJson.put(column, jsonObject.get(column));
			}
		}
		return (T) JSON.toJavaObject(resultJson, entity.getClass());
	}

	/**
	 * <p>功能描述: [ VO去掉其他属性,如baseVO只保留 ID属性值其余为null convertToNullTenant(BaseVO,"id")
	 * s将目标对象转换为一个新的对象只包含columns属性,其余属性为null] </p>
	 * @Title convertToNullTenant
	 * @param <T> 泛型对象.
	 * @param entity
	 * @param columns 需要包含的属性名称.
	 * @return T
	 * @CreateDate 2019年9月6日 上午9:43:44
	 * @update 2019年9月6日 上午9:43:44  lifanfan    
	 */
	@SuppressWarnings("unchecked")
	public static <T> T convertToNullTenant(T entity, String... columns) {
		JSONObject jsonObject = (JSONObject) JSON.toJSON(entity);
		JSONObject resultJson = new JSONObject();
		for (String column : columns) {
			resultJson.put(column, jsonObject.get(column));
		}
		if (jsonObject.get("tenant") != null) {
			resultJson.put("tenant", jsonObject.get("tenant"));
		}
		return (T) JSON.toJavaObject(resultJson, entity.getClass());
	}

	/**
	 * <p>功能描述: [VO互转] </p>
	 * @Title convertVO.
	 * @param <T> 要转换的源对象.
	 * @param <V> 要转换的目标对象.
	 * @param source .
	 * @param target .
	 * @return V
	 * @CreateDate 2019年9月6日 上午9:44:11
	 * @update 2019年9月6日 上午9:44:11  lifanfan    
	 */
	@SuppressWarnings("unchecked")
	public static <T, V> V convertVO(T source, V target) {
		// String jsonStr=JsonJackUtils.toJSon(source);
		// return JsonJackUtils.readValue(jsonStr, valueType);
		JSONObject jsonObject = (JSONObject) JSON.toJSON(source);
		return ((V) jsonObject.toJavaObject(target.getClass()));
	}

	/**
	 * <p>功能描述: [VO互转] </p>
	 * @Title convertVO
	 * @param <T> 要转换的源对象.
	 * @param <V> 要转换的目标对象.
	 * @param source .
	 * @param target 目标对象.
	 * @return V
	 * @CreateDate 2019年9月6日 上午9:44:44
	 * @update 2019年9月6日 上午9:44:44  lifanfan    
	 */
	public static <T, V> V convertVO(T source, Class<V> target) {
		// String jsonStr=JsonJackUtils.toJSon(source);
		// return JsonJackUtils.readValue(jsonStr, valueType);
		JSONObject jsonObject = (JSONObject) JSON.toJSON(source);
		return jsonObject.toJavaObject(target);
	}

	/**
	 * <p>功能描述: [将map转换成Object] </p>
	 * @Title convertMap
	 * @param type .
	 * @param map .
	 * @return
	 * @throws IntrospectionException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws Exception Object
	 * @CreateDate 2019年9月6日 上午9:45:12
	 * @update 2019年9月6日 上午9:45:12  lifanfan    
	 */
	public static Object convertMap(Class type, Map map) throws IntrospectionException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		BeanInfo beanInfo = Introspector.getBeanInfo(type); //  获取类属性
		Object obj = type.newInstance(); //  创建 JavaBean 对象
		// 给JavaBean 对象的属性赋值
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (map.containsKey(propertyName) && !"class".equals(propertyName)) {
				Object value = map.get(propertyName);
				Object[] args = new Object[1];
				args[0] = value;
				descriptor.getWriteMethod().invoke(obj, args);
			}
		}
		return obj;
	}
	
	/**
	 * 
	* @Function: BeanUtil.java
	* @Title: copyProperties
	* @Description: 从源对象中复制属性值到目标对象 如BeanUtil.copyProperties(environment,params,sort,search,page);
	* @param target 需要复制的目标对象:保留目标对象中已有的属性值
	* @param sources 支持传入多个源对象 
	* @return Object 引用传递,调用时可以不用在意返回值
	* @author: leiyangjun
	* @date: 2020年7月24日
	 */
	public static Object copyPropertiesBysources(Object target, Object ...sources) {
		for (Object source : sources) {
			copyProperties(source, target);
		}
		return target;
	}
	
	/**
	 * 
	* @Function: BeanUtil.java
	* @Title: copyPropertiesBysources
	* @Description: 适用于不需要保留目标对象字段属性,以及目标对象不需要实例化
	* @param <T>
	* @param targetClass
	* @param sources
	* @return T
	* @author: leiyangjun
	* @date: 2020年9月17日
	 */
	public static <T> T copyPropertiesBysources(Class<T> targetClass, Object ...sources) {
		T target=getTargetInstance(targetClass);
		for (Object source : sources) {
			copyProperties(source, target);
		}
		return target;
	}
	
	/**
	 * 
	* @Function: BeanUtil.java
	* @Title: copyProperties
	* @Description: 适用于不需要保留目标对象字段属性,以及目标对象不需要实例化
	* @param <T>
	* @param targetClass 
	* @param source
	* @return T
	* @author: leiyangjun
	* @date: 2020年9月17日
	 */
	public static <T> T copyProperties(Class<T> targetClass, Object source) {
		T target=getTargetInstance(targetClass);
		copyProperties(source, target);
		return target;
	}
	
	/**
	 * 
	* @Function: BeanUtil.java
	* @Title: copyListProperties
	* @Description: 拷贝集合对象 示例:BeanCopyUtil.copyListProperties(userDtosList, User::new);
	* @param <S>
	* @param <T>
	* @param sources 数据源集合
	* @param target ::new(eg: UserVO::new)
	* @return
	* List<T>
	* @author: leiyangjun
	* @date: 2020年7月28日
	 */
    public static <S, T> List<T> copyListProperties(List<S> sources, Supplier<T> target) {
        return copyListProperties(sources, target, null);
    }


   	/**
	* 
	* @Function: BeanUtil.java
	* @Title: copyListProperties
	* @Description: 拷贝集合对象
	* @param <S>
	* @param <T>
	* @param sources 数据源集合
	* @param target ::new(eg: UserVO::new)
	* @param callBack 回调函数
	* @return List<T> 
	* @author: leiyangjun
	* @date: 2020年7月28日
	*/
    public static <S, T> List<T> copyListProperties(List<S> sources, Supplier<T> target, BeanCopyUtilCallBack<S, T> callBack) {
        if(CollectionUtils.isEmpty(sources)) {
        	return new ArrayList<T>(0);
        }
    	List<T> list = new ArrayList<>(sources.size());
        for (S source : sources) {
            T t = target.get();
            copyProperties(source, t);
            list.add(t);
            if (callBack != null) {
                // 回调
                callBack.callBack(source, t);
            }
        }
        return list;
    }
    
    private static <T> T getTargetInstance(Class<T> target) {
    	try {
			return target.newInstance();
		} catch (InstantiationException e) {
			log.error("",e);
		} catch (IllegalAccessException e) {
			log.error("",e);
		}
    	return null;
    }
    
    
}
