package me.tr.trFiles.configuration.implementations.properties;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper;
import me.tr.trFiles.configuration.implementations.FileConfiguration;
import me.tr.trFiles.configuration.memory.MemoryConfiguration;
import me.tr.trFiles.configuration.memory.implementations.MemoryPropertiesConfiguration;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Path;
import java.util.Map;
import java.util.zip.ZipFile;

public class PropertiesConfiguration extends FileConfiguration {
    private MemoryPropertiesConfiguration configuration;
    private PropertiesOptions options;

    @Override
    public void loadFromString(String contents) {
        this.configuration = new MemoryPropertiesConfiguration(buildProperties());
        configuration.loadFromString(contents);
    }

    @Override
    public void setConfiguration(MemoryConfiguration configuration) {
        if (!(configuration instanceof MemoryPropertiesConfiguration)) return;
        this.configuration = (MemoryPropertiesConfiguration) configuration;
    }

    @Override
    public MemoryPropertiesConfiguration getConfiguration() {
        return configuration;
    }

    @Override
    public String[] getExtensions() {
        return new String[]{".properties"};
    }

    public PropertiesConfiguration(File file, Map<String, Object> map) {
        super(file, map);
    }

    public PropertiesConfiguration(File file, Reader reader) {
        super(file, reader);
    }

    public PropertiesConfiguration(File file, InputStream is) {
        super(file, is);
    }

    public PropertiesConfiguration(File file) {
        super(file);
    }

    public PropertiesConfiguration(Path path) {
        super(path);
    }

    public PropertiesConfiguration(String path) {
        super(path);
    }

    public PropertiesConfiguration(ZipFile archive, File inside, File to) {
        super(archive, inside, to);
    }

    public PropertiesConfiguration(File archive, File inside, File to) {
        super(archive, inside, to);
    }

    public static PropertiesConfiguration fromMap(File file, Map<String, Object> map) {
        return new PropertiesConfiguration(file, map);
    }

    public static PropertiesConfiguration fromReader(File file, Reader reader) {
        return new PropertiesConfiguration(file, reader);
    }

    public static PropertiesConfiguration fromInputStream(File file, InputStream is) {
        return new PropertiesConfiguration(file, is);
    }

    public static PropertiesConfiguration fromFile(File file) {
        return new PropertiesConfiguration(file);
    }

    public static PropertiesConfiguration fromPath(Path path) {
        return new PropertiesConfiguration(path);
    }

    public static PropertiesConfiguration fromString(String path) {
        return new PropertiesConfiguration(path);
    }

    public static PropertiesConfiguration fromArchive(ZipFile archive, File inside, File to) {
        return new PropertiesConfiguration(archive, inside, to);
    }

    public static PropertiesConfiguration fromArchive(File archive, File inside, File to) {
        return new PropertiesConfiguration(archive, inside, to);
    }

