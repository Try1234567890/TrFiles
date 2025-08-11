package me.tr.trFiles.configuration.implementations.xml;


import me.tr.trFiles.configuration.implementations.FileOptions;
import me.tr.trFiles.configuration.memory.MemoryConfiguration;

public class XmlOptions extends FileOptions {
    private boolean autoDetectXsiType;
    private boolean emptyElementAsNull;
    private boolean processXsiNil;
    private boolean writeXmlDeclaration;
    private boolean writeXml11;
    private boolean writeNullsAsXsiNil;
    private boolean unwrapRootObjectNode;
    private boolean writeXmlSchemaConformingFloats;
    private boolean wrapRootValue;
    private boolean indentOutput;
    private boolean failOnEmptyBeans;
    private boolean failOnSelfReferences;
    private boolean wrapExceptions;
    private boolean failOnUnwrappedTypeIdentifiers;
    private boolean writeSelfReferencesAsNull;

    protected XmlOptions(MemoryConfiguration configuration) {
        super(configuration, "<!--", "-->");
        this.autoDetectXsiType = false;
        this.emptyElementAsNull = false;
        this.processXsiNil = true;
        this.writeXmlDeclaration = false;
        this.writeXml11 = false;
        this.writeNullsAsXsiNil = false;
        this.unwrapRootObjectNode = false;
        this.writeXmlSchemaConformingFloats = false;
        this.wrapRootValue = false;
        this.indentOutput = false;
        this.failOnEmptyBeans = true;
        this.failOnSelfReferences = true;
        this.wrapExceptions = true;
        this.failOnUnwrappedTypeIdentifiers = true;
        this.writeSelfReferencesAsNull = false;

    }


    public boolean isAutoDetectXsiType() {
        return autoDetectXsiType;
    }

    public void setAutoDetectXsiType(boolean autoDetectXsiType) {
        this.autoDetectXsiType = autoDetectXsiType;
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

    public boolean isWriteXmlSchemaConformingFloats() {
        return writeXmlSchemaConformingFloats;
    }

    public void setWriteXmlSchemaConformingFloats(boolean writeXmlSchemaConformingFloats) {
        this.writeXmlSchemaConformingFloats = writeXmlSchemaConformingFloats;
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
}
