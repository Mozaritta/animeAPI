package api.mozaritta.anime.responses;

import api.mozaritta.anime.entities.Anime;
import io.swagger.v3.core.util.Json;
import lombok.Data;

import java.lang.reflect.Array;
import java.util.ArrayList;

@Data
public class ResponseHandler {
    Integer code = 0;
    Object data = new Object();
    String message = "";

    public ResponseHandler(Integer code, Object data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }
}
