package me.tr.trfiles.file.configuration.implementations.toml;

import me.tr.trfiles.file.configuration.implementations.FileOptions;

public class TomlOptions extends FileOptions {
    private boolean wrapRootValue;
    private boolean indentOutput;
    private boolean failOnEmptyBeans;
    private boolean failOnSelfReferences;
    private boolean wrapExceptions;
    private boolean failOnUnwrappedTypeIdentifiers;
    private boolean writeSelfReferencesAsNull;
    private boolean closeCloseable;
    private boolean flushAfterWriteValue;
    private boolean writeDatesAsTimestamps;
    private boolean writeDateKeysAsTimestamps;
    private boolean writeDatesWithZoneId;
    private boolean writeDatesWithContextTimeZone;
    private boolean writeDurationsAsTimestamps;
    private boolean writeCharArraysAsJsonArrays;
    private boolean writeEnumsUsingToString;
    private boolean writeEnumsUsingIndex;
    private boolean writeEnumKeysUsingIndex;
    private boolean writeSingleElemArraysUnwrapped;
    private boolean writeDateTimestampsAsNanoseconds;
    private boolean orderMapEntriesByKeys;
    private boolean failOnOrderMapByIncomparableKey;
    private boolean eagerSerializerFetch;
    private boolean useEqualityForObjectId;


    private boolean useAnnotations;
    private boolean useGettersAsSetters;
    private boolean propagateTransientMarker;
    private boolean autoDetectCreators;
    private boolean autoDetectFields;
    private boolean autoDetectGetters;
    private boolean autoDetectIsGetters;
    private boolean autoDetectSetters;
    private boolean requireSettersForGetters;
    private boolean allowFinalFieldsAsMutators;
    private boolean inferPropertyMutators;
    private boolean inferCreatorFromConstructorProperties;
    private boolean allowVoidValuedProperties;
    private boolean canOverrideAccessModifiers;
    private boolean overridePublicAccessModifiers;
    private boolean inverseReadWriteAccess;
    private boolean useStaticTyping;
    private boolean useBaseTypeAsDefaultImpl;
    private boolean inferBuilderTypeBindings;
    private boolean requireTypeIdForSubtypes;
    private boolean defaultViewInclusion;
    private boolean sortPropertiesAlphabetically;
    private boolean sortCreatorPropertiesFirst;
    private boolean sortCreatorPropertiesByDeclarationOrder;
    private boolean acceptCaseInsensitiveProperties;
    private boolean acceptCaseInsensitiveEnums;
    private boolean acceptCaseInsensitiveValues;
    private boolean useWrapperNameAsPropertyName;
    private boolean useStdBeanNaming;
    private boolean allowExplicitPropertyRenaming;
    private boolean allowIsGettersForNonBoolean;
    private boolean allowCoercionOfScalars;
    private boolean ignoreDuplicateModuleRegistrations;
    private boolean ignoreMergeForUnmergeable;
    private boolean blockUnsafePolymorphicBaseTypes;
    private boolean applyDefaultValues;
    private boolean requireHandlersForJava8Optionals;

