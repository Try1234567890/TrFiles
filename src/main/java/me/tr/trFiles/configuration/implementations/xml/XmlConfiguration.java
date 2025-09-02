package me.tr.trFiles.configuration.implementations.xml;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.deser.FromXmlParser;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import me.tr.trFiles.configuration.implementations.FileConfiguration;
import me.tr.trFiles.configuration.memory.MemoryConfiguration;
import me.tr.trFiles.configuration.memory.implementations.MemoryXmlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Path;
import java.util.Map;
import java.util.zip.ZipFile;

public class XmlConfiguration extends FileConfiguration {
    private MemoryXmlConfiguration configuration;
    private XmlOptions options;
    private String rootName;

    @Override
    public void loadFromString(String contents) {
        this.configuration = new MemoryXmlConfiguration(buildXml());
        configuration.loadFromString(contents);
    }

    @Override
    public void setConfiguration(MemoryConfiguration configuration) {
        if (!(configuration instanceof MemoryXmlConfiguration)) return;
        this.configuration = (MemoryXmlConfiguration) configuration;
    }

    @Override
    public MemoryXmlConfiguration getConfiguration() {
        return configuration;
    }

    @Override
    public String[] getExtensions() {
        return new String[]{".xml"};
    }

    public XmlConfiguration(File file, Map<String, Object> map) {
        super(file, map);
    }

    public XmlConfiguration(File file, Reader reader) {
        super(file, reader);
    }

    public XmlConfiguration(File file, InputStream is) {
        super(file, is);
    }

    public XmlConfiguration(File file) {
        super(file);
    }

    public XmlConfiguration(Path path) {
        super(path);
    }

    public XmlConfiguration(String path) {
        super(path);
    }

    public XmlConfiguration(ZipFile archive, File inside, File to) {
        super(archive, inside, to);
    }

    public XmlConfiguration(File archive, File inside, File to) {
        super(archive, inside, to);
    }

    public static XmlConfiguration fromMap(File file, Map<String, Object> map) {
        return new XmlConfiguration(file, map);
    }

    public static XmlConfiguration fromReader(File file, Reader reader) {
        return new XmlConfiguration(file, reader);
    }

    public static XmlConfiguration fromInputStream(File file, InputStream is) {
        return new XmlConfiguration(file, is);
    }

    public static XmlConfiguration fromFile(File file) {
        return new XmlConfiguration(file);
    }

    public static XmlConfiguration fromPath(Path path) {
        return new XmlConfiguration(path);
    }

    public static XmlConfiguration fromString(String path) {
        return new XmlConfiguration(path);
    }

    public static XmlConfiguration fromArchive(ZipFile archive, File inside, File to) {
        return new XmlConfiguration(archive, inside, to);
    }

    public static XmlConfiguration fromArchive(File archive, File inside, File to) {
        return new XmlConfiguration(archive, inside, to);
    }

    private XmlMapper buildXml() {
        XmlMapper mapper = new XmlMapper();

        mapper.configure(FromXmlParser.Feature.AUTO_DETECT_XSI_TYPE, options().isAutoDetectXsiType());
        mapper.configure(FromXmlParser.Feature.EMPTY_ELEMENT_AS_NULL, options().isEmptyElementAsNull());
        mapper.configure(FromXmlParser.Feature.PROCESS_XSI_NIL, options().isProcessXsiNil());

        mapper.configure(ToXmlGenerator.Feature.AUTO_DETECT_XSI_TYPE, options().isAutoDetectXsiType());
        mapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, options().isWriteXmlDeclaration());
        mapper.configure(ToXmlGenerator.Feature.WRITE_XML_1_1, options().isWriteXml11());
        mapper.configure(ToXmlGenerator.Feature.WRITE_NULLS_AS_XSI_NIL, options().isWriteNullsAsXsiNil());
        mapper.configure(ToXmlGenerator.Feature.UNWRAP_ROOT_OBJECT_NODE, options().isUnwrapRootObjectNode());
        mapper.configure(ToXmlGenerator.Feature.WRITE_XML_SCHEMA_CONFORMING_FLOATS, options().isWriteXmlSchemaConformingFloats());


        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, options().isWrapRootValue());
        mapper.configure(SerializationFeature.INDENT_OUTPUT, options().isIndentOutput());
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, options().isFailOnEmptyBeans());
        mapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, options().isFailOnSelfReferences());
        mapper.configure(SerializationFeature.FAIL_ON_UNWRAPPED_TYPE_IDENTIFIERS, options().isFailOnUnwrappedTypeIdentifiers());
        mapper.configure(SerializationFeature.WRITE_SELF_REFERENCES_AS_NULL, options().isWriteSelfReferencesAsNull());
        mapper.configure(SerializationFeature.WRAP_EXCEPTIONS, options().isWrapExceptions());

        setRootName("configuration");
        return mapper;
    }

    public String getRootName() {
        return rootName;
    }

    public void setRootName(String rootName) {
        this.rootName = rootName;
    }

    @Override
    public XmlOptions options() {
        if (options == null) {
            options = new XmlOptions(this);
        }
        return options;
    }
}
