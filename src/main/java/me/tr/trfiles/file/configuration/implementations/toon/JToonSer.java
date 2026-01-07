package me.tr.trfiles.file.configuration.implementations.toon;

import dev.toonformat.jtoon.DecodeOptions;
import dev.toonformat.jtoon.EncodeOptions;
import dev.toonformat.jtoon.JToon;

import java.util.Map;

public record JToonSer(DecodeOptions decode, EncodeOptions encode) {

    public JToonSer() {
        this(new DecodeOptions(), new EncodeOptions());
    }

    public String encode(Object input) {
        return JToon.encode(input, encode);
    }

    public Map<?, ?> decode(String toon) {
        return (Map<?, ?>) JToon.decode(toon, decode);
    }
}
