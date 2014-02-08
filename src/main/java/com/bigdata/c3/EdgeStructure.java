package com.bigdata.c3;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 36015To
 * Date: 05/02/14
 * Time: 15:54
 * To change this template use File | Settings | File Templates.
 */
public class EdgeStructure implements FieldStructure {
    @Override
    public boolean isValidTarget(String[] dirs) {
        return  true;
    }

    @Override
    public void fillTarget(List<String> ret, Object val) {}
}
