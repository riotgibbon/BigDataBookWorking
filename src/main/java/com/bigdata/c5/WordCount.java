package com.bigdata.c5;

import com.twitter.maple.tap.StdoutTap;
import jcascalog.Api;
import jcascalog.Subquery;
import jcascalog.example.Split;
import jcascalog.op.Count;

public class WordCount {

    public static  void main(String [] args) throws Exception{
        String count = "?count";
        String word = "?word";
        String sentence = "?sentence";
        Api.execute(new StdoutTap(),
                new Subquery(word, count)
                        .predicate(Data.SENTENCE, sentence)
                        .predicate(new Split(), sentence).out(word)
                        .predicate(new Count(), count)
        );

    }
}
