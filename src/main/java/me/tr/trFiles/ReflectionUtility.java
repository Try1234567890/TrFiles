package me.tr.trFiles;

import me.tr.trFiles.configuration.implementations.FileConfiguration;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;

public class ReflectionUtility {

    private ReflectionUtility() {
    }

    /**
     * Create an instance of provided class;
     * <p>
     * The instance will be created via {@link java.lang.reflect}.
     * This method got the constructor with provided parameters types,
     * make it accessible, with {@link Constructor#setAccessible(boolean)} method,
     * and return the result of {@link Constructor#newInstance(Object...)}.
     *
     * @param reference  The class to create an instance of.
     * @param parameters The parameters to provide while instantiation.
     * @param <T>        The object to return, that correspond to class type.
     * @return The instance of class if no error occurs;
     * @throws RuntimeException if an error occurs while running,
     *                          NoSuchMethod, Instantiation Error, IllegalAccess or InvocationTarget Error.
     */
    public static <T> T createInstance(Class<T> reference, Object... parameters) {
        if (parameters == null) parameters = new Object[0];
        Validator.isNull(reference, "Reference cannot be null while creating a new instance of it.");
        try {
            Constructor<T> constructor = reference.getDeclaredConstructor(Arrays.stream(parameters).map(Object::getClass).toArray(Class[]::new));
            constructor.setAccessible(true);
            return constructor.newInstance(parameters);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new RuntimeException("An error occurs while creating a new instance of " + reference.getSimpleName() + (FileConfiguration.class.isAssignableFrom(reference) ? ".\nThis class is assignable from FileConfiguration, make sure your class has a constructor without parameters." : ""), e);
        }
    }

    /**
     * Determines whether the object identified by the given fully qualified name is a class.
     * <p>
     * This method attempts to load the class using {@link Class#forName(String)}.
     * If the class exists, {@code true} is returned; otherwise, {@code false}.
     *
     * @param className the fully qualified name of the object to check
     * @return {@code true} if the object exists and is a class, {@code false} otherwise
     */
    public static boolean isClass(String className) {
        String resourcePath = className.replace('.', '/');
        try {
            Class.forName(resourcePath);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * Determines whether the given fully qualified name represents a package.
     * <p>
     * This method checks the classpath for the existence of a directory corresponding
     * to the package path. If such a directory is found, {@code true} is returned; otherwise, {@code false}.
     *
     * @param packageName the fully qualified name of the package to check
     * @return {@code true} if the package exists in the classpath, {@code false} otherwise
     */
    public static boolean isPackage(String packageName) {
        String resourcePath = packageName.replace('.', '/');
        try {
            Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(resourcePath);
            return resources.hasMoreElements();
        } catch (IOException e) {
            return false;
        }
    }
}
