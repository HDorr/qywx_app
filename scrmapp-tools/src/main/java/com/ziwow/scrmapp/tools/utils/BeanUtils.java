package com.ziwow.scrmapp.tools.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;

/**
 * 
 * ClassName: BeanUtils <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014-11-11 下午5:27:55 <br/>
 *
 * @author hogen
 * @version 
 * @since JDK 1.6
 */
public class BeanUtils extends org.springframework.beans.BeanUtils{

	public static void copyProperties(Object source, Object target,
			Boolean isCopyNullValue) {
		
		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");

		Class<?> actualEditable = target.getClass();
		PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);

		for (PropertyDescriptor targetPd : targetPds) {
			Method writeMethod = targetPd.getWriteMethod();
			if (writeMethod != null) {
				PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
				if (sourcePd != null) {
					Method readMethod = sourcePd.getReadMethod();
					if (readMethod != null &&
							writeMethod.getParameterTypes()[0].isAssignableFrom(readMethod.getReturnType())) {
						try {
							if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
								readMethod.setAccessible(true);
							}
							Object value = readMethod.invoke(source);
							if (!isCopyNullValue) {
								if (value == null) {
									continue;
								}
							}
							if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
								writeMethod.setAccessible(true);
							}
							writeMethod.invoke(target, value);
						}
						catch (Throwable ex) {
							throw new FatalBeanException(
									"Could not copy property '" + targetPd.getName() + "' from source to target", ex);
						}
					}
				}
			}
		}
	}

}
