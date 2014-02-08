package com.bigdata.c4;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.*;

import java.io.IOException;
import java.util.Iterator;


/**
 * Created with IntelliJ IDEA.
 * User: 36015To
 * Date: 05/02/14
 * Time: 19:34
 * To change this template use File | Settings | File Templates.
 */
class WordCountReducer extends MapReduceBase implements Reducer<Text, IntWritable,Text,IntWritable> {
    @Override
    public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
        int sum = 0;
        while (values.hasNext()){
            sum+=values.next().get();
        }
        output.collect(key, new IntWritable(sum));
    }
}
