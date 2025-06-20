package uz.dev.streamingservice.service.template;

/**
 * Created by: asrorbek
 * DateTime: 6/18/25 15:38
 **/

public interface BaseService<T, ID> {
    T getById(ID id);

    void create(T t);

    void update(T t, ID id);

    void delete(ID id);

}
