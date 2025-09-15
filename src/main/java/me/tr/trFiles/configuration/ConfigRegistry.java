package me.tr.trFiles.configuration;

import me.tr.trFiles.ReflectionUtility;
import me.tr.trFiles.Validator;
import me.tr.trFiles.configuration.implementations.FileConfiguration;
import me.tr.trFiles.configuration.management.FileUtility;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class ConfigRegistry {
    private ConfigRegistry() {
    }

    private static final Map<String, Class<? extends FileConfiguration>> knownConfigsClass = new HashMap<>();
    private static final Map<Class<? extends FileConfiguration>, FileConfiguration> knownConfigs = new HashMap<>();

    /**
     * Register a new {@link FileConfiguration} to the registry.
     * <p>
     * <b>If this method is not called for every non-default {@link FileConfiguration},
     * that {@link FileConfiguration} will not be recognized if used.
     * Make sure you register all implementations of {@link FileConfiguration}
     * at the start of your programs.
     * </b>
     *
     * @param configuration The FileConfiguration class to register.
     */
    public static void register(Class<? extends FileConfiguration> configuration) {
        Validator.isNull(configuration, "Provided configuration to register cannot be null");
        getKnownConfigsClass().put(configuration.getName(), configuration);
        getKnownConfigs().put(configuration, ReflectionUtility.createInstance(configuration));
    }

    /**
     * Get the {@code class} of the provided extension if exists.
     *
     * @param extension The extension to get {@code class} to.
     * @return The {@code class} if found, otherwise null.
     * @throws NullPointerException if the provided extension is null.
     */
    public static @Nullable Class<? extends FileConfiguration> getClassByExtension(String extension) {
        Validator.isNull(extension, "Provided extension  used to search Configuration cannot be null");
        FileConfiguration configuration = getConfigByExtension(extension);
        if (configuration == null) return null;
        return configuration.getClass();
    }

    /**
     * Get the {@link FileConfiguration} of the provided extension if exists.
     *
     * @param extension The extension to get {@link FileConfiguration} to.
     * @return The instance of {@link FileConfiguration} if found, otherwise null.
     * @throws NullPointerException if the provided extension is null.
     */
    public static @Nullable FileConfiguration getConfigByExtension(String extension) {
        Validator.isNull(extension, "Provided extension used to search Configuration cannot be null");
        for (FileConfiguration config : getKnownConfigs().values()) {
            if (Arrays.asList(config.getExtensions()).contains(extension)) {
                return config;
            }
        }
        return null;
    }

    /**
     * Get the {@link FileConfiguration} of the provided file if exists.
     * <p>
     * The provided file mustn't exist, but cannot be null.
     *
     * @param file The file to get {@link FileConfiguration} to.
     * @return The instance of {@link FileConfiguration} if found, otherwise null.
     * @throws NullPointerException if the provided file is null.
     */
    public static @Nullable FileConfiguration getConfigByFile(File file) {
        Validator.isNull(file, "Provided file used to search Configuration cannot be null");
        for (FileConfiguration config : getKnownConfigs().values()) {
            if (Arrays.asList(config.getExtensions()).contains(FileUtility.getExtension(file))
                    || Arrays.asList(config.getExtensions()).contains('.' + FileUtility.getExtension(file))) {
                return config;
            }
        }
        return null;
    }

    /**
     * @return All mapped FileConfiguration classes name
     * (Es: me.tr.trFiles.configuration.implementations.yaml.YamlConfiguration) with the correspond class.
     */
    private static Map<String, Class<? extends FileConfiguration>> getKnownConfigsClass() {
        return knownConfigsClass;
    }

    /**
     * @return All mapped FileConfiguration classes with an empty instance.
     */
    public static Map<Class<? extends FileConfiguration>, FileConfiguration> getKnownConfigs() {
        return knownConfigs;
    }

    /**
     * Automatically register all {@link FileConfiguration} found inside the provided package.
     *
     * @param packageName The package name where search the FileConfigurations;
     * @throws NullPointerException if the provided package name is null.
     * @throws RuntimeException     if an error occurs while reading the package, or if an error occurs while searching the class inside of it.
     */
    @SuppressWarnings("unchecked")
    public static void registerAll(String packageName) {
        Validator.isNull(packageName, "Provided package used to load all Configuration classes is null.");
        try (InputStream packageIn = ClassLoader.getSystemClassLoader().getResourceAsStream(packageName.replace('.', '/'))) {
            if (packageIn != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(packageIn));
                reader.lines()
                        .filter(line -> {
                            String className = packageName + '.' + line;
                            if (ReflectionUtility.isPackage(className)) {
                                registerAll(className);
                                return false;
                            }
                            return line.endsWith(".class");
                        })
                        .map(className -> getClass(className, packageName))
                        .filter(clazz -> FileConfiguration.class.isAssignableFrom(clazz)
                                && !Modifier.isAbstract(clazz.getModifiers())
                                && !Modifier.isInterface(clazz.getModifiers())
                        ).forEach(clazz -> register((Class<? extends FileConfiguration>) clazz));
            }
        } catch (IOException e) {
            throw new RuntimeException("An error occurs while searching package at: " + packageName, e);
        }
    }

    /**
     * Get the class by name inside the provided package.
     *
     * @param className   The class name to search.
     * @param packageName The package name where search the provided class name.
     * @return The class if any is found.
     * @throws RuntimeException     if the class is not found;
     * @throws NullPointerException if the at least one of the params is null.
     */
    private static Class<?> getClass(String className, String packageName) {
        Validator.isNull(className, "Provided class name used to load Configuration classes is null.");
        Validator.isNull(packageName, "Provided package name used to load Configuration classes is null.");
        String classPath = packageName + '.' + className.substring(0, className.lastIndexOf('.'));
        try {
            return Class.forName(classPath);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("An error occurs while searching class to: " + classPath, e);
        }
    }
}
