package me.tr.trfiles.configuration.implementations.xml;


import me.tr.trfiles.configuration.implementations.FileOptions;

public class XmlOptions extends FileOptions {
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

    private boolean writeXmlDeclaration;
    private boolean writeXml11;
    private boolean writeNullsAsXsiNil;
    private boolean unwrapRootObjectNode;
    private boolean autoDetectXsiTypeSer;
    private boolean writeXmlSchemaConformingFloats;

    private boolean autoDetectXsiTypeDes;
    private boolean emptyElementAsNull;
    private boolean processXsiNil;


    protected XmlOptions(XmlConfiguration configuration) {
        super(configuration, "<!--", "-->");
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

        this.writeXmlDeclaration = false;
        this.writeXml11 = false;
        this.writeNullsAsXsiNil = false;
        this.unwrapRootObjectNode = false;
        this.autoDetectXsiTypeSer = false;
        this.writeXmlSchemaConformingFloats = false;

        this.autoDetectXsiTypeDes = false;
        this.emptyElementAsNull = false;
        this.processXsiNil = true;
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

    public boolean isWriteXmlDeclaration() {
        return writeXmlDeclaration;
    }

    public void setWriteXmlDeclaration(boolean writeXmlDeclaration) {
        this.writeXmlDeclaration = writeXmlDeclaration;
    }

    public boolean isWriteXml11() {
        return writeXml11;
    }

    public void setWriteXml11(boolean writeXml11) {
        this.writeXml11 = writeXml11;
    }

    public boolean isWriteNullsAsXsiNil() {
        return writeNullsAsXsiNil;
    }

    public void setWriteNullsAsXsiNil(boolean writeNullsAsXsiNil) {
        this.writeNullsAsXsiNil = writeNullsAsXsiNil;
    }

    public boolean isUnwrapRootObjectNode() {
        return unwrapRootObjectNode;
    }

    public void setUnwrapRootObjectNode(boolean unwrapRootObjectNode) {
        this.unwrapRootObjectNode = unwrapRootObjectNode;
    }

    public boolean isAutoDetectXsiTypeSer() {
        return autoDetectXsiTypeSer;
    }

    public void setAutoDetectXsiTypeSer(boolean autoDetectXsiTypeSer) {
        this.autoDetectXsiTypeSer = autoDetectXsiTypeSer;
    }

    public boolean isWriteXmlSchemaConformingFloats() {
        return writeXmlSchemaConformingFloats;
    }

    public void setWriteXmlSchemaConformingFloats(boolean writeXmlSchemaConformingFloats) {
        this.writeXmlSchemaConformingFloats = writeXmlSchemaConformingFloats;
    }

    public boolean isAutoDetectXsiTypeDes() {
        return autoDetectXsiTypeDes;
    }

    public void setAutoDetectXsiTypeDes(boolean autoDetectXsiTypeDes) {
        this.autoDetectXsiTypeDes = autoDetectXsiTypeDes;
    }

    public boolean isEmptyElementAsNull() {
        return emptyElementAsNull;
    }

    public void setEmptyElementAsNull(boolean emptyElementAsNull) {
        this.emptyElementAsNull = emptyElementAsNull;
    }

    public boolean isProcessXsiNil() {
        return processXsiNil;
    }

    public void setProcessXsiNil(boolean processXsiNil) {
        this.processXsiNil = processXsiNil;
    }

    @Override
    public String toString() {
        return "XmlOptions{" +
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
                ", writeXmlDeclaration=" + writeXmlDeclaration +
                ", writeXml11=" + writeXml11 +
                ", writeNullsAsXsiNil=" + writeNullsAsXsiNil +
                ", unwrapRootObjectNode=" + unwrapRootObjectNode +
                ", autoDetectXsiTypeSer=" + autoDetectXsiTypeSer +
                ", writeXmlSchemaConformingFloats=" + writeXmlSchemaConformingFloats +
                ", autoDetectXsiTypeDes=" + autoDetectXsiTypeDes +
                ", emptyElementAsNull=" + emptyElementAsNull +
                ", processXsiNil=" + processXsiNil +
                ", commentPrefix='" + super.getCommentPrefix() + '\'' +
                ", commentSuffix='" + super.getCommentSuffix() + '\'' +
                ", header='" + super.getHeader() + '\'' +
                ", footer='" + super.getFooter() + '\'' +
                '}';    }
}
