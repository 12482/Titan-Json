//package com.test;
//import com.thinkaurelius.titan.core.TitanFactory;
//import com.thinkaurelius.titan.core.TitanGraph;
//import com.thinkaurelius.titan.example.GraphOfTheGodsFactory;
//
//import org.junit.Test;
//
//import org.apache.tinkerpop.gremlin.structure.Graph;
//import org.apache.tinkerpop.gremlin.structure.io.IoCore;
//import org.apache.tinkerpop.gremlin.structure.io.graphson.GraphSONMapper;
//import org.apache.tinkerpop.gremlin.structure.io.graphson.GraphSONReader;
//import org.apache.tinkerpop.gremlin.structure.io.graphson.GraphSONWriter;
//
//import static org.hamcrest.Matchers.is;
//import static org.junit.Assert.assertThat;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.InputStream;
//import java.io.OutputStream;
//
//import static java.nio.charset.StandardCharsets.UTF_8;
//
//public class TitanGraphSonBugTest {
//
//    @Test
//    public void can_save_and_load_GraphSON() throws Exception {
//        // Construct an in-memory "Graph of the Gods".
//        final TitanGraph g1 = makeEmptyTitan();
//        final boolean uniqueNameCompositeIndex = false;
//        GraphOfTheGodsFactory.loadWithoutMixedIndex(g1, uniqueNameCompositeIndex);
//
//        // See how many elements are in the original.
//        final long numVertices = g1.traversal().V().count().next();
//        final long numEdges = g1.traversal().E().count().next();
//
//        // Save the "Graph of the Gods" to a string.
//        final OutputStream output = new ByteArrayOutputStream();
//        saveGraphSON(g1, output);
//        final String graphson = output.toString();
//
//        // Load the GraphSON string into an empty, in-memory Titan graph.
//        final Graph g2 = makeEmptyTitan();
//        final InputStream input = new ByteArrayInputStream(graphson.getBytes(UTF_8));
//        loadGraphSON(g2, input);
//
//        // Make sure we have the same number of elements.
////        assertThat(g2.traversal().V().count().next(), is(numVertices));
////        assertThat(g2.traversal().E().count().next(), is(numEdges));
//    }
//
//    /** Create an empty, in-memory Titan graph. */
//    private static TitanGraph makeEmptyTitan() {
//        // http://s3.thinkaurelius.com/docs/titan/1.0.0/inmemorystorage.html
//        return TitanFactory.build().set("storage.backend", "inmemory").open();
//    }
//
//    /** Saves the graph as GraphSON. */
//    private static void saveGraphSON(final Graph graph, final OutputStream stream) throws Exception {
//        final GraphSONWriter.Builder builder = GraphSONWriter.build();
//        final GraphSONMapper mapper = newGraphSONMapper(graph);
//        builder.mapper(mapper);
//        final GraphSONWriter writer = builder.create();
//        writer.writeGraph(stream, graph);
//    }
//
//    /** Loads the graph as GraphSON. */
//    private static void loadGraphSON(final Graph graph, final InputStream stream) throws Exception {
//        final GraphSONReader.Builder builder = GraphSONReader.build();
//        final GraphSONMapper mapper = newGraphSONMapper(graph);
//        builder.mapper(mapper);
//        final GraphSONReader reader = builder.create();
//        reader.readGraph(stream, graph);
//    }
//
//    /** Create a GraphSONMapper that preserves types. */
//    private static GraphSONMapper newGraphSONMapper(final Graph graph) {
//        final GraphSONMapper.Builder builder = graph.io(IoCore.graphson()).mapper();
//        // Different failure with embedded type info.
//        // builder.embedTypes(true);
//        return builder.create();
//    }
//}
