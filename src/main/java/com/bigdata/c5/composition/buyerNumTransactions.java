package com.bigdata.c5.composition;

import com.twitter.maple.tap.StdoutTap;
import jcascalog.Api;

/**
 * Created with IntelliJ IDEA.
 * User: toby
 * Date: 2/12/14
 * Time: 10:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class buyerNumTransactions {
    public static  void main(String [] args) throws Exception{
          Api.execute(new StdoutTap(), DynamicSubqueries.sourceNumTransactions("/tmp/json/input.txt", "?buyer"));
      }
}
