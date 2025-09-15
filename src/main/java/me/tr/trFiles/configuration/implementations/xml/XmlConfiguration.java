package me.tr.trFiles.configuration.implementations.xml;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.deser.FromXmlParser;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import me.tr.trFiles.configuration.ConfigRegistry;
import me.tr.trFiles.configuration.implementations.FileConfiguration;
import me.tr.trFiles.configuration.memory.implementations.MemoryXmlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Path;
import java.util.zip.ZipFile;

public class XmlConfiguration extends FileConfiguration {
    private XmlOptions options;

    private XmlConfiguration() {
    }

    @Override
    public String[] getExtensions() {
        return new String[]{".xml"};
    }


    @Override
    public XmlConfiguration newConfiguration(File file) {
        return XmlConfiguration.emptyXml(file);
    }

    @Override
    public MemoryXmlConfiguration newMemoryConfiguration() {
        return MemoryXmlConfiguration.emptyXml(buildXml());
    }

    @Override
    public void register() {
        ConfigRegistry.register(getClass());
    }

    @Override
    public MemoryXmlConfiguration getConfiguration() {
        return (MemoryXmlConfiguration) super.getConfiguration();
    }

    public static XmlConfiguration emptyXml(File file) {
        return (XmlConfiguration) emptyConfig(file, XmlConfiguration.class);
    }

    public static XmlConfiguration fromReader(File file, Reader reader) {
        return (XmlConfiguration) fromReader(file, reader, XmlConfiguration.class);
    }


    public static XmlConfiguration fromInputStream(File file, InputStream is) {
        return (XmlConfiguration) fromInputStream(file, is, XmlConfiguration.class);
    }

    public static XmlConfiguration fromFile(File file) {
        return (XmlConfiguration) fromFile(file, XmlConfiguration.class);
    }

    public static XmlConfiguration fromPath(Path path) {
        return (XmlConfiguration) fromPath(path, XmlConfiguration.class);
    }

    public static XmlConfiguration fromString(String path) {
        return (XmlConfiguration) fromString(path, XmlConfiguration.class);
    }

    public static XmlConfiguration fromContent(File file, String content) {
        return (XmlConfiguration) fromContent(file, content, XmlConfiguration.class);
    }

    public static XmlConfiguration fromArchive(ZipFile archive, File inside, File to) {
        return (XmlConfiguration) fromArchive(archive, inside, to, XmlConfiguration.class);
    }

    public static XmlConfiguration fromBytes(File file, byte[] bytes) {
        return (XmlConfiguration) fromBytes(file, bytes, XmlConfiguration.class);
    }

    @Override
    public XmlOptions options() {
        if (options == null) {
            options = new XmlOptions(this);
        }
        return options;
    }

    public void options(XmlOptions options) {
        this.options = options;
    }


    private XmlMapper buildXml() {
        return XmlMapper.builder()
                .configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, options().isWriteXmlDeclaration())
                .configure(ToXmlGenerator.Feature.WRITE_XML_1_1, options().isWriteXml11())
                .configure(ToXmlGenerator.Feature.WRITE_NULLS_AS_XSI_NIL, options().isWriteNullsAsXsiNil())
                .configure(ToXmlGenerator.Feature.UNWRAP_ROOT_OBJECT_NODE, options().isUnwrapRootObjectNode())
                .configure(ToXmlGenerator.Feature.AUTO_DETECT_XSI_TYPE, options().isAutoDetectXsiTypeSer())
                .configure(ToXmlGenerator.Feature.WRITE_XML_SCHEMA_CONFORMING_FLOATS, options().isWriteXmlSchemaConformingFloats())
                .configure(FromXmlParser.Feature.AUTO_DETECT_XSI_TYPE, options().isAutoDetectXsiTypeDes())
                .configure(FromXmlParser.Feature.EMPTY_ELEMENT_AS_NULL, options().isEmptyElementAsNull())
                .configure(FromXmlParser.Feature.PROCESS_XSI_NIL, options().isProcessXsiNil())
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

    public void setRootName(String rootName) {
        getConfiguration().setRootName(rootName);
    }
}
