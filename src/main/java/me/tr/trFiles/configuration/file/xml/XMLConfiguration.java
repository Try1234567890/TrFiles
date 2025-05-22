package me.tr.trFiles.configuration.file.xml;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import me.tr.trFiles.configuration.file.FileConfiguration;
import me.tr.trFiles.general.utility.FileUtility;
import me.tr.trFiles.general.utility.Validate;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.jar.JarFile;

/**
 * This class represents the extension of FileConfiguration File for XML.
 */
public class XMLConfiguration extends FileConfiguration {
    public static final String[] XML_EXTENSIONS = new String[]{"xml"};
    private XmlMapper xmlMapper = new XmlMapper();

    /*
     */
    @Override
    protected String saveToString() {
        String header = buildHeader();
        String footer = buildFooter();
        String dump;
        try {
            dump = xmlMapper.writeValueAsString(getValues(true));
            dump = dump.replaceAll("<[^<>]*LinkedHashMap[^<>]*>", "");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        if (dump.equals(BLANK_FILE)) {
            return "";
        }
        if (options().copyHeader()) {
            dump = header + dump;
        }
        if (options().copyFooter()) {
            dump += footer;
        }
        return dump;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected XMLConfiguration loadFromString(String contents) {
        if (contents.isEmpty()) {
            map.clear();
            return this;
        }
        Map<String, Object> fileMap;
        try {
            fileMap = xmlMapper.readValue(contents, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        if (fileMap != null) {
            convertMapsToSections(fileMap, this);
        }
        return this;
    }

    protected XMLConfiguration() {
        xmlMapper = new XmlMapper();
        ObjectWriter writer = xmlMapper.writer();
        if (options().prettyPrint())
            writer.withDefaultPrettyPrinter();
        writer.with(options().dateFormat())
                .with(options().timeZone())
                .with(options().dateFormat());
        for (JsonParser.Feature parserFeature : options().parseFeatures())
            xmlMapper.enable(parserFeature);
        for (JsonGenerator.Feature generatorFeature : options().generatorFeatures())
            xmlMapper.enable(generatorFeature);
        for (SerializationFeature serializationFeature : options().serializationFeatures())
            xmlMapper.enable(serializationFeature);
    }

    public XMLConfiguration(File file) {
        loadConfiguration(file);
    }


    public static XMLConfiguration loadConfiguration(String file) {
        return loadConfiguration(new File(file));
    }

    public static XMLConfiguration loadConfiguration(File file) {
        if (!FileUtility.isXML(file)) {
            throw new IllegalArgumentException("File is not a YAML file: " + file.getName());
        }
        XMLConfiguration config = new XMLConfiguration();
        config.load(file);
        return config;
    }

    @Override
    protected String buildHeader() {
        return build(options().header());
    }

    @Override
    protected String buildFooter() {
        return build(options().footer());
    }


    @Override
    public XMLOptions options() {
        if (options == null) {
            options = new XMLOptions(this);
        }
        return (XMLOptions) options;
    }
}
