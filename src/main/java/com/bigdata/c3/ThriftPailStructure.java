package com.bigdata.c3;

import com.backtype.hadoop.pail.PailStructure;
import org.apache.thrift.*;

/**
 * Created with IntelliJ IDEA.
 * User: 36015To
 * Date: 05/02/14
 * Time: 13:26
 * To change this template use File | Settings | File Templates.
 */
public abstract class  ThriftPailStructure <T extends Comparable> implements PailStructure<T> {
    private transient TSerializer serializer;
    private transient TDeserializer deserializer;

    private TSerializer getSerializer(){
        if (serializer==null) serializer = new TSerializer();
        return serializer;
    }
    private TDeserializer getDeserializer(){
        if(deserializer==null) deserializer = new TDeserializer();
        return deserializer;
    }

    public byte[] serialize(T obj){
        try {
            return getSerializer().serialize((TBase)obj);
        } catch (TException e) {
            throw new RuntimeException(e);
        }
    }

    public T deserialize(byte[] record){
        T ret = createThriftObject();
        try {
            getDeserializer().deserialize((TBase)ret,record);
        }catch (TException e){
            throw new RuntimeException(e);
        }
        return ret;
    }

    protected abstract T createThriftObject();

}
