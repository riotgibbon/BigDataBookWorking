package com.bigdata.c4;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Created with IntelliJ IDEA.
 * User: 36015To
 * Date: 05/02/14
 * Time: 19:00
 * To change this template use File | Settings | File Templates.
 */
public class SplitAndFilterMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {
    private static final IntWritable one = new IntWritable(1);
    private Text word = new Text();

    public static final Set<String> STOP_WORDS = new HashSet<String>()
    {{
        add("the");
        add("a");
    }};

    @Override
    public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> outputCollector, Reporter reporter) throws IOException {
        String line = value.toString();
        StringTokenizer tokenizer = new StringTokenizer(line);
        while (tokenizer.hasMoreTokens()){

            String token = tokenizer.nextToken();
            if(!STOP_WORDS.contains(token)){
                word.set(token);
                outputCollector.collect(word,one);
            }
        }
    }
}
