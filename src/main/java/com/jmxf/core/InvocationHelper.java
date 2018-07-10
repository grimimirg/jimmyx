package com.jmxf.core;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

import com.jmxf.core.annotation.ParamBody;
import com.jmxf.core.annotation.ParamPath;
import com.jmxf.core.annotation.ParamQuery;

import io.vavr.Tuple3;
import io.vavr.collection.List;
import io.vertx.core.MultiMap;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.RoutingContext;
import jodd.typeconverter.TypeConverterManager;

/**
 * @author Andrea_Grimandi
 */
public class InvocationHelper {

    /**
     * @param service
     * @param method
     * @param routingContext
     * @return
     * @throws Exception
     */
    public Object invokeMethod(Object service, Method method, RoutingContext routingContext) throws Exception {
        MultiMap queryParams = routingContext.queryParams();
        Map<String, String> pathParams = routingContext.pathParams();
        Buffer body = routingContext.getBody();

        // 1. type, 2. name, 3. value
        List<Tuple3<Class<?>, String, Object>> list = List.empty();

        for (Parameter par : method.getParameters()) {
            ParamQuery paramQuery = par.getAnnotation(ParamQuery.class);
            if (paramQuery != null) {
                list = list.push(new Tuple3<Class<?>, String, Object>(par.getType(), paramQuery.value(),
                        queryParams.get(paramQuery.value())));
            }

            ParamPath paramPath = par.getAnnotation(ParamPath.class);
            if (paramPath != null) {
                list = list.push(new Tuple3<Class<?>, String, Object>(par.getType(), paramPath.value(),
                        pathParams.get(paramPath.value())));
            }

            ParamBody annotation = par.getAnnotation(ParamBody.class);
            if (annotation != null) {
                list = list.push(new Tuple3<Class<?>, String, Object>(par.getType(), annotation.value(), body));
            }
        }

        return method.invoke(service, list.reverse()
                .map(mapper -> TypeConverterManager.lookup(mapper._1()).convert(mapper._3())).toJavaArray());
    }

}
