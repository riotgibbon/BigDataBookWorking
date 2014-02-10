package com.bigdata.c5.CustomPredicates;

import cascading.flow.FlowProcess;
import cascading.operation.FilterCall;
import cascalog.CascalogFilter;

/**
 * Created with IntelliJ IDEA.
 * User: toby
 * Date: 2/10/14
 * Time: 5:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class GreaterThanThirtyFilter extends CascalogFilter  {
    @Override
    public boolean isKeep(FlowProcess flowProcess, FilterCall filterCall) {
        return filterCall.getArguments().getInteger(0)>30;
    }
}
