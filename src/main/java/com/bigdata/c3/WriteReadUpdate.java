package com.bigdata.c3;

import java.io.IOException;

public class WriteReadUpdate {

    private static final String loginPath = "/tmp/logins";
    private static final String updatePath = "/tmp/updates";

    public static void main(String[] args) throws IOException {
        Utils.writeLogins(loginPath, "alex", "bob");
        Utils.readLogins(loginPath);

        Utils.writeLogins(updatePath, "chris", "dave");
        Utils.readLogins(updatePath);

        Utils.appendData(loginPath, updatePath);
        Utils.readLogins(loginPath);
    }

}
