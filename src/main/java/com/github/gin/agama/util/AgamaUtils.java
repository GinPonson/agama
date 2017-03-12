package com.github.gin.agama.util;

import com.github.gin.agama.AgamaException;

import java.util.Collection;
import java.util.Map;

/**
 * Created by GinPonson on 12/6/2016.
 */
public class AgamaUtils {
    public static boolean isNotBlank(String string){
        return string != null && !string.trim().equals("");
    }

    public static boolean isNotBlank(Object object){
        return object != null && !object.toString().trim().equals("");
    }

    public static boolean isBlank(String string){
        return string == null || string.trim().equals("");
    }

    public static boolean isNotEmpty(Collection collection){
        return collection != null && !collection.isEmpty();
    }

    public static boolean isNotEmptyMap(Map map){
        return map != null && !map.isEmpty();
    }

    public static boolean contains(String str,String con){
        if(str == null)
            throw new AgamaException();
        return str.indexOf(con) != -1;
    }
}
