package me.tr.trfiles.file.placeholders;

import me.tr.trformatter.palceholders.functions.Function;
import me.tr.trformatter.palceholders.params.Params;
import me.tr.trformatter.palceholders.placeholders.Placeholder;
import me.tr.trformatter.uids.UID;

import java.io.File;


public class FileSize extends Placeholder {
    public FileSize() {
        super(new UID("FileSize"));
    }

    public FileSize(Params params, Function[] functions, int start, int end) {
        super(new UID("FileSize"), params, functions, start, end, 1);
    }

    @Override
    public String process(String str) {
        File configFile = getParams().asFile(0);
        if (!configFile.exists()) {
            return "FileNotExists";
        }
        if (!configFile.isFile()) {
            return "IsNotAFile";
        }
        return String.valueOf(configFile.length());
    }

    @Override
    public FileSize newInstance(Params params, Function[] functions, int start, int end) {
        return new FileSize(params, functions, start, end);
    }
}
