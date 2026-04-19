package net.mikelythgoe.springbootonlygrpc;

import io.grpc.stub.StreamObserver;
import net.mikelythgoe.springbootonlygrpc.proto.HelloReply;
import net.mikelythgoe.springbootonlygrpc.proto.HelloRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

@Service
class GrpcServerService extends net.mikelythgoe.springbootonlygrpc.proto.SimpleGrpc.SimpleImplBase {

    private static final Log log = LogFactory.getLog(GrpcServerService.class);

    @Override
    public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
        // validate request
        log.info("Hello " + req.getName());
        if (req.getName().startsWith("error")) {
            throw new IllegalArgumentException("Bad name: " + req.getName());
        }
        if (req.getName().startsWith("internal")) {
            throw new RuntimeException();
        }
        // Create a response
        HelloReply reply = HelloReply.newBuilder().setMessage("Hello ==> " + req.getName()).build();
        // Send the response
        responseObserver.onNext(reply);
        // Mark the end of the stream
        responseObserver.onCompleted();
    }

    @Override
    public void streamHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
        log.info("Hello " + req.getName());
        int count = 0;
        while (count < 10) {
            HelloReply reply = HelloReply.newBuilder().setMessage("Hello(" + count + ") ==> " + req.getName()).build();
            responseObserver.onNext(reply);
            count++;
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                responseObserver.onError(e);
                return;
            }
        }
        responseObserver.onCompleted();
    }
}
