package com.example.ReviewsInTheStore.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.example.FeedbackCheckRequest;
import org.example.FeedbackCheckResponse;
import org.example.SpamCheckServiceGrpc;

public class SpamCheckClient {
    public static FeedbackCheckResponse checkFeedbackIsSpam(String message) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        SpamCheckServiceGrpc.SpamCheckServiceBlockingStub stub =
                SpamCheckServiceGrpc.newBlockingStub(channel);

        FeedbackCheckRequest request = FeedbackCheckRequest.newBuilder()
                .setMessage(message)
                .build();

        FeedbackCheckResponse response = stub.checkFeedback(request);
        channel.shutdown();

        return response;
    }
}
