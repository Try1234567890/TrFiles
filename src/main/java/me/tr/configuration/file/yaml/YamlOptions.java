package me.tr.configuration.file.yaml;

import me.tr.configuration.file.FileOptions;
import me.tr.configuration.memory.MemoryConfiguration;
import org.yaml.snakeyaml.DumperOptions;

public class YamlOptions extends FileOptions {
    private int intent = 2;
    private DumperOptions.FlowStyle flowStyle = DumperOptions.FlowStyle.BLOCK;

    protected YamlOptions(MemoryConfiguration configuration) {
        super(configuration);
    }

    public int indent() {
        return intent;
    }

    public YamlOptions intent(int intent) {
        if (intent < 2 || intent > 9) {
            throw new IllegalArgumentException("Indentation must be between 2 and 9");
        }
        this.intent = intent;
        return this;
    }

    public DumperOptions.FlowStyle flowStyle() {
        return flowStyle;
    }

    public YamlOptions flowStyle(DumperOptions.FlowStyle flowStyle) {
        this.flowStyle = flowStyle;
        return this;
    }
}
