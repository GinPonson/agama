package cloud;

import com.github.gin.agama.site.Request;

/**
 * Created by FSTMP on 2016/10/31.
 */
public class RequestUtil {

    public static Request createRequest(){
        Request request = new Request();
        request.getHeaders().put("X-Requested-With","XMLHttpRequest");
        request.getHeaders().put("Accept", "application/json, text/javascript, */*; q=0.01");
        request.getHeaders().put("Referer", "https://yun.baidu.com/share/home?uk=325913312#category/type=0");
        request.getHeaders().put("Accept-Language", "zh-CN");
        return request;
    }
}