    private JavaPropsMapper buildProperties() {
        return JavaPropsMapper.builder()
                .configure(MapperFeature.USE_ANNOTATIONS, options().isUseAnnotations())
                .configure(MapperFeature.USE_GETTERS_AS_SETTERS, options().isUseGettersAsSetters())
                .configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, options().isPropagateTransientMarker())
                .configure(MapperFeature.AUTO_DETECT_CREATORS, options().isAutoDetectCreators())
                .configure(MapperFeature.AUTO_DETECT_FIELDS, options().isAutoDetectFields())
                .configure(MapperFeature.AUTO_DETECT_GETTERS, options().isAutoDetectGetters())
                .configure(MapperFeature.AUTO_DETECT_IS_GETTERS, options().isAutoDetectIsGetters())
                .configure(MapperFeature.AUTO_DETECT_SETTERS, options().isAutoDetectSetters())
                .configure(MapperFeature.REQUIRE_SETTERS_FOR_GETTERS, options().isRequireSettersForGetters())
                .configure(MapperFeature.ALLOW_FINAL_FIELDS_AS_MUTATORS, options().isAllowFinalFieldsAsMutators())
                .configure(MapperFeature.INFER_PROPERTY_MUTATORS, options().isInferPropertyMutators())
                .configure(MapperFeature.INFER_CREATOR_FROM_CONSTRUCTOR_PROPERTIES, options().isInferCreatorFromConstructorProperties())
                .configure(MapperFeature.ALLOW_VOID_VALUED_PROPERTIES, options().isAllowVoidValuedProperties())
                .configure(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS, options().isCanOverrideAccessModifiers())
                .configure(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS, options().isOverridePublicAccessModifiers())
                .configure(MapperFeature.INVERSE_READ_WRITE_ACCESS, options().isInverseReadWriteAccess())
                .configure(MapperFeature.USE_STATIC_TYPING, options().isUseStaticTyping())
                .configure(MapperFeature.USE_BASE_TYPE_AS_DEFAULT_IMPL, options().isUseBaseTypeAsDefaultImpl())
                .configure(MapperFeature.INFER_BUILDER_TYPE_BINDINGS, options().isInferBuilderTypeBindings())
                .configure(MapperFeature.REQUIRE_TYPE_ID_FOR_SUBTYPES, options().isRequireTypeIdForSubtypes())
                .configure(MapperFeature.DEFAULT_VIEW_INCLUSION, options().isDefaultViewInclusion())
                .configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, options().isSortPropertiesAlphabetically())
                .configure(MapperFeature.SORT_CREATOR_PROPERTIES_FIRST, options().isSortCreatorPropertiesFirst())
                .configure(MapperFeature.SORT_CREATOR_PROPERTIES_BY_DECLARATION_ORDER, options().isSortCreatorPropertiesByDeclarationOrder())
                .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, options().isAcceptCaseInsensitiveProperties())
                .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, options().isAcceptCaseInsensitiveEnums())
                .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_VALUES, options().isAcceptCaseInsensitiveValues())
                .configure(MapperFeature.USE_WRAPPER_NAME_AS_PROPERTY_NAME, options().isUseWrapperNameAsPropertyName())
                .configure(MapperFeature.USE_STD_BEAN_NAMING, options().isUseStdBeanNaming())
                .configure(MapperFeature.ALLOW_EXPLICIT_PROPERTY_RENAMING, options().isAllowExplicitPropertyRenaming())
                .configure(MapperFeature.ALLOW_IS_GETTERS_FOR_NON_BOOLEAN, options().isAllowIsGettersForNonBoolean())
                .configure(MapperFeature.ALLOW_COERCION_OF_SCALARS, options().isAllowCoercionOfScalars())
                .configure(MapperFeature.IGNORE_DUPLICATE_MODULE_REGISTRATIONS, options().isIgnoreDuplicateModuleRegistrations())
                .configure(MapperFeature.IGNORE_MERGE_FOR_UNMERGEABLE, options().isIgnoreMergeForUnmergeable())
                .configure(MapperFeature.BLOCK_UNSAFE_POLYMORPHIC_BASE_TYPES, options().isBlockUnsafePolymorphicBaseTypes())
                .configure(MapperFeature.APPLY_DEFAULT_VALUES, options().isApplyDefaultValues())
                .configure(MapperFeature.REQUIRE_HANDLERS_FOR_JAVA8_OPTIONALS, options().isRequireHandlersForJava8Optionals())
                .configure(SerializationFeature.INDENT_OUTPUT, options().isIndentOutput())
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, options().isFailOnEmptyBeans())
                .configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, options().isFailOnSelfReferences())
                .configure(SerializationFeature.WRAP_EXCEPTIONS, options().isWrapExceptions())
                .configure(SerializationFeature.WRITE_SELF_REFERENCES_AS_NULL, options().isWriteSelfReferencesAsNull())
                .configure(SerializationFeature.FAIL_ON_UNWRAPPED_TYPE_IDENTIFIERS, options().isFailOnUnwrappedTypeIdentifiers())
                .build();
    }


    @Override
    public PropertiesOptions options() {
        if (options == null) {
            options = new PropertiesOptions(this);
        }
        return options;
    }
}
