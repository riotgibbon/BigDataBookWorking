package com.bigdata.c6;

import cascading.flow.FlowProcess;
import cascading.operation.FunctionCall;
import cascading.tap.Tap;
import cascading.tuple.Tuple;
import cascalog.CascalogFunction;
import cascalog.ops.IdentityBuffer;
import cascalog.ops.RandLong;
import com.backtype.hadoop.pail.Pail;
import com.backtype.hadoop.pail.PailSpec;
import com.backtype.hadoop.pail.PailStructure;
import com.bigdata.c3.SplitDataPailStructure;
import com.bigdata.thrift.Data;
import com.bigdata.thrift.DataUnit;
import com.bigdata.thrift.PageID;
import jcascalog.Api;
import jcascalog.Option;
import jcascalog.Subquery;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.backtype.cascading.tap.PailTap;

/**
 * Created by toby on 2/13/14.
 */
public class tools {

    private static final String rootPath = "/tmp/swa";
    private static final String snapshot = rootPath + "/snapshot";
    private static final String shredded= rootPath + "/shredded";
    private static final String normalizedUrls= rootPath + "/normalized_urls";
    private static final String normalizedPageViews= rootPath + "/normalized_pageview_users";
    private static final String uniquePageViews= rootPath + "/unique_pageviews";
    private static final String dataMaster = "/data/master";
    private static final String empty = "_";
    private static final String data = "?data";

    public static void setApplicationConf(){
        Map conf = new HashMap();
        String sers = "backtype.hadoop.ThriftSerialization," +
                "org.apache.hadoop.io.serializer.WritableSerialization";
        conf.put("io.serializations", sers);
        Api.setApplicationConf(conf);

    }

    public static void  ingest(Pail masterPail, Pail newDataPail) throws IOException {
        FileSystem fs = FileSystem.get(new Configuration());
        fs.delete(new Path(rootPath), true);
        fs.mkdirs(new Path(rootPath));
        Pail snapshotPail = newDataPail.snapshot(rootPath +"/newDataSnapshot");
        appendNewData(masterPail,snapshotPail);
        newDataPail.deleteSnapshot(snapshotPail);
    }

    public static void pailTapUsage(){
        Tap source = new PailTap(snapshot);
        new Subquery("?data").predicate(source, "_", "?data");
    }

    public static PailTap attributeTap(String path, final DataUnit._Fields... fields){
        PailTap.PailTapOptions opts = new PailTap.PailTapOptions();
        opts.attrs = new List[]{
            new ArrayList<String>(){{
                for(DataUnit._Fields field:fields)
                add("" + field.getThriftFieldId());
            }}
            };
        return new PailTap(snapshot, opts);
    }

    public static PailTap splitDataTap(String path){
        PailTap.PailTapOptions options = new PailTap.PailTapOptions();
        options.spec = new PailSpec((PailStructure) new SplitDataPailStructure());
        return  new PailTap(path, options);
    }

    public static Pail shred() throws  IOException{
        PailTap source = new PailTap(snapshot);
        PailTap sink = new PailTap(shredded);
        String rand = "?rand";
        String dataIn = "?data-in";
        Subquery reduced = new Subquery(rand, data)
                .predicate(source, empty, dataIn)
                .predicate(new RandLong())
                .out(rand)
                .predicate(new IdentityBuffer(), dataIn)
                .out(data);
        Api.execute(sink, new Subquery(data).predicate(reduced, empty, data));
        Pail shreddedPail = new Pail(shredded);
        shreddedPail.consolidate();
        return  shreddedPail;
    }

    private static void appendNewData(Pail masterPail, Pail snapshotPail) throws IOException {
        Pail shreddedPail  = shred();
        masterPail.absorb(shreddedPail);
    }

    public static class NormalizeURL extends CascalogFunction{

        @Override
        public void operate(FlowProcess flowProcess, FunctionCall call) {
            Data data= ((Data) call.getArguments().getObject(0)).deepCopy();
            DataUnit dataUnit = data.getDataunit();

            if(dataUnit.getSetField() == DataUnit._Fields.PAGE_VIEW) normalize(dataUnit.getPage_view().getPage());

            call.getOutputCollector().add(new Tuple(data));
        }

        private void normalize(PageID page) {
            if(page.getSetField()==PageID._Fields.URL){
                String urlStr = page.getUrl();
                try {
                    URL url  = new URL(urlStr);
                    page.setUrl(url.getProtocol()+"://" + url.getHost() + url.getPath());

                } catch (MalformedURLException e) {

                }
            }
        }

        public static void normalizeURLs(){
            Tap masterDataSet = new PailTap(dataMaster);
            Tap outTap = splitDataTap(normalizedUrls);
            String normalized = "?normalized";
            String raw = "?raw";
            Api.execute(outTap,
                    new Subquery(normalized)
                    .predicate(masterDataSet, empty, raw)
                    .predicate(new NormalizeURL(), raw)
                    .out(normalized)
                    );
        }

        public static void deduplicatePageviews(){
            Tap source = attributeTap(normalizedPageViews, DataUnit._Fields.PAGE_VIEW);
            Tap outTap = splitDataTap(uniquePageViews);

            Api.execute(outTap,
                    new Subquery(data)
                    .predicate(source,data)
                    .predicate(Option.DISTINCT, true)
            );
        }
    }

}
