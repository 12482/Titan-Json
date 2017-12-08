package com.totitan;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.io.IoCore;
import org.apache.tinkerpop.gremlin.structure.io.graphson.GraphSONMapper;
import org.apache.tinkerpop.gremlin.structure.io.graphson.GraphSONReader;

import com.thinkaurelius.titan.core.TitanTransaction;
import com.thinkaurelius.titan.core.TitanVertex;
import com.titan.util.TitanConn;

/**
 * json 到 titan
 * @author Administrator
 *
 */
public class TitanInput {

	public static void main(String[] args) throws Exception {
		TitanConn conn = new TitanConn();
//		TitanTransaction tts = conn.getTitanTrans();
//		Iterator<TitanVertex>  iter = tts.query().vertices().iterator();
//		while(iter.hasNext()){
//			System.err.println("get ...");
//			TitanVertex t = iter.next();
//			System.err.println(t.value("name").toString());
//			t.remove();
//		}
//		tts.commit();
		FileInputStream stream = new FileInputStream("gson");
		loadGraphSON(conn.getTitanGraph(), stream);
		
		System.err.println("over ...");

	}
	
	/** Loads the graph as GraphSON. */
    private static void loadGraphSON(final Graph graph, final InputStream stream) throws Exception {
        final GraphSONReader.Builder builder = GraphSONReader.build();
        final GraphSONMapper mapper = newGraphSONMapper(graph);
        builder.mapper(mapper);
        final GraphSONReader reader = builder.create();
        reader.readGraph(stream, graph);
    }
    
	   /** Create a GraphSONMapper that preserves types. */
 private static GraphSONMapper newGraphSONMapper(final Graph graph) {
//     final GraphSONMapper.Builder builder = graph.io(IoCore.graphson()).mapper();
     // Different failure with embedded type info.
     // builder.embedTypes(true);
     
     final GraphSONMapper.Builder builder = graph.io(IoCore.graphson()).mapper();
     builder.embedTypes(true);
     return builder.create();
//     return builder.create();
 }

}
