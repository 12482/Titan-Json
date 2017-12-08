package com.titan.util;

import java.io.FileOutputStream;

import org.apache.commons.configuration.BaseConfiguration;
import org.apache.tinkerpop.gremlin.structure.io.graphson.GraphSONWriter;

import com.thinkaurelius.titan.core.TitanFactory;
import com.thinkaurelius.titan.core.TitanGraph;
import com.thinkaurelius.titan.core.TitanTransaction;
import com.thinkaurelius.titan.core.TransactionBuilder;
import com.thinkaurelius.titan.core.schema.TitanManagement;
import com.thinkaurelius.titan.graphdb.tinkerpop.TitanBlueprintsGraph;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.GraphFactory;

public class TitanConn {

	private TitanGraph graph = null;
	private String keyspace = "nknowledgegraph";
	private String index_name = "nknowledgegraph_hbase_index";
	private String index_addr = "192.168.233.130";
	// private String store_addr =
	// "172.19.104.33:4191,172.19.104.35:4191,172.19.104.36:4191";
	private static String store_addr = "192.168.233.130";
	

	public TitanConn(String keyspace, String index, String index_addr, String store_addr) {
		// if (StringUtil.isNotEmpty(keyspace) && StringUtil.isNotEmpty(index)
		// &&
		// StringUtil.isNotEmpty(index_addr)&&StringUtil.isNotEmpty(store_addr))
		// {
		this.keyspace = keyspace;
		this.index_name = index;
		this.index_addr = index_addr;
		this.store_addr = store_addr;
		// }
	}

	public TitanConn() {
		BaseConfiguration baseConfiguration = new BaseConfiguration();
		// baseConfiguration.setProperty("storage.backend", "hbase");
//		baseConfiguration.setProperty("storage.backend", "cassandra");
		baseConfiguration.setProperty("storage.backend","cassandrathrift");
		baseConfiguration.setProperty("storage.port", "9160");
		baseConfiguration.setProperty("storage.hostname", store_addr);
		// baseConfiguration.setProperty("storage.hbase.table", keyspace);
		// baseConfiguration.setProperty("cache.db-cache", "true");
		// builder.set("storage.hostname",
		// "172.19.104.54,172.19.104.55,172.19.104.56");
		baseConfiguration.setProperty("storage.cassandra.keyspace", keyspace);
		baseConfiguration.setProperty("index.search.backend", "elasticsearch");
		baseConfiguration.setProperty("index.search.hostname", index_addr);
		baseConfiguration.setProperty("index.search.index-name", index_name);
		baseConfiguration.setProperty("index.search.elasticsearch.interface", "TRANSPORT_CLIENT");
		baseConfiguration.setProperty("index.search.elasticsearch.cluster-name", "elasticsearch");
		baseConfiguration.setProperty("index.search.elasticsearch.client-only", "true");

		graph = TitanFactory.open(baseConfiguration);
	}

	public TitanGraph getTitanGraph() {
		if (graph == null || graph.isClosed()) {
			BaseConfiguration baseConfiguration = new BaseConfiguration();
			// baseConfiguration.setProperty("storage.backend", "hbase");
			baseConfiguration.setProperty("storage.backend", "cassandra");
			baseConfiguration.setProperty("storage.hostname", store_addr);
			// baseConfiguration.setProperty("storage.hbase.table", keyspace);
			// baseConfiguration.setProperty("cache.db-cache", "true");
			// builder.set("storage.hostname",
			// "172.19.104.54,172.19.104.55,172.19.104.56");
			baseConfiguration.setProperty("storage.cassandra.keyspace", keyspace);
			baseConfiguration.setProperty("index.search.backend", "elasticsearch");
			baseConfiguration.setProperty("index.search.hostname", index_addr);
			baseConfiguration.setProperty("index.search.index-name", index_name);
			baseConfiguration.setProperty("index.search.elasticsearch.interface", "TRANSPORT_CLIENT");
			baseConfiguration.setProperty("index.search.elasticsearch.cluster-name", "elasticsearch");
			baseConfiguration.setProperty("index.search.elasticsearch.client-only", "true");

			graph = TitanFactory.open(baseConfiguration);
			
			// TitanManagement manager = graph.openManagement();
		}
		return graph;
	}

	public TitanTransaction getTitanTrans() {
		return getTitanGraph().newTransaction();
	}
	
	public Graph  getGraph(){
		BaseConfiguration baseConfiguration = new BaseConfiguration();
		// baseConfiguration.setProperty("storage.backend", "hbase");
//		baseConfiguration.setProperty("storage.backend", "cassandra");
		
//		baseConfiguration.setProperty("blueprints.graph","com.thinkaurelius.titan.core.TitanFactory");
		//com.tinkerpop.blueprints.impls.
		baseConfiguration.setProperty("storage.backend","cassandrathrift");
		baseConfiguration.setProperty("storage.port", "9160");
		baseConfiguration.setProperty("storage.hostname", store_addr);
		// baseConfiguration.setProperty("storage.hbase.table", keyspace);
		// baseConfiguration.setProperty("cache.db-cache", "true");
		// builder.set("storage.hostname",
		// "172.19.104.54,172.19.104.55,172.19.104.56");
		baseConfiguration.setProperty("storage.cassandra.keyspace", keyspace);
		baseConfiguration.setProperty("index.search.backend", "elasticsearch");
		baseConfiguration.setProperty("index.search.hostname", index_addr);
		baseConfiguration.setProperty("index.search.index-name", index_name);
		baseConfiguration.setProperty("index.search.elasticsearch.interface", "TRANSPORT_CLIENT");
		baseConfiguration.setProperty("index.search.elasticsearch.cluster-name", "elasticsearch");
		baseConfiguration.setProperty("index.search.elasticsearch.client-only", "true");
		Graph g = GraphFactory.open(baseConfiguration);
		return g;
	}

	public void close(TitanTransaction ts) {
		if (ts != null && !ts.isClosed()) {
			ts.close();
		}
	}

	public static void main(String[] args) {
		TitanConn tc = new TitanConn();
		tc.getGraph();
		System.err.println("over");
	}

}
