package org.example.tests.extensions;

import org.junit.jupiter.api.extension.*;

import java.lang.reflect.Method;

public class PrivateMethodTestsExtension implements BeforeEachCallback {
    @Override
    public void beforeEach(ExtensionContext context) {
        context.getTestMethod().ifPresent(testMethod -> {
            TestPrivateMethod annotation = testMethod.getAnnotation(TestPrivateMethod.class);
            if (annotation != null) {
                invokePrivateMethod(annotation);
            }
        });
    }

    private void invokePrivateMethod(TestPrivateMethod annotation) {
        try {
            Class<?> clazz = Class.forName(annotation.className());

            Method method = clazz.getDeclaredMethod(annotation.methodName(), annotation.paramTypes());
            method.setAccessible(true);

        } catch (Exception e) {
            throw new RuntimeException("Failed to invoke private method", e);
        }
    }
}
