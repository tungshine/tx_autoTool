package com.tanglover.tool;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class EntityDtoConverter {

    /**
     * @Author TangXu
     * @Date 2018/10/18 13:46
     * @Description 视图对象转换实体对象
     * @Param [source, target]
     * @Return java.lang.Object
     */
    public static Object voConvertEntity(Object source, Object target) {
        Class<?> sourceClass = source.getClass();
        Class<?> targetClass = target.getClass();
        Field[] fields = sourceClass.getDeclaredFields();
        for (Field sourceField : fields) {
            try {
                invoke(sourceClass.getDeclaredMethods(), targetClass.getDeclaredMethods(), target, source,
                        sourceField.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return target;
    }

//	public static Object DtoConvertEntity(Object source, Class<?> target) {
//		Object object = null;
//		try {
//			object = entityConvertDto(source, target);
//		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
//		} catch (SecurityException e) {
//			e.printStackTrace();
//		}
//		return object;
//	}

    /**
     * @Author TangXu
     * @Date 2018/10/18 13:48
     * @Description 实体对象转换视图对象
     * @Param [source, target]
     * @Return java.lang.Object
     */
    public static Object entityConvertDto(Object source, Class<?> target) {
        Class<?> sourceClazz = source.getClass();
        Object targetObject = null;
        try {
            targetObject = target.newInstance();
            Field[] fields = sourceClazz.getDeclaredFields();
            for (Field resourceField : fields) {
                invoke(sourceClazz.getDeclaredMethods(), target.getDeclaredMethods(), targetObject, source,
                        resourceField.getName());
            }
            return targetObject;
        } catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return null;
        } catch (SecurityException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @Author TangXu
     * @Date 2018/10/18 13:47
     * @Description 运用反射机制赋值给对象
     * @Param [sourceMethods, targetMethods, targetObject, sourceObject, name]
     * @Return void
     */
    private static void invoke(Method[] sourceMethods, Method[] targetMethods, Object targetObject, Object sourceObject,
                               String name) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        String upperName = Character.toUpperCase(name.charAt(0)) + name.substring(1);
        String setterName = "set" + upperName;
        String getterName = "get" + upperName;
        Method sourceMethod = null;
        Method targetMethod = null;
        sourceMethod = getMethodByName(sourceMethods, getterName);
        targetMethod = getMethodByName(targetMethods, setterName);
        if (sourceMethod != null && targetMethod != null) {
            Object value = sourceMethod.invoke(sourceObject);
            if (null != value) {
                targetMethod.invoke(targetObject, value);
            }
        }
    }

    /**
     * @Author TangXu
     * @Date 2018/10/18 13:48
     * @Description 获取方法名称
     * @Param [methods, methodName]
     * @Return java.lang.reflect.Method
     */
    private static Method getMethodByName(Method[] methods, String methodName) {
        for (Method m : methods) {
            if (m.getName().equals(methodName)) {
                return m;
            }
        }
        return null;
    }
}