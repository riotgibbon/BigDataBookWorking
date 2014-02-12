package com.bigdata.c5.composition;

import com.bigdata.c5.Data;
import com.twitter.maple.tap.StdoutTap;
import jcascalog.Api;
import jcascalog.Subquery;
import jcascalog.op.*;
import jcascalog.example.Split;

/**
 * Created with IntelliJ IDEA.
 * User: toby
 * Date: 2/11/14
 * Time: 10:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class DynamicWordCount {
    public static  void main(String [] args) throws Exception{

        String count = "?count";
        String word = "?word";
        String sentence = "?sentence";
        String numWords = "?num-words";

        Subquery wordCount = new Subquery(word, count)
                .predicate(Data.SENTENCE, sentence)
                .predicate(new Split(), sentence).out(word)
                .predicate(new Count(), count);

        Api.execute(new StdoutTap(),   wordCount);

        Api.execute(new StdoutTap(),
                new Subquery(count, numWords)
                    .predicate(wordCount, "_", count)
                    .predicate(new Count(),numWords));

        Api.execute(new StdoutTap(),
                new Subquery(numWords)
                        .predicate(wordCount, "_", count)
                        .predicate(new Count(),numWords));
    }

}
