package me.tr.trFiles.configuration.implementations.toml;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.toml.TomlMapper;
import me.tr.trFiles.configuration.implementations.FileConfiguration;
import me.tr.trFiles.configuration.implementations.Implementations;
import me.tr.trFiles.configuration.memory.MemoryConfiguration;
import me.tr.trFiles.general.utility.Validate;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class TomlConfiguration extends FileConfiguration {
    private TomlMapper mapper;
    private TomlOptions options;


    @Override
    protected FileConfiguration loadFromString(String contents) {
        Validate.notNull(contents != null, "Contents cannot be null");
        Map<?, ?> input;
        try {
            input = mapper.readValue(contents, Map.class);
        } catch (IOException e) {
            throw new RuntimeException("Error while loading configuration. ", e);
        }
        convertMapsToSections(input, this);
        setImplementation(Implementations.TOML);
        return this;
    }

    @Override
    protected String saveToString() {
        String contents;
        try {
            contents = mapper.writeValueAsString(getValues(true));
        } catch (IOException e) {
            throw new RuntimeException("Error while saving configuration. ", e);
        }
        setImplementation(Implementations.TOML);
        return contents;
    }

    @Override
    protected TomlConfiguration newInstance() {
        return new TomlConfiguration();
    }


    /**
     * Create a new empty instance of {@link TomlConfiguration}.
     */
    public TomlConfiguration() {
        buildToml();
    }

    /**
     * Create a new instance of {@link TomlConfiguration} with provided configuration content.
     */
    public TomlConfiguration(MemoryConfiguration configuration) {
        super(configuration);
        buildToml();
    }


    public static TomlConfiguration from(File file) {
        if (!Implementations.TOML.isValid(file)) {
            throw new IllegalArgumentException(file.getPath() + " is not a valid TOML file.");
        }
        return (TomlConfiguration) FileConfiguration.from(file);
    }

    public static TomlConfiguration fromMap(Map<String, Object> map) {
        return (TomlConfiguration) fromMap(map, Implementations.XML);
    }


    private void buildToml() {
        setImplementation(Implementations.TOML);

        this.mapper = TomlMapper.builder()
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
    public TomlOptions options() {
        if (options == null) {
            options = new TomlOptions(this);
        }
        return options;
    }
}
