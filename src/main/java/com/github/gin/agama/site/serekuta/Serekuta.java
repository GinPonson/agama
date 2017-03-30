package com.github.gin.agama.site.serekuta;

/**
 * @author  GinPonson
 */
public interface Serekuta {

    Serekuta select(String css);

    Serekuta find(String nodeExp);

    String text();

    String attr(String attr);

    Serekuta first();

    Serekuta last();

}
