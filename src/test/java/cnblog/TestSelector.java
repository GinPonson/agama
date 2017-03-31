package cnblog;

import com.github.gin.agama.site.serekuta.JsoupSerekuta;
import com.github.gin.agama.site.serekuta.Serekuta;
import com.github.gin.agama.site.serekuta.XpathSerekuta;

import java.util.Optional;

/**
 * Created by FSTMP on 2017/3/14.
 */
public class TestSelector {

    public static void main(String[] args){

        String html = "<ul>\n" +
                "    <li>list item 1</li>\n" +
                "    <li>list item 2</li>\n" +
                "    <li>list item 3</li>\n" +
                "    <li>list item 4</li>\n" +
                "    <li>list item 5</li>\n" +
                "</ul>";
        Serekuta serekuta = new JsoupSerekuta(html);
        System.out.println(serekuta.select("li"));

        Serekuta serekuta1 = new XpathSerekuta(html);
        System.out.println(serekuta1.select("//li"));

    }
}
