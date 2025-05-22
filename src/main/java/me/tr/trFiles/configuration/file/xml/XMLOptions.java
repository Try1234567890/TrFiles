package me.tr.trFiles.configuration.file.xml;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.SerializationFeature;
import me.tr.trFiles.configuration.file.FileOptions;
import me.tr.trFiles.configuration.memory.MemoryConfiguration;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class XMLOptions extends FileOptions {
    private final String commentPrefix = "<!-- ";
    private final String commentSuffix = " -->";
    private boolean copyHeader = false;
    private boolean copyFooter = false;
    private String header = "";
    private String footer = "";
    private boolean prettyPrint = true;
    private List<JsonParser.Feature> parseFeatures = List.of(
            JsonParser.Feature.ALLOW_COMMENTS,
            JsonParser.Feature.ALLOW_YAML_COMMENTS
    );
    private List<JsonGenerator.Feature> generatorFeatures = new ArrayList<>();
    private List<SerializationFeature> serializationFeatures = new ArrayList<>();
    private TimeZone timeZone = TimeZone.getDefault();
    private DateFormat dateFormat = DateFormat.getDateInstance();
    private Locale locale = Locale.getDefault();

    protected XMLOptions(MemoryConfiguration configuration) {
        super(configuration);
        super.commentPrefix(commentPrefix);
        super.commentSuffix(commentSuffix);
    }

    public boolean prettyPrint() {
        return prettyPrint;
    }

    public void prettyPrint(boolean prettyPrint) {
        this.prettyPrint = prettyPrint;
    }

    public List<JsonParser.Feature> parseFeatures() {
        return parseFeatures;
    }

    public void parseFeatures(List<JsonParser.Feature> parseFeatures) {
        this.parseFeatures = parseFeatures;
    }

    public List<JsonGenerator.Feature> generatorFeatures() {
        return generatorFeatures;
    }

    public void generatorFeatures(List<JsonGenerator.Feature> generatorFeatures) {
        this.generatorFeatures = generatorFeatures;
    }

    public List<SerializationFeature> serializationFeatures() {
        return serializationFeatures;
    }

    public void serializationFeatures(List<SerializationFeature> serializationFeatures) {
        this.serializationFeatures = serializationFeatures;
    }

    public TimeZone timeZone() {
        return timeZone;
    }

    public void timeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public DateFormat dateFormat() {
        return dateFormat;
    }

    public void dateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public Locale locale() {
        return locale;
    }

    public void locale(Locale locale) {
        this.locale = locale;
    }

    public String commentPrefix() {
        return commentPrefix;
    }

    public String commentSuffix() {
        return commentSuffix;
    }

    public XMLOptions header(String header) {
        this.header = header;
        return this;
    }

    public String header() {
        return header;
    }

    public XMLOptions footer(String footer) {
        this.footer = footer;
        return this;
    }

    public String footer() {
        return footer;
    }

    public boolean copyHeader() {
        return copyHeader;
    }

    public XMLOptions copyHeader(boolean copyHeader) {
        this.copyHeader = copyHeader;
        return this;
    }

    public boolean copyFooter() {
        return copyFooter;
    }

    public XMLOptions copyFooter(boolean copyFooter) {
        this.copyFooter = copyFooter;
        return this;
    }
}
