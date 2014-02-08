package com.bigdata.c3;
import com.bigdata.thrift.*;

import java.util.Collections;
import java.util.List;


public class DataPailStructure extends ThriftPailStructure<Data> {
    @Override
    protected Data createThriftObject() {
        return new Data();
    }

    @Override
    public boolean isValidTarget(String... strings) {
        return true;
    }

    @Override
    public List<String> getTarget(Data data) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public Class getType() {
        return Data.class;
    }
}
