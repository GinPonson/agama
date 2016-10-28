package bilibili;

import com.github.gin.agama.annotation.ChildItem;
import com.github.gin.agama.annotation.Xpath;
import com.github.gin.agama.entity.AgamaEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GinPonson on 10/15/2016.
 */
public class BiliBili extends AgamaEntity {

    @ChildItem(BiliBiliVedio.class)
    @Xpath("//div[@class='l-item']")
    private List<BiliBiliVedio> vedios = new ArrayList<>();

    public List<BiliBiliVedio> getVedios() {
        return vedios;
    }

    public void setVedios(List<BiliBiliVedio> vedios) {
        this.vedios = vedios;
    }
}
