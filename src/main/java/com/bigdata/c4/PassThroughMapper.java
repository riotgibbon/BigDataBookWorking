package com.bigdata.c4;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

import java.io.IOException;


/**
 * Created with IntelliJ IDEA.
 * User: 36015To
 * Date: 07/02/14
 * Time: 09:26
 * To change this template use File | Settings | File Templates.
 */
public class PassThroughMapper extends MapReduceBase implements Mapper<IntWritable, IntWritable, IntWritable, IntWritable> {
    @Override
    public void map(IntWritable key, IntWritable count,
                    OutputCollector<IntWritable, IntWritable> outputCollector,
                    Reporter reporter) throws IOException {
        outputCollector.collect(key,count);
    }
}
