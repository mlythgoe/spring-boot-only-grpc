package net.mikelythgoe.springbootonlygrpc;

import io.grpc.stub.StreamObserver;
import net.mikelythgoe.springbootonlygrpc.proto.HelloReply;
import net.mikelythgoe.springbootonlygrpc.proto.HelloRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

class GrpcServerServiceTest {

    private GrpcServerService service;

    @BeforeEach
    void setUp() {
        service = new GrpcServerService();
    }

    @Nested
    @DisplayName("sayHello")
    class SayHelloTests {

        @Test
        @DisplayName("should return greeting with name")
        void shouldReturnGreetingWithName() {
            HelloRequest request = HelloRequest.newBuilder()
                    .setName("World")
                    .build();
            TestStreamObserver<HelloReply> responseObserver = new TestStreamObserver<>();

            service.sayHello(request, responseObserver);

            assertEquals(1, responseObserver.getValues().size());
            assertEquals("Hello ==> World", responseObserver.getValues().get(0).getMessage());
            assertTrue(responseObserver.isCompleted());
            assertNull(responseObserver.getError());
        }

        @Test
        @DisplayName("should throw IllegalArgumentException when name starts with 'error'")
        void shouldThrowIllegalArgumentExceptionForErrorName() {
            HelloRequest request = HelloRequest.newBuilder()
                    .setName("error-test")
                    .build();
            TestStreamObserver<HelloReply> responseObserver = new TestStreamObserver<>();

            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> service.sayHello(request, responseObserver)
            );

            assertEquals("Bad name: error-test", exception.getMessage());
        }

        @Test
        @DisplayName("should throw RuntimeException when name starts with 'internal'")
        void shouldThrowRuntimeExceptionForInternalName() {
            HelloRequest request = HelloRequest.newBuilder()
                    .setName("internal-failure")
                    .build();
            TestStreamObserver<HelloReply> responseObserver = new TestStreamObserver<>();

            assertThrows(
                    RuntimeException.class,
                    () -> service.sayHello(request, responseObserver)
            );
        }

        @Test
        @DisplayName("should handle empty name")
        void shouldHandleEmptyName() {
            HelloRequest request = HelloRequest.newBuilder()
                    .setName("")
                    .build();
            TestStreamObserver<HelloReply> responseObserver = new TestStreamObserver<>();

            service.sayHello(request, responseObserver);

            assertEquals("Hello ==> ", responseObserver.getValues().get(0).getMessage());
            assertTrue(responseObserver.isCompleted());
        }
    }

    @Nested
    @DisplayName("streamHello")
    class StreamHelloTests {

        @Test
        @DisplayName("should stream 10 messages with incrementing count")
        void shouldStream10Messages() {
            HelloRequest request = HelloRequest.newBuilder()
                    .setName("StreamTest")
                    .build();
            TestStreamObserver<HelloReply> responseObserver = new TestStreamObserver<>();

            service.streamHello(request, responseObserver);

            assertEquals(10, responseObserver.getValues().size());
            assertTrue(responseObserver.isCompleted());
            assertNull(responseObserver.getError());

            // Verify first and last messages
            assertEquals("Hello(0) ==> StreamTest", responseObserver.getValues().get(0).getMessage());
            assertEquals("Hello(9) ==> StreamTest", responseObserver.getValues().get(9).getMessage());
        }

        @Test
        @DisplayName("should include correct count in each message")
        void shouldIncludeCorrectCountInMessages() {
            HelloRequest request = HelloRequest.newBuilder()
                    .setName("Test")
                    .build();
            TestStreamObserver<HelloReply> responseObserver = new TestStreamObserver<>();

            service.streamHello(request, responseObserver);

            for (int i = 0; i < 10; i++) {
                String expectedMessage = "Hello(" + i + ") ==> Test";
                assertEquals(expectedMessage, responseObserver.getValues().get(i).getMessage());
            }
        }
    }

    /**
     * Test implementation of StreamObserver for capturing responses.
     */
    private static class TestStreamObserver<T> implements StreamObserver<T> {
        private final List<T> values = new ArrayList<>();
        private final AtomicBoolean completed = new AtomicBoolean(false);
        private final AtomicReference<Throwable> error = new AtomicReference<>();

        @Override
        public void onNext(T value) {
            values.add(value);
        }

        @Override
        public void onError(Throwable t) {
            error.set(t);
        }

        @Override
        public void onCompleted() {
            completed.set(true);
        }

        public List<T> getValues() {
            return values;
        }

        public boolean isCompleted() {
            return completed.get();
        }

        public Throwable getError() {
            return error.get();
        }
    }
}
