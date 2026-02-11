package me.tr.trfiles.configuration.implementations.yaml;

import me.tr.trfiles.configuration.implementations.FileOptions;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.inspector.TagInspector;
import org.yaml.snakeyaml.inspector.UnTrustedTagInspector;
import org.yaml.snakeyaml.serializer.AnchorGenerator;
import org.yaml.snakeyaml.serializer.NumberAnchorGenerator;

import java.util.Map;
import java.util.TimeZone;

public class YamlOptions extends FileOptions {
    private DumperOptions.ScalarStyle defaultStyle;
    private DumperOptions.FlowStyle defaultFlowStyle;
    private boolean canonical;
    private boolean allowUnicode;
    private boolean allowReadOnlyProperties;
    private int indent;
    private int indicatorIndent;
    private boolean indentWithIndicator;
    private int width;
    private boolean splitLines;
    private DumperOptions.LineBreak lineBreak;
    private boolean explicitStart;
    private boolean explicitEnd;
    private TimeZone timeZone;
    private int maxSimpleKeyLength;
    private boolean processComments;
    private DumperOptions.NonPrintableStyle nonPrintableStyle;

    private DumperOptions.Version version;
    private Map<String, String> tags;
    private Boolean prettyFlow;
    private AnchorGenerator anchorGenerator;

    private boolean dereferenceAliases;
    private boolean allowDuplicateKeys;
    private boolean wrappedToRootException;
    private int maxAliasesForCollections;
    private boolean allowRecursiveKeys;
    private boolean enumCaseSensitive;
    private int nestingDepthLimit;
    private int codePointLimit;
    private boolean mergeOnCompose;
    private TagInspector tagInspector;


