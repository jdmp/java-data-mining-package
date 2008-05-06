package org.jdmp.matrix.util;

import java.lang.reflect.Field;

public class ReflectionUtil {

  public static Object extractPrivateField(Class<?> c, Object o, String fieldName) {
    Field[] fields = c.getDeclaredFields();
    if (fields != null) {
      for (Field f : fields) {
        try {
          if (fieldName.equals(f.getName())) {
            boolean isAccessible = f.isAccessible();
            f.setAccessible(true);
            Object value = f.get(o);
            f.setAccessible(isAccessible);
            return value;
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
    return null;
  }
  
}
