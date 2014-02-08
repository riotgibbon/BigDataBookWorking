package com.bigdata.c4;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

import java.io.IOException;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: 36015To
 * Date: 07/02/14
 * Time: 09:35
 * To change this template use File | Settings | File Templates.
 */
public class WordFrequencyVsLength {
    public static  void main(String [] args) throws Exception{
        FileSystem.get(new Configuration()).delete(new Path(args[1]),true);
        String tmpPath = "/tmp/" + UUID.randomUUID().toString();
        runWordBucketer(args[0], tmpPath);
        runBucketAverager(tmpPath, args[1]);
        FileSystem.get(new Configuration())
                .delete(new Path(tmpPath), true);
    }

    private static void runWordBucketer( // first job
                                         String input, String output) throws Exception {
        JobConf conf = new JobConf(WordFrequencyVsLength.class);
        conf.setJobName("freqVsAvg1");
        conf.setMapperClass(SplitAndFilterMapper.class);
        conf.setReducerClass(LengthToCountReducer.class);
        conf.setMapOutputKeyClass(Text.class);
        conf.setMapOutputValueClass(IntWritable.class);
        conf.setOutputKeyClass(IntWritable.class);
        conf.setOutputValueClass(IntWritable.class);
        FileInputFormat.setInputPaths(conf, new Path(input));
        FileOutputFormat.setOutputPath(conf, new Path(output));
        conf.setInputFormat(TextInputFormat.class);
        conf.setOutputFormat(SequenceFileOutputFormat.class);
        JobClient.runJob(conf);
    }

    private static void runBucketAverager(
            String input, String output) throws Exception { // second job
        JobConf conf = new JobConf(WordFrequencyVsLength.class);
        conf.setJobName("freqVsAvg2");
        conf.setMapperClass(PassThroughMapper.class);
        conf.setReducerClass(AverageReducer.class);
        conf.setMapOutputKeyClass(IntWritable.class);
        conf.setMapOutputValueClass(IntWritable.class);
        conf.setOutputKeyClass(IntWritable.class);
        conf.setOutputValueClass(DoubleWritable.class);
        FileInputFormat.setInputPaths(conf, new Path(input));
        FileOutputFormat.setOutputPath(conf, new Path(output));
        conf.setInputFormat(SequenceFileInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class);
        JobClient.runJob(conf);
    }
}
