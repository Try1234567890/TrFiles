package me.tr.trFiles.configuration.implementations.properties;

import me.tr.trFiles.configuration.implementations.FileOptions;

public class PropertiesOptions extends FileOptions {
    private boolean useAnnotations = true;
    private boolean useGettersAsSetters = true;
    private boolean propagateTransientMarker = false;
    private boolean autoDetectCreators = true;
    private boolean autoDetectFields = true;
    private boolean autoDetectGetters = true;
    private boolean autoDetectIsGetters = true;
    private boolean autoDetectSetters = true;
    private boolean requireSettersForGetters = false;
    private boolean allowFinalFieldsAsMutators = true;
    private boolean inferPropertyMutators = true;
    private boolean inferCreatorFromConstructorProperties = true;
    private boolean allowVoidValuedProperties = false;
    private boolean canOverrideAccessModifiers = true;
    private boolean overridePublicAccessModifiers = true;
    private boolean inverseReadWriteAccess = false;
    private boolean useStaticTyping = false;
    private boolean useBaseTypeAsDefaultImpl = false;
    private boolean inferBuilderTypeBindings = true;
    private boolean requireTypeIdForSubtypes = true;
    private boolean defaultViewInclusion = true;
    private boolean sortPropertiesAlphabetically = false;
    private boolean sortCreatorPropertiesFirst = true;
    private boolean sortCreatorPropertiesByDeclarationOrder = false;
    private boolean acceptCaseInsensitiveProperties = false;
    private boolean acceptCaseInsensitiveEnums = false;
    private boolean acceptCaseInsensitiveValues = false;
    private boolean useWrapperNameAsPropertyName = false;
    private boolean useStdBeanNaming = false;
    private boolean allowExplicitPropertyRenaming = false;
    private boolean allowIsGettersForNonBoolean = false;
    private boolean allowCoercionOfScalars = true;
    private boolean ignoreDuplicateModuleRegistrations = true;
    private boolean ignoreMergeForUnmergeable = true;
    private boolean blockUnsafePolymorphicBaseTypes = false;
    private boolean applyDefaultValues = true;
    private boolean requireHandlersForJava8Optionals = true;

    private boolean indentOutput = false;
    private boolean failOnEmptyBeans = true;
    private boolean failOnSelfReferences = true;
    private boolean wrapExceptions = true;
    private boolean failOnUnwrappedTypeIdentifiers = true;
    private boolean writeSelfReferencesAsNull = false;

    protected PropertiesOptions(PropertiesConfiguration configuration) {
        super(configuration, "#", "");
    }

    public boolean isUseAnnotations() {
        return useAnnotations;
    }

    public void setUseAnnotations(boolean useAnnotations) {
        this.useAnnotations = useAnnotations;
    }

    public boolean isUseGettersAsSetters() {
        return useGettersAsSetters;
    }

    public void setUseGettersAsSetters(boolean useGettersAsSetters) {
        this.useGettersAsSetters = useGettersAsSetters;
    }

    public boolean isPropagateTransientMarker() {
        return propagateTransientMarker;
    }

    public void setPropagateTransientMarker(boolean propagateTransientMarker) {
        this.propagateTransientMarker = propagateTransientMarker;
    }

    public boolean isAutoDetectCreators() {
        return autoDetectCreators;
    }

    public void setAutoDetectCreators(boolean autoDetectCreators) {
        this.autoDetectCreators = autoDetectCreators;
    }

    public boolean isAutoDetectFields() {
        return autoDetectFields;
    }

    public void setAutoDetectFields(boolean autoDetectFields) {
        this.autoDetectFields = autoDetectFields;
    }

    public boolean isAutoDetectGetters() {
        return autoDetectGetters;
    }

    public void setAutoDetectGetters(boolean autoDetectGetters) {
        this.autoDetectGetters = autoDetectGetters;
    }

    public boolean isAutoDetectIsGetters() {
        return autoDetectIsGetters;
    }

    public void setAutoDetectIsGetters(boolean autoDetectIsGetters) {
        this.autoDetectIsGetters = autoDetectIsGetters;
    }

    public boolean isAutoDetectSetters() {
        return autoDetectSetters;
    }

    public void setAutoDetectSetters(boolean autoDetectSetters) {
        this.autoDetectSetters = autoDetectSetters;
    }

    public boolean isRequireSettersForGetters() {
        return requireSettersForGetters;
    }

    public void setRequireSettersForGetters(boolean requireSettersForGetters) {
        this.requireSettersForGetters = requireSettersForGetters;
    }

    public boolean isAllowFinalFieldsAsMutators() {
        return allowFinalFieldsAsMutators;
    }

