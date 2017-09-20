package ai.marbles.easysrl;

import ai.marbles.grpc.ServiceAcceptor;
import ai.marbles.grpc.ServiceConnector;
import ai.marbles.grpc.ccg.RawSentence;
import ai.marbles.grpc.ccg.SimpleCcgParserGrpc;
import edu.uw.easysrl.main.CcgServiceHandler;
import static org.junit.Assert.*;
import org.junit.Test;

/*
 * A testing Client that sends a single query to EasySRL Server and prints the results.
 */
public class EasySRLDaemonTest {

    @Test
    public void testClientServer() {
        // Collect the port number.
        int port = 9083;

        // User.
        ServiceAcceptor server = null;
        try {

            System.out.println("Starting EasySRLDaemonTest...");
            CcgServiceHandler basesvc = new CcgServiceHandler("./easysrl/model");
            basesvc.init();
            server = new ServiceAcceptor(port, new CcgServiceHandler.SimpleParser(basesvc));
            server.start();
            System.out.println("EasySRL parser service at port " + port);

            ServiceConnector client = new ServiceConnector("localhost", port);
            SimpleCcgParserGrpc.SimpleCcgParserBlockingStub stub = SimpleCcgParserGrpc.newBlockingStub(client.getChannel());

            RawSentence request = RawSentence.newBuilder()
                        .setRaw("It is my first morning of high school.")
                        .build();
            String answer = stub.parse(request).getResult().toString().replaceFirst("\\s+$", "");
            String expected = "(<T S[dcl] 1 2> (<L NP PRP PRP It NP>) (<T S[dcl]\\NP 0 2> (<L (S[dcl]\\NP)/NP VBZ VBZ is (S[dcl]\\NP)/NP>) (<T NP 0 2> (<L NP/N PRP$ PRP$ my NP/N>) (<T N 1 2> (<L N/N JJ JJ first N/N>) (<T N 0 2> (<L N/PP NN NN morning N/PP>) (<T PP 0 2> (<L PP/NP IN IN of PP/NP>) (<T NP 0 1> (<T N 1 2> (<L N/N JJ JJ high N/N>) (<L N NN NN school. N>) ) ) ) ) ) ) ) )";
            // Print the answer.
            assertEquals(expected, answer);

            client.shutdown();
            assertTrue(client.blockUntilShutdown(3000));
        } catch (Exception e) {
            fail(e.getMessage());
        }
        try {
            if (server != null) {
                server.shutdown();
                assertTrue(server.blockUntilShutdown(3000));
            }
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