    protected TomlOptions(TomlConfiguration configuration) {
        super(configuration, "#", "");
        this.wrapRootValue = false;
        this.indentOutput = false;
        this.failOnEmptyBeans = true;
        this.failOnSelfReferences = true;
        this.wrapExceptions = true;
        this.failOnUnwrappedTypeIdentifiers = true;
        this.writeSelfReferencesAsNull = false;
        this.closeCloseable = false;
        this.flushAfterWriteValue = true;
        this.writeDatesAsTimestamps = true;
        this.writeDateKeysAsTimestamps = false;
        this.writeDatesWithZoneId = false;
        this.writeDatesWithContextTimeZone = true;
        this.writeDurationsAsTimestamps = true;
        this.writeCharArraysAsJsonArrays = false;
        this.writeEnumsUsingToString = false;
        this.writeEnumsUsingIndex = false;
        this.writeEnumKeysUsingIndex = false;
        this.writeSingleElemArraysUnwrapped = false;
        this.writeDateTimestampsAsNanoseconds = true;
        this.orderMapEntriesByKeys = false;
        this.failOnOrderMapByIncomparableKey = true;
        this.eagerSerializerFetch = true;
        this.useEqualityForObjectId = false;


        this.useAnnotations = true;
        this.useGettersAsSetters = true;
        this.propagateTransientMarker = false;
        this.autoDetectCreators = true;
        this.autoDetectFields = true;
        this.autoDetectGetters = true;
        this.autoDetectIsGetters = true;
        this.autoDetectSetters = true;
        this.requireSettersForGetters = false;
        this.allowFinalFieldsAsMutators = true;
        this.inferPropertyMutators = true;
        this.inferCreatorFromConstructorProperties = true;
        this.allowVoidValuedProperties = false;
        this.canOverrideAccessModifiers = true;
        this.overridePublicAccessModifiers = true;
        this.inverseReadWriteAccess = false;
        this.useStaticTyping = false;
        this.useBaseTypeAsDefaultImpl = false;
        this.inferBuilderTypeBindings = true;
        this.requireTypeIdForSubtypes = true;
        this.defaultViewInclusion = true;
        this.sortPropertiesAlphabetically = false;
        this.sortCreatorPropertiesFirst = true;
        this.sortCreatorPropertiesByDeclarationOrder = false;
        this.acceptCaseInsensitiveProperties = false;
        this.acceptCaseInsensitiveEnums = false;
        this.acceptCaseInsensitiveValues = false;
        this.useWrapperNameAsPropertyName = false;
        this.useStdBeanNaming = false;
        this.allowExplicitPropertyRenaming = false;
        this.allowIsGettersForNonBoolean = false;
        this.allowCoercionOfScalars = true;
        this.ignoreDuplicateModuleRegistrations = true;
        this.ignoreMergeForUnmergeable = true;
        this.blockUnsafePolymorphicBaseTypes = false;
        this.applyDefaultValues = true;
        this.requireHandlersForJava8Optionals = true;

    }


    public boolean isWrapRootValue() {
        return wrapRootValue;
    }