    public void setAllowFinalFieldsAsMutators(boolean allowFinalFieldsAsMutators) {
        this.allowFinalFieldsAsMutators = allowFinalFieldsAsMutators;
    }

    public boolean isInferPropertyMutators() {
        return inferPropertyMutators;
    }

    public void setInferPropertyMutators(boolean inferPropertyMutators) {
        this.inferPropertyMutators = inferPropertyMutators;
    }

    public boolean isInferCreatorFromConstructorProperties() {
        return inferCreatorFromConstructorProperties;
    }

    public void setInferCreatorFromConstructorProperties(boolean inferCreatorFromConstructorProperties) {
        this.inferCreatorFromConstructorProperties = inferCreatorFromConstructorProperties;
    }

    public boolean isAllowVoidValuedProperties() {
        return allowVoidValuedProperties;
    }

    public void setAllowVoidValuedProperties(boolean allowVoidValuedProperties) {
        this.allowVoidValuedProperties = allowVoidValuedProperties;
    }

    public boolean isCanOverrideAccessModifiers() {
        return canOverrideAccessModifiers;
    }

    public void setCanOverrideAccessModifiers(boolean canOverrideAccessModifiers) {
        this.canOverrideAccessModifiers = canOverrideAccessModifiers;
    }

    public boolean isOverridePublicAccessModifiers() {
        return overridePublicAccessModifiers;
    }

    public void setOverridePublicAccessModifiers(boolean overridePublicAccessModifiers) {
        this.overridePublicAccessModifiers = overridePublicAccessModifiers;
    }

    public boolean isInverseReadWriteAccess() {
        return inverseReadWriteAccess;
    }

    public void setInverseReadWriteAccess(boolean inverseReadWriteAccess) {
        this.inverseReadWriteAccess = inverseReadWriteAccess;
    }

    public boolean isUseStaticTyping() {
        return useStaticTyping;
    }

    public void setUseStaticTyping(boolean useStaticTyping) {
        this.useStaticTyping = useStaticTyping;
    }

    public boolean isUseBaseTypeAsDefaultImpl() {
        return useBaseTypeAsDefaultImpl;
    }

    public void setUseBaseTypeAsDefaultImpl(boolean useBaseTypeAsDefaultImpl) {
        this.useBaseTypeAsDefaultImpl = useBaseTypeAsDefaultImpl;
    }

    public boolean isInferBuilderTypeBindings() {
        return inferBuilderTypeBindings;
    }

    public void setInferBuilderTypeBindings(boolean inferBuilderTypeBindings) {
        this.inferBuilderTypeBindings = inferBuilderTypeBindings;
    }

    public boolean isRequireTypeIdForSubtypes() {
        return requireTypeIdForSubtypes;
    }

    public void setRequireTypeIdForSubtypes(boolean requireTypeIdForSubtypes) {
        this.requireTypeIdForSubtypes = requireTypeIdForSubtypes;
    }

    public boolean isDefaultViewInclusion() {
        return defaultViewInclusion;
    }

    public void setDefaultViewInclusion(boolean defaultViewInclusion) {
        this.defaultViewInclusion = defaultViewInclusion;
    }

    public boolean isSortPropertiesAlphabetically() {
        return sortPropertiesAlphabetically;
    }

    public void setSortPropertiesAlphabetically(boolean sortPropertiesAlphabetically) {
        this.sortPropertiesAlphabetically = sortPropertiesAlphabetically;
    }

    public boolean isSortCreatorPropertiesFirst() {
        return sortCreatorPropertiesFirst;
    }

    public void setSortCreatorPropertiesFirst(boolean sortCreatorPropertiesFirst) {
        this.sortCreatorPropertiesFirst = sortCreatorPropertiesFirst;
    }

    public boolean isSortCreatorPropertiesByDeclarationOrder() {
        return sortCreatorPropertiesByDeclarationOrder;
    }

    public void setSortCreatorPropertiesByDeclarationOrder(boolean sortCreatorPropertiesByDeclarationOrder) {
        this.sortCreatorPropertiesByDeclarationOrder = sortCreatorPropertiesByDeclarationOrder;
    }

    public boolean isAcceptCaseInsensitiveProperties() {
        return acceptCaseInsensitiveProperties;
    }

    public void setAcceptCaseInsensitiveProperties(boolean acceptCaseInsensitiveProperties) {
        this.acceptCaseInsensitiveProperties = acceptCaseInsensitiveProperties;
    }

    public boolean isAcceptCaseInsensitiveEnums() {
        return acceptCaseInsensitiveEnums;
    }

    public void setAcceptCaseInsensitiveEnums(boolean acceptCaseInsensitiveEnums) {
        this.acceptCaseInsensitiveEnums = acceptCaseInsensitiveEnums;
    }

