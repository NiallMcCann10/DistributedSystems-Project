package ie.gmit.ds;

import com.google.protobuf.BoolValue;
import com.google.protobuf.ByteString;
import io.grpc.stub.StreamObserver;


import java.util.ArrayList;
import java.util.logging.Logger;

public class PasswordServiceImpl extends PasswordServiceGrpc.PasswordServiceImplBase {

    private ArrayList<RequestHash> LogindetailsList;
    private static final Logger logger =
            Logger.getLogger(PasswordServiceImpl.class.getName());

    public PasswordServiceImpl() { LogindetailsList = new ArrayList<>();}

    byte[] salt_2;
    byte[] hashedPassword_2;

    @Override
    public void hash(RequestHash request, StreamObserver<ResponseHash> responceObserver) {
        try {
            String pwd = request.getPassword();
            char[] pwdCharArray = pwd.toCharArray();
            byte[] salt = Passwords.getNextSalt();
            byte[] hashedPassword = Passwords.hash(pwdCharArray,salt);

            //Build Object
            ResponseHash responseHash = ResponseHash.newBuilder()
                    .setSalt(ByteString.copyFrom(salt))
                    .setUserId(request.getUserId())
                    .setHashedPassword(ByteString.copyFrom(hashedPassword))
                    .build();

            responceObserver.onNext(responseHash);
        } catch (RuntimeException ex) {
            responceObserver.onNext( null);
        }
        responceObserver.onCompleted();
    }

    @Override
    public void validate(RequestValidate request, StreamObserver<BoolValue> responceObserver) {
        try {
            String pwd = request.getPassword();
            char[] pwdCharArray = pwd.toCharArray();
            byte[] salt = Passwords.getNextSalt();
            byte[] hashedPassword = Passwords.hash(pwdCharArray, salt);

            logger.info("Checking the Validity of password");
            boolean validationRequest = Passwords.isExpectedPassword(pwdCharArray, salt, hashedPassword);
            if (validationRequest == true) {
                responceObserver.onNext(BoolValue.newBuilder().setValue(true).build());
            } else {
                responceObserver.onNext(BoolValue.newBuilder().setValue(false).build());
            }
        }catch(RuntimeException ex){
            responceObserver.onNext(BoolValue.newBuilder().setValue(false).build());
        }
            responceObserver.onCompleted();
        }
    }

