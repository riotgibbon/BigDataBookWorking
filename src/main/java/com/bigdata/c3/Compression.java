package com.bigdata.c3;

import com.backtype.hadoop.pail.Pail;
import com.backtype.hadoop.pail.PailSpec;
import com.backtype.hadoop.pail.SequenceFileFormat;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: 36015To
 * Date: 05/02/14
 * Time: 13:14
 * To change this template use File | Settings | File Templates.
 */
public class Compression {
    public static void main(String[] args) throws IOException {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(SequenceFileFormat.CODEC_ARG, SequenceFileFormat.CODEC_ARG_GZIP);
        options.put(SequenceFileFormat.TYPE_ARG, SequenceFileFormat.TYPE_ARG_BLOCK);
        LoginPailStructure struct = new LoginPailStructure();
        Pail compressed = Pail.create("/tmp/compressed", new PailSpec("SequenceFile", options,struct));


    }
}
