package com.bigdata.c3;

import com.bigdata.thrift.Data;
import com.bigdata.thrift.DataUnit;
import org.apache.thrift.meta_data.FieldValueMetaData;
import org.apache.thrift.meta_data.StructMetaData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 36015To
 * Date: 05/02/14
 * Time: 15:40
 * To change this template use File | Settings | File Templates.
 */
public class SplitDataPailStructure extends DataPailStructure {
    public static HashMap<Short, FieldStructure> validFieldMap = new HashMap<Short, FieldStructure>();

    static {
        for (DataUnit._Fields k: DataUnit.metaDataMap.keySet()){
            FieldValueMetaData md = DataUnit.metaDataMap.get(k).valueMetaData;
            FieldStructure fieldStructure;
            if(md instanceof StructMetaData && ((StructMetaData) md).structClass.getName().endsWith("Property"))  {
                fieldStructure = new PropertyStructure(((StructMetaData) md).structClass );
            }  else {
                fieldStructure = new EdgeStructure();
            }
            validFieldMap.put(k.getThriftFieldId(),fieldStructure);
        }
    }
    public List<String> getTarget(Data object) {
        List<String> ret = new ArrayList<String>();
        DataUnit du = object.getDataunit();
        short id = du.getSetField().getThriftFieldId();
        ret.add("" + id);
        validFieldMap.get(id).fillTarget(ret, du.getFieldValue());
        return ret;
    }
    public boolean isValidTarget(String[] dirs) {
        if(dirs.length==0) return false;
        try {
            short id = Short.parseShort(dirs[0]);
            FieldStructure s = validFieldMap.get(id);
            if(s==null)
                return false;
            else
                return s.isValidTarget(dirs);
        } catch(NumberFormatException e) {
            return false;
        }
    }
}
