package com.tohdfs;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.io.IoCore;
import org.apache.tinkerpop.gremlin.structure.io.graphson.GraphSONMapper;
import org.apache.tinkerpop.gremlin.structure.io.graphson.GraphSONReader;
import org.apache.tinkerpop.gremlin.structure.io.graphson.GraphSONWriter;

import com.thinkaurelius.titan.core.TitanGraph;
import com.thinkaurelius.titan.core.TitanTransaction;
import com.thinkaurelius.titan.core.TitanVertex;
import com.titan.util.TitanConn;

public class TitanOutput {

	public static void main(String[] args) throws Exception {
		TitanConn conn = new TitanConn();
//		TitanTransaction tts = conn.getTitanTrans();

		// TitanVertex t1 = tts.addVertex("name","秦始皇");
		// t1.property("age","40");
		//
		// TitanVertex t2 = tts.addVertex("name","秦二世");
		// t2.property("age","20");
		//
		// TitanVertex t3 = tts.addVertex("name","扶苏");
		// TitanVertex t4 = tts.addVertex("name","蒙恬");
		// t4.property("nick","战神");
		//
		// TitanVertex t5 = tts.addVertex("name","蒙毅");
		// TitanVertex t6 = tts.addVertex("name","赵高");
		// t6.property("point","总管");
		//
		// t1.addEdge("儿子", t2);
		// t1.addEdge("儿子", t3);
		// t1.addEdge("臣子", t4);
		// t1.addEdge("臣子", t5);
		// t1.addEdge("总管", t6);
		//
		// t2.addEdge("兄弟", t3);
		//
		// t4.addEdge("对手", t6);
		//
		// tts.commit();
		// System.err.println("add success ..");
//		TitanVertex tv = tts.query().vertices().iterator().next();
//		System.err.println(tv.keys());

		TitanGraph graph = conn.getTitanGraph();
//		OutputStream out = new FileOutputStream("gson");
		
		
		//链接hdfs
		Configuration conf=new Configuration();
		conf.set("fs.defaultFS", "hdfs://cmpc2:9000");
		FileSystem fileSystem=FileSystem.get(conf);
		FSDataOutputStream out=fileSystem.create(new Path("/gson"),true) ;
		//导出
		saveGraphSON(graph, out);
		System.exit(0);
	}
	   /** Saves the graph as GraphSON. */
    private static void saveGraphSON(final Graph graph, final OutputStream stream) throws Exception {
        final GraphSONWriter.Builder builder = GraphSONWriter.build();
        final GraphSONMapper mapper = newGraphSONMapper(graph);
        builder.mapper(mapper);
        final GraphSONWriter writer = builder.create();
        writer.writeGraph(stream, graph);
    }
    
    
	
	   /** Create a GraphSONMapper that preserves types. */
    private static GraphSONMapper newGraphSONMapper(final Graph graph) {
//        final GraphSONMapper.Builder builder = graph.io(IoCore.graphson()).mapper();
        // Different failure with embedded type info.
        // builder.embedTypes(true);
        
        final GraphSONMapper.Builder builder = graph.io(IoCore.graphson()).mapper();
        builder.embedTypes(true);
        return builder.create();
//        return builder.create();
    }
    
    //outputStream转inputStream
    public static ByteArrayInputStream parse(OutputStream out) throws Exception
    {
        ByteArrayOutputStream   baos=new   ByteArrayOutputStream();
        baos=(ByteArrayOutputStream) out;
        ByteArrayInputStream swapStream = new ByteArrayInputStream(baos.toByteArray());
        return swapStream;
    }
    
    

}
