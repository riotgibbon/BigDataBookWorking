package com.bigdata.c5;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: toby
 * Date: 2/10/14
 * Time: 4:18 AM
 * To change this template use File | Settings | File Templates.
 */

public class PersonData        {

    public static List AGE = Arrays.asList(
            Arrays.asList("alice", 28),
            Arrays.asList("bob", 33),
            Arrays.asList("chris", 40),
            Arrays.asList("david", 25));

    public static List GENDER = Arrays.asList(
            Arrays.asList("alice","f"),
            Arrays.asList("bob","m"),
            Arrays.asList("chris", "m"),
            Arrays.asList("david", "m"),
            Arrays.asList("emily", "f"));

    public static List FOLLOWS = Arrays.asList(
            Arrays.asList("alice","david"),
            Arrays.asList("alice", "bob"),
            Arrays.asList("bob", "david"),
            Arrays.asList("emily", "gary"));
}
