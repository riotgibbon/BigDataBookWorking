package com.bigdata.c4;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

import java.io.IOException;
import java.util.Iterator;


class LengthToCountReducer extends MapReduceBase implements Reducer<Text, IntWritable,IntWritable,IntWritable> {
    @Override
    public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<IntWritable, IntWritable> output, Reporter reporter) throws IOException {
        int sum = 0;
        while (values.hasNext()){
            sum+=values.next().get();
        }
        output.collect(new IntWritable(key.toString().length()), new IntWritable(sum));
    }
}
