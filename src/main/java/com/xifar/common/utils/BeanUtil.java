package com.xifar.common.utils;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtilsBean;

public class BeanUtil {

	// 将JavaBean转为map类型，然后返回一个map类型的值
	public static Map<String, Object> beanToMap(Object obj) {
		Map<String, Object> params = new HashMap<String, Object>(0);
		try {
			PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
			PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj);
			for (int i = 0; i < descriptors.length; i++) {
				String name = descriptors[i].getName();
				if (!"class".equals(name)) {
					params.put(name, propertyUtilsBean.getNestedProperty(obj, name));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return params;
	}

	// 将JavaBean转为map<String,String>类型，然后返回一个map类型的值
	public static Map<String, String> beanToStringMap(Object obj) {
		Map<String, String> params = new HashMap<String, String>(0);
		try {
			PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
			PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj);
			for (int i = 0; i < descriptors.length; i++) {
				String name = descriptors[i].getName();
				if (!"class".equals(name)) {
					if (null != propertyUtilsBean.getNestedProperty(obj, name)
							&& null != propertyUtilsBean.getPropertyType(obj, name)) {
						if ("class java.lang.String".equals(propertyUtilsBean.getPropertyType(obj, name).toString())
								|| "class java.lang.Integer".equals(propertyUtilsBean.getPropertyType(obj, name).toString())
								|| "class java.lang.Double".equals(propertyUtilsBean.getPropertyType(obj, name).toString())
								|| "class java.lang.Float".equals(propertyUtilsBean.getPropertyType(obj, name).toString())
								|| "class java.lang.Short".equals(propertyUtilsBean.getPropertyType(obj, name).toString())
								|| "class java.lang.Byte".equals(propertyUtilsBean.getPropertyType(obj, name).toString())
								|| "class java.lang.Long".equals(propertyUtilsBean.getPropertyType(obj, name).toString())
								|| "class java.lang.Boolean".equals(propertyUtilsBean.getPropertyType(obj, name).toString()))
							params.put(name, propertyUtilsBean.getNestedProperty(obj, name).toString());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return params;
	}

	public static void main(String[] args) {
	}

}
