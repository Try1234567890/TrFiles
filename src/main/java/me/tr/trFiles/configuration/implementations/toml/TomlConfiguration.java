package me.tr.trFiles.configuration.implementations.toml;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.toml.TomlMapper;
import me.tr.trFiles.configuration.ConfigRegistry;
import me.tr.trFiles.configuration.implementations.FileConfiguration;
import me.tr.trFiles.configuration.memory.implementations.MemoryTomlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Path;
import java.util.zip.ZipFile;

public class TomlConfiguration extends FileConfiguration {
    private TomlOptions options;

    private TomlConfiguration() {}

    @Override
    public String[] getExtensions() {
        return new String[]{".toml"};
    }

    @Override
    public TomlConfiguration newConfiguration(File file) {
        return TomlConfiguration.emptyToml(file);
    }

    @Override
    public MemoryTomlConfiguration newMemoryConfiguration() {
        return MemoryTomlConfiguration.emptyToml(buildToml());
    }


    @Override
    public void register() {
        ConfigRegistry.register(getClass());
    }

    @Override
    public MemoryTomlConfiguration getConfiguration() {
        return (MemoryTomlConfiguration) super.getConfiguration();
    }

    public static TomlConfiguration emptyToml(File file) {
        return (TomlConfiguration) emptyConfig(file, TomlConfiguration.class);
    }

    public static TomlConfiguration fromReader(File file, Reader reader) {
        return (TomlConfiguration) fromReader(file, reader, TomlConfiguration.class);
    }


    public static TomlConfiguration fromInputStream(File file, InputStream is) {
        return (TomlConfiguration) fromInputStream(file, is, TomlConfiguration.class);
    }

    public static TomlConfiguration fromFile(File file) {
        return (TomlConfiguration) fromFile(file, TomlConfiguration.class);
    }

    public static TomlConfiguration fromPath(Path path) {
        return (TomlConfiguration) fromPath(path, TomlConfiguration.class);
    }

    public static TomlConfiguration fromString(String path) {
        return (TomlConfiguration) fromString(path, TomlConfiguration.class);
    }

    public static TomlConfiguration fromContent(File file, String content) {
        return (TomlConfiguration) fromContent(file, content, TomlConfiguration.class);
    }

    public static TomlConfiguration fromArchive(ZipFile archive, File inside, File to) {
        return (TomlConfiguration) fromArchive(archive, inside, to, TomlConfiguration.class);
    }

    public static TomlConfiguration fromBytes(File file, byte[] bytes) {
        return (TomlConfiguration) fromBytes(file, bytes, TomlConfiguration.class);
    }


    @Override
    public TomlOptions options() {
        if (options == null) {
            options = new TomlOptions(this);
        }
        return options;
    }

    public void options(TomlOptions options) {
        this.options = options;
    }


    private TomlMapper buildToml() {
        return TomlMapper.builder()
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
                .configure(SerializationFeature.WRAP_ROOT_VALUE, options().isWrapRootValue())
                .configure(SerializationFeature.INDENT_OUTPUT, options().isIndentOutput())
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, options().isFailOnEmptyBeans())
                .configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, options().isFailOnSelfReferences())
                .configure(SerializationFeature.WRAP_EXCEPTIONS, options().isWrapExceptions())
                .configure(SerializationFeature.FAIL_ON_UNWRAPPED_TYPE_IDENTIFIERS, options().isFailOnUnwrappedTypeIdentifiers())
                .configure(SerializationFeature.WRITE_SELF_REFERENCES_AS_NULL, options().isWriteSelfReferencesAsNull())
                .configure(SerializationFeature.CLOSE_CLOSEABLE, options().isCloseCloseable())
                .configure(SerializationFeature.FLUSH_AFTER_WRITE_VALUE, options().isFlushAfterWriteValue())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, options().isWriteDatesAsTimestamps())
                .configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, options().isWriteDateKeysAsTimestamps())
                .configure(SerializationFeature.WRITE_DATES_WITH_ZONE_ID, options().isWriteDatesWithZoneId())
                .configure(SerializationFeature.WRITE_DATES_WITH_CONTEXT_TIME_ZONE, options().isWriteDatesWithContextTimeZone())
                .configure(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS, options().isWriteDurationsAsTimestamps())
                .configure(SerializationFeature.WRITE_CHAR_ARRAYS_AS_JSON_ARRAYS, options().isWriteCharArraysAsJsonArrays())
                .configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, options().isWriteEnumsUsingToString())
                .configure(SerializationFeature.WRITE_ENUMS_USING_INDEX, options().isWriteEnumsUsingIndex())
                .configure(SerializationFeature.WRITE_ENUM_KEYS_USING_INDEX, options().isWriteEnumKeysUsingIndex())
                .configure(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED, options().isWriteSingleElemArraysUnwrapped())
                .configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, options().isWriteDateTimestampsAsNanoseconds())
                .configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, options().isOrderMapEntriesByKeys())
                .configure(SerializationFeature.FAIL_ON_ORDER_MAP_BY_INCOMPARABLE_KEY, options().isFailOnOrderMapByIncomparableKey())
                .configure(SerializationFeature.EAGER_SERIALIZER_FETCH, options().isEagerSerializerFetch())
                .configure(SerializationFeature.USE_EQUALITY_FOR_OBJECT_ID, options().isUseEqualityForObjectId())
                .build();
    }
}
