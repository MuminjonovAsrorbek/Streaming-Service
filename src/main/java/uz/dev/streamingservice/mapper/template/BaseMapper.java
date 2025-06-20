package uz.dev.streamingservice.mapper.template;

/**
 * Created by: asrorbek
 * DateTime: 6/18/25 16:36
 **/

public interface BaseMapper<T, R> {

    T toEntity(R r);

    R toDTO(T t);

}
