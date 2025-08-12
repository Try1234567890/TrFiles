package me.tr.trFiles.configuration.implementations.xml;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.deser.FromXmlParser;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import me.tr.trFiles.configuration.implementations.FileConfiguration;
import me.tr.trFiles.configuration.implementations.Implementations;
import me.tr.trFiles.configuration.memory.MemoryConfiguration;
import me.tr.trFiles.general.utility.Validate;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class XmlConfiguration extends FileConfiguration {
    private XmlMapper xmlMapper;
    private XmlOptions options;
    private String rootName;


    @Override
    protected FileConfiguration loadFromString(String contents) {
        Validate.notNull(contents != null, "Contents cannot be null.");
        Map<?, ?> input;
        try {
            input = xmlMapper.reader().readValue(contents, Map.class);
        } catch (IOException e) {
            throw new RuntimeException("Error while loading configuration. ", e);
        }
        convertMapsToSections(input, this);
        setImplementation(Implementations.XML);
        return this;
    }

    @Override
    protected String saveToString() {
        String contents;
        try {
            contents = xmlMapper.writer().withRootName(getRootName()).writeValueAsString(getValues(true));
        } catch (IOException e) {
            throw new RuntimeException("Error while saving configuration. ", e);
        }
        setImplementation(Implementations.XML);
        return contents;
    }

    @Override
    protected XmlConfiguration newInstance() {
        return new XmlConfiguration();
    }

    /**
     * Create a new empty instance of {@link XmlConfiguration}.
     */
    public XmlConfiguration() {
        buildXml();
    }

    /**
     * Create a new instance of {@link XmlConfiguration} with provided configuration content.
     */
    public XmlConfiguration(MemoryConfiguration configuration) {
        super(configuration);
        buildXml();
    }

    public static XmlConfiguration from(File file) {
        if (!Implementations.XML.isValid(file)) {
            throw new IllegalArgumentException(file.getPath() + " is not a valid XML file.");
        }
        return (XmlConfiguration) FileConfiguration.from(file);
    }

    public static XmlConfiguration fromMap(Map<String, Object> map) {
        return (XmlConfiguration) fromMap(map, Implementations.XML);
    }

    private void buildXml() {
        setImplementation(Implementations.XML);

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

        setRootName("Configuration");

        this.xmlMapper = mapper;
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