    public void setWrapRootValue(boolean wrapRootValue) {
        this.wrapRootValue = wrapRootValue;
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

    public boolean isCloseCloseable() {
        return closeCloseable;
    }

    public void setCloseCloseable(boolean closeCloseable) {
        this.closeCloseable = closeCloseable;
    }

    public boolean isFlushAfterWriteValue() {
        return flushAfterWriteValue;
    }

    public void setFlushAfterWriteValue(boolean flushAfterWriteValue) {
        this.flushAfterWriteValue = flushAfterWriteValue;
    }

    public boolean isWriteDatesAsTimestamps() {
        return writeDatesAsTimestamps;
    }

    public void setWriteDatesAsTimestamps(boolean writeDatesAsTimestamps) {
        this.writeDatesAsTimestamps = writeDatesAsTimestamps;
    }

    public boolean isWriteDateKeysAsTimestamps() {
        return writeDateKeysAsTimestamps;
    }

    public void setWriteDateKeysAsTimestamps(boolean writeDateKeysAsTimestamps) {
        this.writeDateKeysAsTimestamps = writeDateKeysAsTimestamps;
    }

    public boolean isWriteDatesWithZoneId() {
        return writeDatesWithZoneId;
    }

    public void setWriteDatesWithZoneId(boolean writeDatesWithZoneId) {
        this.writeDatesWithZoneId = writeDatesWithZoneId;
    }

    public boolean isWriteDatesWithContextTimeZone() {
        return writeDatesWithContextTimeZone;
    }

    public void setWriteDatesWithContextTimeZone(boolean writeDatesWithContextTimeZone) {
        this.writeDatesWithContextTimeZone = writeDatesWithContextTimeZone;
    }

    public boolean isWriteDurationsAsTimestamps() {
        return writeDurationsAsTimestamps;
    }

    public void setWriteDurationsAsTimestamps(boolean writeDurationsAsTimestamps) {
        this.writeDurationsAsTimestamps = writeDurationsAsTimestamps;
    }

    public boolean isWriteCharArraysAsJsonArrays() {
        return writeCharArraysAsJsonArrays;
    }

    public void setWriteCharArraysAsJsonArrays(boolean writeCharArraysAsJsonArrays) {
        this.writeCharArraysAsJsonArrays = writeCharArraysAsJsonArrays;
    }

    public boolean isWriteEnumsUsingToString() {
        return writeEnumsUsingToString;
    }

    public void setWriteEnumsUsingToString(boolean writeEnumsUsingToString) {
        this.writeEnumsUsingToString = writeEnumsUsingToString;
    }

    public boolean isWriteEnumsUsingIndex() {
        return writeEnumsUsingIndex;
    }

    public void setWriteEnumsUsingIndex(boolean writeEnumsUsingIndex) {
        this.writeEnumsUsingIndex = writeEnumsUsingIndex;
    }

    public boolean isWriteEnumKeysUsingIndex() {
        return writeEnumKeysUsingIndex;
    }

    public void setWriteEnumKeysUsingIndex(boolean writeEnumKeysUsingIndex) {
        this.writeEnumKeysUsingIndex = writeEnumKeysUsingIndex;
    }

    public boolean isWriteSingleElemArraysUnwrapped() {
        return writeSingleElemArraysUnwrapped;
    }

    public void setWriteSingleElemArraysUnwrapped(boolean writeSingleElemArraysUnwrapped) {
        this.writeSingleElemArraysUnwrapped = writeSingleElemArraysUnwrapped;
    }

    public boolean isWriteDateTimestampsAsNanoseconds() {
        return writeDateTimestampsAsNanoseconds;
    }

    public void setWriteDateTimestampsAsNanoseconds(boolean writeDateTimestampsAsNanoseconds) {
        this.writeDateTimestampsAsNanoseconds = writeDateTimestampsAsNanoseconds;
    }

    public boolean isOrderMapEntriesByKeys() {
        return orderMapEntriesByKeys;
    }

    public void setOrderMapEntriesByKeys(boolean orderMapEntriesByKeys) {
        this.orderMapEntriesByKeys = orderMapEntriesByKeys;
    }

    public boolean isFailOnOrderMapByIncomparableKey() {
        return failOnOrderMapByIncomparableKey;
    }

    public void setFailOnOrderMapByIncomparableKey(boolean failOnOrderMapByIncomparableKey) {
        this.failOnOrderMapByIncomparableKey = failOnOrderMapByIncomparableKey;
    }

    public boolean isEagerSerializerFetch() {
        return eagerSerializerFetch;
    }

    public void setEagerSerializerFetch(boolean eagerSerializerFetch) {
        this.eagerSerializerFetch = eagerSerializerFetch;
    }

    public boolean isUseEqualityForObjectId() {
        return useEqualityForObjectId;
    }

    public void setUseEqualityForObjectId(boolean useEqualityForObjectId) {
        this.useEqualityForObjectId = useEqualityForObjectId;
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

    @Override
    public String toString() {
        return "TomlOptions{" +
                "wrapRootValue=" + wrapRootValue +
                ", indentOutput=" + indentOutput +
                ", failOnEmptyBeans=" + failOnEmptyBeans +
                ", failOnSelfReferences=" + failOnSelfReferences +
                ", wrapExceptions=" + wrapExceptions +
                ", failOnUnwrappedTypeIdentifiers=" + failOnUnwrappedTypeIdentifiers +
                ", writeSelfReferencesAsNull=" + writeSelfReferencesAsNull +
                ", closeCloseable=" + closeCloseable +
                ", flushAfterWriteValue=" + flushAfterWriteValue +
                ", writeDatesAsTimestamps=" + writeDatesAsTimestamps +
                ", writeDateKeysAsTimestamps=" + writeDateKeysAsTimestamps +
                ", writeDatesWithZoneId=" + writeDatesWithZoneId +
                ", writeDatesWithContextTimeZone=" + writeDatesWithContextTimeZone +
                ", writeDurationsAsTimestamps=" + writeDurationsAsTimestamps +
                ", writeCharArraysAsJsonArrays=" + writeCharArraysAsJsonArrays +
                ", writeEnumsUsingToString=" + writeEnumsUsingToString +
                ", writeEnumsUsingIndex=" + writeEnumsUsingIndex +
                ", writeEnumKeysUsingIndex=" + writeEnumKeysUsingIndex +
                ", writeSingleElemArraysUnwrapped=" + writeSingleElemArraysUnwrapped +
                ", writeDateTimestampsAsNanoseconds=" + writeDateTimestampsAsNanoseconds +
                ", orderMapEntriesByKeys=" + orderMapEntriesByKeys +
                ", failOnOrderMapByIncomparableKey=" + failOnOrderMapByIncomparableKey +
                ", eagerSerializerFetch=" + eagerSerializerFetch +
                ", useEqualityForObjectId=" + useEqualityForObjectId +
                ", useAnnotations=" + useAnnotations +
                ", useGettersAsSetters=" + useGettersAsSetters +
                ", propagateTransientMarker=" + propagateTransientMarker +
                ", autoDetectCreators=" + autoDetectCreators +
                ", autoDetectFields=" + autoDetectFields +
                ", autoDetectGetters=" + autoDetectGetters +
                ", autoDetectIsGetters=" + autoDetectIsGetters +
                ", autoDetectSetters=" + autoDetectSetters +
                ", requireSettersForGetters=" + requireSettersForGetters +
                ", allowFinalFieldsAsMutators=" + allowFinalFieldsAsMutators +
                ", inferPropertyMutators=" + inferPropertyMutators +
                ", inferCreatorFromConstructorProperties=" + inferCreatorFromConstructorProperties +
                ", allowVoidValuedProperties=" + allowVoidValuedProperties +
                ", canOverrideAccessModifiers=" + canOverrideAccessModifiers +
                ", overridePublicAccessModifiers=" + overridePublicAccessModifiers +
                ", inverseReadWriteAccess=" + inverseReadWriteAccess +
                ", useStaticTyping=" + useStaticTyping +
                ", useBaseTypeAsDefaultImpl=" + useBaseTypeAsDefaultImpl +
                ", inferBuilderTypeBindings=" + inferBuilderTypeBindings +
                ", requireTypeIdForSubtypes=" + requireTypeIdForSubtypes +
                ", defaultViewInclusion=" + defaultViewInclusion +
                ", sortPropertiesAlphabetically=" + sortPropertiesAlphabetically +
                ", sortCreatorPropertiesFirst=" + sortCreatorPropertiesFirst +
                ", sortCreatorPropertiesByDeclarationOrder=" + sortCreatorPropertiesByDeclarationOrder +
                ", acceptCaseInsensitiveProperties=" + acceptCaseInsensitiveProperties +
                ", acceptCaseInsensitiveEnums=" + acceptCaseInsensitiveEnums +
                ", acceptCaseInsensitiveValues=" + acceptCaseInsensitiveValues +
                ", useWrapperNameAsPropertyName=" + useWrapperNameAsPropertyName +
                ", useStdBeanNaming=" + useStdBeanNaming +
                ", allowExplicitPropertyRenaming=" + allowExplicitPropertyRenaming +
                ", allowIsGettersForNonBoolean=" + allowIsGettersForNonBoolean +
                ", allowCoercionOfScalars=" + allowCoercionOfScalars +
                ", ignoreDuplicateModuleRegistrations=" + ignoreDuplicateModuleRegistrations +
                ", ignoreMergeForUnmergeable=" + ignoreMergeForUnmergeable +
                ", blockUnsafePolymorphicBaseTypes=" + blockUnsafePolymorphicBaseTypes +
                ", applyDefaultValues=" + applyDefaultValues +
                ", requireHandlersForJava8Optionals=" + requireHandlersForJava8Optionals +
                ", commentPrefix='" + super.getCommentPrefix() + '\'' +
                ", commentSuffix='" + super.getCommentSuffix() + '\'' +
                ", header='" + super.getHeader() + '\'' +
                ", footer='" + super.getFooter() + '\'' +
                '}';
    }
}
