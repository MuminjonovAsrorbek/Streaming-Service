package uz.dev.streamingservice.utils;

/**
 * Created by: asrorbek
 * DateTime: 6/13/25 18:17
 **/

public class CommonUtil {

    public static <T> T getOrDefault(T value, T def) {

        return value == null ? def : value;

    }

}
