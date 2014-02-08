package com.bigdata.c3;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 36015To
 * Date: 05/02/14
 * Time: 15:41
 * To change this template use File | Settings | File Templates.
 */
public interface FieldStructure {
    public boolean isValidTarget(String[] dirs);
    public void fillTarget(List<String> ret, Object val);
}
