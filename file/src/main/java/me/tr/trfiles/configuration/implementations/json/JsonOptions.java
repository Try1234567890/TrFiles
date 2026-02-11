package me.tr.trfiles.configuration.implementations.json;

import com.google.gson.ExclusionStrategy;
import com.google.gson.LongSerializationPolicy;
import com.google.gson.Strictness;
import me.tr.trfiles.configuration.implementations.FileOptions;

import java.lang.reflect.Modifier;
import java.util.Arrays;

public class JsonOptions extends FileOptions {
    private boolean prettyPrinting;
    private boolean serializeNulls;
    private String datePattern;
    private double version;
    private int[] excludeFieldsWithModifiers;
    private boolean excludeFieldsWithoutExposeAnnotation;
    private ExclusionStrategy[] exclusionStrategies;
    private boolean disableHtmlEscaping;
    private boolean generateNonExecutableJson;
    private boolean enableComplexMapKeySerialization;
    private boolean serializeSpecialFloatingPointValues;
    private LongSerializationPolicy longSerializationPolicy;
    private Strictness strictness;

    protected JsonOptions(JsonConfiguration configuration) {
        super(configuration, "//", "");
        this.prettyPrinting = true;
        this.serializeNulls = false;
        this.datePattern = null;
        this.version = 1.0;
        this.excludeFieldsWithModifiers = new int[]{Modifier.STATIC, Modifier.TRANSIENT};
        this.excludeFieldsWithoutExposeAnnotation = false;
        this.exclusionStrategies = new ExclusionStrategy[0];
        this.disableHtmlEscaping = false;
        this.generateNonExecutableJson = false;
        this.enableComplexMapKeySerialization = false;
        this.serializeSpecialFloatingPointValues = false;
        this.longSerializationPolicy = LongSerializationPolicy.DEFAULT;
        this.strictness = Strictness.LENIENT;
    }

    public boolean isPrettyPrinting() {
        return prettyPrinting;
    }

    public void setPrettyPrinting(boolean prettyPrinting) {
        this.prettyPrinting = prettyPrinting;
    }

    public boolean isSerializeNulls() {
        return serializeNulls;
    }

    public void setSerializeNulls(boolean serializeNulls) {
        this.serializeNulls = serializeNulls;
    }

    public String getDatePattern() {
        return datePattern;
    }

    public void setDatePattern(String datePattern) {
        this.datePattern = datePattern;
    }

    public double getVersion() {
        return version;
    }

    public void setVersion(double version) {
        this.version = version;
    }

    public int[] getExcludeFieldsWithModifiers() {
        return excludeFieldsWithModifiers;
    }

    public void setExcludeFieldsWithModifiers(int[] excludeFieldsWithModifiers) {
        this.excludeFieldsWithModifiers = excludeFieldsWithModifiers;
    }

    public boolean isExcludeFieldsWithoutExposeAnnotation() {
        return excludeFieldsWithoutExposeAnnotation;
    }

    public void setExcludeFieldsWithoutExposeAnnotation(boolean excludeFieldsWithoutExposeAnnotation) {
        this.excludeFieldsWithoutExposeAnnotation = excludeFieldsWithoutExposeAnnotation;
    }

    public ExclusionStrategy[] getExclusionStrategies() {
        return exclusionStrategies;
    }

    public void setExclusionStrategies(ExclusionStrategy[] exclusionStrategies) {
        this.exclusionStrategies = exclusionStrategies;
    }

    public boolean isDisableHtmlEscaping() {
        return disableHtmlEscaping;
    }

    public void setDisableHtmlEscaping(boolean disableHtmlEscaping) {
        this.disableHtmlEscaping = disableHtmlEscaping;
    }

    public boolean isGenerateNonExecutableJson() {
        return generateNonExecutableJson;
    }

    public void setGenerateNonExecutableJson(boolean generateNonExecutableJson) {
        this.generateNonExecutableJson = generateNonExecutableJson;
    }

    public boolean isEnableComplexMapKeySerialization() {
        return enableComplexMapKeySerialization;
    }

    public void setEnableComplexMapKeySerialization(boolean enableComplexMapKeySerialization) {
        this.enableComplexMapKeySerialization = enableComplexMapKeySerialization;
    }

    public boolean isSerializeSpecialFloatingPointValues() {
        return serializeSpecialFloatingPointValues;
    }

    public void setSerializeSpecialFloatingPointValues(boolean serializeSpecialFloatingPointValues) {
        this.serializeSpecialFloatingPointValues = serializeSpecialFloatingPointValues;
    }

    public LongSerializationPolicy getLongSerializationPolicy() {
        return longSerializationPolicy;
    }

    public void setLongSerializationPolicy(LongSerializationPolicy longSerializationPolicy) {
        this.longSerializationPolicy = longSerializationPolicy;
    }

    public Strictness getStrictness() {
        return strictness;
    }

    public void setStrictness(Strictness strictness) {
        this.strictness = strictness;
    }

    @Override
    public String toString() {
        return "JsonOptions{" +
                "prettyPrinting=" + prettyPrinting +
                ", serializeNulls=" + serializeNulls +
                ", datePattern='" + datePattern + '\'' +
                ", version=" + version +
                ", excludeFieldsWithModifiers=" + Arrays.toString(excludeFieldsWithModifiers) +
                ", excludeFieldsWithoutExposeAnnotation=" + excludeFieldsWithoutExposeAnnotation +
                ", exclusionStrategies=" + Arrays.toString(exclusionStrategies) +
                ", disableHtmlEscaping=" + disableHtmlEscaping +
                ", generateNonExecutableJson=" + generateNonExecutableJson +
                ", enableComplexMapKeySerialization=" + enableComplexMapKeySerialization +
                ", serializeSpecialFloatingPointValues=" + serializeSpecialFloatingPointValues +
                ", longSerializationPolicy=" + longSerializationPolicy +
                ", strictness=" + strictness +
                ", commentPrefix='" + super.getCommentPrefix() + '\'' +
                ", commentSuffix='" + super.getCommentSuffix() + '\'' +
                ", header='" + super.getHeader() + '\'' +
                ", footer='" + super.getFooter() + '\'' +
                '}';
    }
}