    protected YamlOptions(YamlConfiguration configuration) {
        super(configuration, "#", "");
        this.defaultStyle = DumperOptions.ScalarStyle.PLAIN;
        this.defaultFlowStyle = DumperOptions.FlowStyle.BLOCK;
        this.canonical = false;
        this.allowUnicode = true;
        this.allowReadOnlyProperties = false;
        this.indent = 2;
        this.indicatorIndent = 0;
        this.indentWithIndicator = false;
        this.width = 1000;
        this.splitLines = true;
        this.lineBreak = DumperOptions.LineBreak.getPlatformLineBreak();
        this.explicitStart = false;
        this.explicitEnd = false;
        this.timeZone = null;
        this.maxSimpleKeyLength = 128;
        this.processComments = false;
        this.nonPrintableStyle = DumperOptions.NonPrintableStyle.BINARY;
        this.version = null;
        this.tags = null;
        this.prettyFlow = true;
        this.anchorGenerator = new NumberAnchorGenerator(0);


        this.dereferenceAliases = false;
        this.allowDuplicateKeys = true;
        this.wrappedToRootException = false;
        this.maxAliasesForCollections = 50;
        this.allowRecursiveKeys = false;
        this.enumCaseSensitive = true;
        this.nestingDepthLimit = 50;
        this.codePointLimit = 3 * 1024 * 1024;
        this.mergeOnCompose = false;
        this.tagInspector = new UnTrustedTagInspector();


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

    public int getIndicatorIndent() {
        return indicatorIndent;
    }

    public void setIndicatorIndent(int indicatorIndent) {
        this.indicatorIndent = indicatorIndent;
    }

    public boolean isIndentWithIndicator() {
        return indentWithIndicator;
    }

    public void setIndentWithIndicator(boolean indentWithIndicator) {
        this.indentWithIndicator = indentWithIndicator;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
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

    public int getMaxSimpleKeyLength() {
        return maxSimpleKeyLength;
    }

    public void setMaxSimpleKeyLength(int maxSimpleKeyLength) {
        this.maxSimpleKeyLength = maxSimpleKeyLength;
    }

    public boolean isProcessComments() {
        return processComments;
    }

    public void setProcessComments(boolean processComments) {
        this.processComments = processComments;
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

    public Map<String, String> getTags() {
        return tags;
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }

    public Boolean getPrettyFlow() {
        return prettyFlow;
    }

    public void setPrettyFlow(Boolean prettyFlow) {
        this.prettyFlow = prettyFlow;
    }

    public AnchorGenerator getAnchorGenerator() {
        return anchorGenerator;
    }

    public void setAnchorGenerator(AnchorGenerator anchorGenerator) {
        this.anchorGenerator = anchorGenerator;
    }

    public boolean isDereferenceAliases() {
        return dereferenceAliases;
    }

    public void setDereferenceAliases(boolean dereferenceAliases) {
        this.dereferenceAliases = dereferenceAliases;
    }


    public boolean isAllowDuplicateKeys() {
        return allowDuplicateKeys;
    }

    public void setAllowDuplicateKeys(boolean allowDuplicateKeys) {
        this.allowDuplicateKeys = allowDuplicateKeys;
    }

    public boolean isWrappedToRootException() {
        return wrappedToRootException;
    }

    public void setWrappedToRootException(boolean wrappedToRootException) {
        this.wrappedToRootException = wrappedToRootException;
    }

    public int getMaxAliasesForCollections() {
        return maxAliasesForCollections;
    }

    public void setMaxAliasesForCollections(int maxAliasesForCollections) {
        this.maxAliasesForCollections = maxAliasesForCollections;
    }

    public boolean isAllowRecursiveKeys() {
        return allowRecursiveKeys;
    }

    public void setAllowRecursiveKeys(boolean allowRecursiveKeys) {
        this.allowRecursiveKeys = allowRecursiveKeys;
    }

    public boolean isEnumCaseSensitive() {
        return enumCaseSensitive;
    }

    public void setEnumCaseSensitive(boolean enumCaseSensitive) {
        this.enumCaseSensitive = enumCaseSensitive;
    }

    public int getNestingDepthLimit() {
        return nestingDepthLimit;
    }

    public void setNestingDepthLimit(int nestingDepthLimit) {
        this.nestingDepthLimit = nestingDepthLimit;
    }

    public int getCodePointLimit() {
        return codePointLimit;
    }

    public void setCodePointLimit(int codePointLimit) {
        this.codePointLimit = codePointLimit;
    }

    public boolean isMergeOnCompose() {
        return mergeOnCompose;
    }

    public void setMergeOnCompose(boolean mergeOnCompose) {
        this.mergeOnCompose = mergeOnCompose;
    }

    public TagInspector getTagInspector() {
        return tagInspector;
    }

    public void setTagInspector(TagInspector tagInspector) {
        this.tagInspector = tagInspector;
    }

    @Override
    public String toString() {
        return "YamlOptions{" +
                "defaultStyle=" + defaultStyle +
                ", defaultFlowStyle=" + defaultFlowStyle +
                ", canonical=" + canonical +
                ", allowUnicode=" + allowUnicode +
                ", allowReadOnlyProperties=" + allowReadOnlyProperties +
                ", indent=" + indent +
                ", indicatorIndent=" + indicatorIndent +
                ", indentWithIndicator=" + indentWithIndicator +
                ", width=" + width +
                ", splitLines=" + splitLines +
                ", lineBreak=" + lineBreak +
                ", explicitStart=" + explicitStart +
                ", explicitEnd=" + explicitEnd +
                ", timeZone=" + timeZone +
                ", maxSimpleKeyLength=" + maxSimpleKeyLength +
                ", processComments=" + processComments +
                ", nonPrintableStyle=" + nonPrintableStyle +
                ", version=" + version +
                ", tags=" + tags +
                ", prettyFlow=" + prettyFlow +
                ", anchorGenerator=" + anchorGenerator +
                ", dereferenceAliases=" + dereferenceAliases +
                ", allowDuplicateKeys=" + allowDuplicateKeys +
                ", wrappedToRootException=" + wrappedToRootException +
                ", maxAliasesForCollections=" + maxAliasesForCollections +
                ", allowRecursiveKeys=" + allowRecursiveKeys +
                ", enumCaseSensitive=" + enumCaseSensitive +
                ", nestingDepthLimit=" + nestingDepthLimit +
                ", codePointLimit=" + codePointLimit +
                ", mergeOnCompose=" + mergeOnCompose +
                ", tagInspector=" + tagInspector +
                ", commentPrefix='" + super.getCommentPrefix() + '\'' +
                ", commentSuffix='" + super.getCommentSuffix() + '\'' +
                ", header='" + super.getHeader() + '\'' +
                ", footer='" + super.getFooter() + '\'' +
                '}';
    }
}
