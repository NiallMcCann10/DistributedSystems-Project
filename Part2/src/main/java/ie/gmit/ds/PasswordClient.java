package ie.gmit.ds;

import com.google.protobuf.BoolValue;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PasswordClient {
    private static final Logger logger =
            Logger.getLogger(PasswordClient.class.getName());
    private final ManagedChannel channel;
    private final PasswordServiceGrpc.PasswordServiceStub asyncPasswordService;
    private final PasswordServiceGrpc.PasswordServiceBlockingStub syncPasswordService;

    public PasswordClient(String host, int port) {
        channel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();
        syncPasswordService = PasswordServiceGrpc.newBlockingStub(channel);
        asyncPasswordService = PasswordServiceGrpc.newStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public ResponseHash PasswordHashed(RequestHash newPasswordHash) {
        ResponseHash result = null;
        try {
            result = syncPasswordService.hash(newPasswordHash);
        } catch (StatusRuntimeException ex) {
            logger.log(Level.WARNING, "RPC failed: {0}", ex.getStatus());
            return result;
        }
        if (result!= null) {
            logger.info("Successfully Hashed " + newPasswordHash);
            //logger.info("Successfully  " + newPasswordHash.getUserId());
        } else {
            logger.warning("Failed to Authenticate");
        }
        return result;
    }

    public boolean checkValidation(RequestValidate requestValidate) {
        boolean validationResponse = false;
        try {
            validationResponse = syncPasswordService.validate(requestValidate).getValue();
            return validationResponse;
        } catch (
            StatusRuntimeException ex) {
        }
        return validationResponse;
    }

}
