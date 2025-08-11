package me.tr.trFiles.configuration.implementations.yaml;

import me.tr.trFiles.configuration.implementations.FileOptions;
import me.tr.trFiles.configuration.memory.MemoryConfiguration;
import org.yaml.snakeyaml.DumperOptions;

import java.util.TimeZone;

public class YamlOptions extends FileOptions {
    private boolean allowDuplicateKeys;
    private boolean allowRecursiveKeys;
    private boolean processComments;
    private boolean enumCaseSensitive;
    private DumperOptions.ScalarStyle defaultStyle;
    private DumperOptions.FlowStyle defaultFlowStyle;
    private boolean canonical;
    private boolean allowUnicode;
    private boolean allowReadOnlyProperties;
    private int indent;
    private boolean splitLines;
    private DumperOptions.LineBreak lineBreak;
    private boolean explicitStart;
    private boolean explicitEnd;
    private TimeZone timeZone;
    private DumperOptions.NonPrintableStyle nonPrintableStyle;
    private DumperOptions.Version version;
    private Boolean prettyFlow;


    protected YamlOptions(MemoryConfiguration configuration) {
        super(configuration, "#", "");
        setAllowDuplicateKeys(true);
        setAllowRecursiveKeys(false);
        setProcessComments(false);
        setEnumCaseSensitive(true);
        setDefaultStyle(DumperOptions.ScalarStyle.PLAIN);
        setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        setCanonical(false);
        setAllowUnicode(true);
        setAllowReadOnlyProperties(false);
        setIndent(2);
        setSplitLines(true);
        setLineBreak(DumperOptions.LineBreak.UNIX);
        setExplicitStart(false);
        setExplicitEnd(false);
        setTimeZone(null);
        setNonPrintableStyle(DumperOptions.NonPrintableStyle.BINARY);
        setVersion(null);
        setPrettyFlow(true);
    }

    public boolean isAllowDuplicateKeys() {
        return allowDuplicateKeys;
    }

    public void setAllowDuplicateKeys(boolean allowDuplicateKeys) {
        this.allowDuplicateKeys = allowDuplicateKeys;
    }

    public boolean isAllowRecursiveKeys() {
        return allowRecursiveKeys;
    }

    public void setAllowRecursiveKeys(boolean allowRecursiveKeys) {
        this.allowRecursiveKeys = allowRecursiveKeys;
    }

    public boolean isProcessComments() {
        return processComments;
    }

    public void setProcessComments(boolean processComments) {
        this.processComments = processComments;
    }

    public boolean isEnumCaseSensitive() {
        return enumCaseSensitive;
    }

    public void setEnumCaseSensitive(boolean enumCaseSensitive) {
        this.enumCaseSensitive = enumCaseSensitive;
    }

    public DumperOptions.ScalarStyle getDefaultStyle() {
        return defaultStyle;
    }

    public void setDefaultStyle(DumperOptions.ScalarStyle defaultStyle) {
        this.defaultStyle = defaultStyle;
    }

    public DumperOptions.FlowStyle getDefaultFlowStyle() {
        return defaultFlowStyle;
    }

    public void setDefaultFlowStyle(DumperOptions.FlowStyle defaultFlowStyle) {
        this.defaultFlowStyle = defaultFlowStyle;
    }

    public boolean isCanonical() {
        return canonical;
    }

    public void setCanonical(boolean canonical) {
        this.canonical = canonical;
    }

    public boolean isAllowUnicode() {
        return allowUnicode;
    }

    public void setAllowUnicode(boolean allowUnicode) {
        this.allowUnicode = allowUnicode;
    }

    public boolean isAllowReadOnlyProperties() {
        return allowReadOnlyProperties;
    }

    public void setAllowReadOnlyProperties(boolean allowReadOnlyProperties) {
        this.allowReadOnlyProperties = allowReadOnlyProperties;
    }

    public int getIndent() {
        return indent;
    }

    public void setIndent(int indent) {
        this.indent = indent;
    }

    public boolean isSplitLines() {
        return splitLines;
    }

    public void setSplitLines(boolean splitLines) {
        this.splitLines = splitLines;
    }

    public DumperOptions.LineBreak getLineBreak() {
        return lineBreak;
    }

    public void setLineBreak(DumperOptions.LineBreak lineBreak) {
        this.lineBreak = lineBreak;
    }

    public boolean isExplicitStart() {
        return explicitStart;
    }

    public void setExplicitStart(boolean explicitStart) {
        this.explicitStart = explicitStart;
    }

    public boolean isExplicitEnd() {
        return explicitEnd;
    }

    public void setExplicitEnd(boolean explicitEnd) {
        this.explicitEnd = explicitEnd;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public DumperOptions.NonPrintableStyle getNonPrintableStyle() {
        return nonPrintableStyle;
    }

    public void setNonPrintableStyle(DumperOptions.NonPrintableStyle nonPrintableStyle) {
        this.nonPrintableStyle = nonPrintableStyle;
    }

    public DumperOptions.Version getVersion() {
        return version;
    }

    public void setVersion(DumperOptions.Version version) {
        this.version = version;
    }

    public Boolean isPrettyFlow() {
        return prettyFlow;
    }

    public void setPrettyFlow(Boolean prettyFlow) {
        this.prettyFlow = prettyFlow;
    }
}