    public boolean isAcceptCaseInsensitiveValues() {
        return acceptCaseInsensitiveValues;
    }

    public void setAcceptCaseInsensitiveValues(boolean acceptCaseInsensitiveValues) {
        this.acceptCaseInsensitiveValues = acceptCaseInsensitiveValues;
    }

    public boolean isUseWrapperNameAsPropertyName() {
        return useWrapperNameAsPropertyName;
    }

    public void setUseWrapperNameAsPropertyName(boolean useWrapperNameAsPropertyName) {
        this.useWrapperNameAsPropertyName = useWrapperNameAsPropertyName;
    }

    public boolean isUseStdBeanNaming() {
        return useStdBeanNaming;
    }

    public void setUseStdBeanNaming(boolean useStdBeanNaming) {
        this.useStdBeanNaming = useStdBeanNaming;
    }

    public boolean isAllowExplicitPropertyRenaming() {
        return allowExplicitPropertyRenaming;
    }

    public void setAllowExplicitPropertyRenaming(boolean allowExplicitPropertyRenaming) {
        this.allowExplicitPropertyRenaming = allowExplicitPropertyRenaming;
    }

    public boolean isAllowIsGettersForNonBoolean() {
        return allowIsGettersForNonBoolean;
    }

    public void setAllowIsGettersForNonBoolean(boolean allowIsGettersForNonBoolean) {
        this.allowIsGettersForNonBoolean = allowIsGettersForNonBoolean;
    }

    public boolean isAllowCoercionOfScalars() {
        return allowCoercionOfScalars;
    }

    public void setAllowCoercionOfScalars(boolean allowCoercionOfScalars) {
        this.allowCoercionOfScalars = allowCoercionOfScalars;
    }

    public boolean isIgnoreDuplicateModuleRegistrations() {
        return ignoreDuplicateModuleRegistrations;
    }

    public void setIgnoreDuplicateModuleRegistrations(boolean ignoreDuplicateModuleRegistrations) {
        this.ignoreDuplicateModuleRegistrations = ignoreDuplicateModuleRegistrations;
    }

    public boolean isIgnoreMergeForUnmergeable() {
        return ignoreMergeForUnmergeable;
    }

    public void setIgnoreMergeForUnmergeable(boolean ignoreMergeForUnmergeable) {
        this.ignoreMergeForUnmergeable = ignoreMergeForUnmergeable;
    }

    public boolean isBlockUnsafePolymorphicBaseTypes() {
        return blockUnsafePolymorphicBaseTypes;
    }

    public void setBlockUnsafePolymorphicBaseTypes(boolean blockUnsafePolymorphicBaseTypes) {
        this.blockUnsafePolymorphicBaseTypes = blockUnsafePolymorphicBaseTypes;
    }

    public boolean isApplyDefaultValues() {
        return applyDefaultValues;
    }

    public void setApplyDefaultValues(boolean applyDefaultValues) {
        this.applyDefaultValues = applyDefaultValues;
    }

    public boolean isRequireHandlersForJava8Optionals() {
        return requireHandlersForJava8Optionals;
    }

    public void setRequireHandlersForJava8Optionals(boolean requireHandlersForJava8Optionals) {
        this.requireHandlersForJava8Optionals = requireHandlersForJava8Optionals;
    }

    public boolean isIndentOutput() {
        return indentOutput;
    }

    public void setIndentOutput(boolean indentOutput) {
        this.indentOutput = indentOutput;
    }

    public boolean isFailOnEmptyBeans() {
        return failOnEmptyBeans;
    }

    public void setFailOnEmptyBeans(boolean failOnEmptyBeans) {
        this.failOnEmptyBeans = failOnEmptyBeans;
    }

    public boolean isFailOnSelfReferences() {
        return failOnSelfReferences;
    }

    public void setFailOnSelfReferences(boolean failOnSelfReferences) {
        this.failOnSelfReferences = failOnSelfReferences;
    }

    public boolean isWrapExceptions() {
        return wrapExceptions;
    }

    public void setWrapExceptions(boolean wrapExceptions) {
        this.wrapExceptions = wrapExceptions;
    }

    public boolean isFailOnUnwrappedTypeIdentifiers() {
        return failOnUnwrappedTypeIdentifiers;
    }

    public void setFailOnUnwrappedTypeIdentifiers(boolean failOnUnwrappedTypeIdentifiers) {
        this.failOnUnwrappedTypeIdentifiers = failOnUnwrappedTypeIdentifiers;
    }

    public boolean isWriteSelfReferencesAsNull() {
        return writeSelfReferencesAsNull;
    }

    public void setWriteSelfReferencesAsNull(boolean writeSelfReferencesAsNull) {
        this.writeSelfReferencesAsNull = writeSelfReferencesAsNull;
    }
}
