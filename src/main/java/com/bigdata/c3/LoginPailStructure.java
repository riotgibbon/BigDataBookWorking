package com.bigdata.c3;

import com.backtype.hadoop.pail.PailStructure;

import java.io.*;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 36015To
 * Date: 04/02/14
 * Time: 19:52
 * To change this template use File | Settings | File Templates.
 */
public class LoginPailStructure implements PailStructure<Login> {
    @Override
    public boolean isValidTarget(String... dirs) {
        return true;
    }

    @Override
    public Login deserialize(byte[] serialized) {
        DataInputStream dataIn =  new DataInputStream(new ByteArrayInputStream(serialized));
        try {
            byte[] userBytes = new byte[dataIn.readInt()];
            dataIn.read(userBytes);
            return  new Login(new String(userBytes), dataIn.readLong());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] serialize(Login login) {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        DataOutputStream dataOut = new DataOutputStream(byteOut);
        byte [] userBytes = login.userName.getBytes();
        try {
            dataOut.writeInt(userBytes.length);
            dataOut.write(userBytes);
            dataOut.writeLong(login.loginUnixTime);
            dataOut.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return byteOut.toByteArray();
    }

    @Override
    public List<String> getTarget(Login object) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public Class getType() {
        return Login.class;
    }
}
