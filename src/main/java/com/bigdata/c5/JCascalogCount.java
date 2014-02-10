package com.bigdata.c5;

import com.twitter.maple.tap.StdoutTap;
import jcascalog.Api;
import jcascalog.Subquery;
import jcascalog.example.Split;
import jcascalog.op.Count;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created with IntelliJ IDEA.
 * User: 36015To
 * Date: 07/02/14
 * Time: 10:35
 * To change this template use File | Settings | File Templates.
 */
public class JCascalogCount {

    static List  SENTENCE = Arrays.asList(
            Arrays.asList("Four score and seven years ago our fathers"),
            Arrays.asList("brought forth on this continent a new nation"),
            Arrays.asList("conceived in Liberty and dedicated to"),
            Arrays.asList("the proposition that all men are created equal"));

    public static  void main(String [] args) throws Exception{
        String count = "?count";
        String word = "?word";
        String sentence = "?sentence";
        Api.execute(new StdoutTap(),
                new Subquery(word, count)
                        .predicate(SENTENCE, sentence)
                        .predicate(new Split(), sentence).out(word)
                        .predicate(new Count(), count)
        );

    }
}
