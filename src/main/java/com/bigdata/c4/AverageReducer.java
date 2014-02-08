package com.bigdata.c4;

import org.apache.hadoop.mapred.*;
import org.apache.hadoop.io.*;

import java.io.IOException;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: 36015To
 * Date: 07/02/14
 * Time: 09:30
 * To change this template use File | Settings | File Templates.
 */
public class AverageReducer extends MapReduceBase implements Reducer<IntWritable, IntWritable, IntWritable, DoubleWritable> {
    @Override
    public void reduce(IntWritable key, Iterator<IntWritable> values,
                       OutputCollector<IntWritable, DoubleWritable> outputCollector, Reporter reporter) throws IOException {
        int sum = 0;
        int count = 0;
        while (values.hasNext() ){
            sum += values.next().get();
            count+=1;
        }
        double average = 1.0 * sum/count;
        outputCollector.collect(key, new DoubleWritable(average));
    }
}
