package com.bigdata.c3;

import java.io.IOException;

public class WritePartitioned {

    private static final String partitioned = "/tmp/partitioned_logins";


    public static void main(String[] args) throws IOException {
        Utils.writeLogins(partitioned, "eve", "fred", new PartitionedLoginPailStructure());
        String partitionedDirectory = Utils.getFormattedDate(Utils.getUnixTime());
        //Utils.readLogins(partitioned );
    }

}
