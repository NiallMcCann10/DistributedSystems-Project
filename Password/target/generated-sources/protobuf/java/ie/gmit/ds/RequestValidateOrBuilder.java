// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Password.proto

package ie.gmit.ds;

public interface RequestValidateOrBuilder extends
    // @@protoc_insertion_point(interface_extends:ie.gmit.ds.RequestValidate)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string password = 1;</code>
   */
  java.lang.String getPassword();
  /**
   * <code>string password = 1;</code>
   */
  com.google.protobuf.ByteString
      getPasswordBytes();

  /**
   * <code>bytes hashedPassword = 2;</code>
   */
  com.google.protobuf.ByteString getHashedPassword();

  /**
   * <code>bytes salt = 3;</code>
   */
  com.google.protobuf.ByteString getSalt();
}
