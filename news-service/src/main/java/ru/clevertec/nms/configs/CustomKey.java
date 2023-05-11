package ru.clevertec.nms.configs;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.stereotype.Component;
import ru.clevertec.nms.models.BaseEntity;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomKey implements KeyGenerator {
    @Override
    public Object generate(Object target, Method method, Object... params) {

        List<SimpleKey> keys = new ArrayList<>();
        List<Long> list = (List<Long>) params[0];
        System.out.println(list);
        return list;
    }
}
