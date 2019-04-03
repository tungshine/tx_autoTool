package com.tanglover.tool;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 不言
 * @create 2018-10-30 9:55
 * @description:
 */
public class ReturnUtil {

    static String SUCCESS = "success";
    static String RESULT = "result";
    static String MSG = "msg";
    static String DATA = "data";

    /**
     * @author: TangXu
     * @date: 2018/10/30 10:01
     * @description:
     * @param: [object]       
     */
    public static Map<String, Object> returnSuccess(Object object) {
        Map<String, Object> map = new HashMap<>();
        map.put(MSG, SUCCESS);
        map.put(RESULT, 0);
        map.put(DATA, object);
        return map;
    }

    /**
     * @author: TangXu
     * @date: 2018/10/30 10:01
     * @description:
     * @param: [errorMsg, errorCode]       
     */
    public static Map<String, Object> returnError(String errorMsg, int errorCode) {
        Map<String, Object> map = new HashMap<>();
        map.put(MSG, errorMsg);
        map.put(RESULT, errorCode);
        map.put(DATA, null);
        return map;
    }

}