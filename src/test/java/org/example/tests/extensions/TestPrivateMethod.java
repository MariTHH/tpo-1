package org.example.tests.extensions;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TestPrivateMethod {
    String className();

    String methodName();

    Class<?>[] paramTypes() default {};

}
