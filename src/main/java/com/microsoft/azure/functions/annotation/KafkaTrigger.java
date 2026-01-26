/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.functions.annotation;

import com.microsoft.azure.functions.BrokerAuthenticationMode;
import com.microsoft.azure.functions.BrokerProtocol;
import com.microsoft.azure.functions.KafkaMessageKeyType;
import com.microsoft.azure.functions.OAuthBearerMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * <p>
 * Place this on a parameter whose value would come from Kafka, and causing the method to run
 * when Kafka event is consumed. The parameter type can be one of the following:
 * </p>
 *
 * <ul>
 *     <li>Any native Java types such as int, String, byte[]</li>
 *     <li>Nullable values using Optional&lt;T&gt;</li>
 *     <li>Any POJO type, currently supported only for Cardinality.One</li>
 * </ul>
 *
 * <p>
 * The following example shows a Java function that is invoked when messages are consumed with
 * the specified topic, brokerList, and consumerGroup on a Kafka cluster.
 * </p>
 *
 * <pre>
 * {@literal @}FunctionName("KafkaTrigger-Java")
 * public void run(
 *    {@literal @}KafkaTrigger(name = "kafkaTrigger",
 *                      topic = "users", 
 *                      brokerList="broker:29092",
 *                      consumerGroup="functions")
 *                      List&lt;Map&lt;String, String&gt;&gt; kafkaEventData,
 *     final ExecutionContext context
 * ) {
 *     context.getLogger().info(kafkaEventData);
 * }
 * </pre>
 *
 * @since 1.4.0
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface KafkaTrigger {

    /**
     * The variable name used in function code for the request or request body.
     * 
     * @return The variable name used in function code for the request or request body.
     */
    String name();

    /**
     * Defines the Topic.
     * 
     * @return The topic.
     */
    String topic();

    /**
     * Defines the BrokerList.
     * 
     * @return The brokerList.
     */
    String brokerList();

    /**
     * Defines the EventHub connection string when using KafkaOutput protocol header feature of Azure EventHubs.
     * 
     * @return The EventHub connection string.
     */
    String eventHubConnectionString() default "";
    /**
     * Cardinality of the trigger input.
     * Choose 'One' if the input is a single message or 'Many' if the input is an array of messages.
     * If you choose 'Many', please set a dataType. 
     * Default: 'One'
     * 
     * @return The cardinality.
     */
    Cardinality cardinality() default Cardinality.ONE;

    /**
     * <p>Defines how Functions runtime should treat the parameter value. Possible values are:</p>
     * <ul>
     *     <li>"": get the value as a string, and try to deserialize to actual parameter type like POJO</li>
     *     <li>string: always get the value as a string</li>
     *     <li>binary: get the value as a binary data, and try to deserialize to actual parameter type byte[]</li>
     * </ul>
     * @return The dataType which will be used by the Functions runtime.
     */
    String dataType() default "";

    /**
     * Defines the consumer group.
     * 
     * @return The consumer group.
     */
    String consumerGroup();

    /**
     * SASL mechanism to use for authentication.
     * Allowed values: Gssapi, Plain, ScramSha256, ScramSha512
     * Default: NOTSET
     * 
     * @return The broker authentication mode.
     */
    BrokerAuthenticationMode authenticationMode() default BrokerAuthenticationMode.NOTSET;

    /**
     * SASL username with the PLAIN and SASL-SCRAM-.. mechanisms
     * Default: ""
     * 
     * @return The SASL username.
     */
    String username() default "";

    /**
     * SASL password with the PLAIN and SASL-SCRAM-.. mechanisms
     * Default: ""
     *
     * security.protocol in librdkafka
     * 
     * @return The SASL password.
     */
    String password() default "";

    /**
     * Defines the security protocol used to communicate with brokers
     * default is NOTSET
     * 
     * @return The security protocol.
     */
    BrokerProtocol protocol() default BrokerProtocol.NOTSET;

    /**
     * Path to client's private key (PEM) used for authentication.
     * Default ""
     * ssl.key.location in librdkafka
     * 
     * @return The ssl key location.
     */
    String sslKeyLocation() default "";

    /**
     * Path to CA certificate file for verifying the broker's certificate.
     * ssl.ca.location in librdkafka
     * 
     * @return The path to CA certificate file. 
     */
    String sslCaLocation() default "";

    /**
     * Path to client's certificate.
     * ssl.certificate.location in librdkafka
     * 
     * @return The ssl certificate location.
     */
    String sslCertificateLocation() default "";

    /**
     * Password for client's certificate.
     * ssl.key.password in librdkafka
     * 
     * @return The ssl key password.
     */
    String sslKeyPassword() default "";

    /**
     * Avro schema for generic record deserialization
     * default ""
     *
     * @return the avro schema
     */
    String avroSchema() default "";

    /**
     * Gets or sets the Avro schema of message key.
     * Should be used only if a generic record should be generated.
     * default ""
     *
     * @return the avro schema for message key
     */
    String keyAvroSchema() default "";

    /**
     * Specifies the data type of the message key that will be deserialized from the Kafka topic.
     * If KeyAvroSchema is set, this value is ignored and the key will be generated as a generic record.
     * The default type is String.
     * Default: String
     *
     * @return the data type of the message key
     */
    KafkaMessageKeyType keyDataType() default KafkaMessageKeyType.String;

    /**
     * Client certificate in PEM format.
     * ssl.certificate.pem in librdkafka
     * default ""
     *
     * @return the ssl certificate PEM
     */
    String sslCertificatePEM() default "";

    /**
     * Client Private Key in PEM format.
     * ssl.key.pem in librdkafka
     * default ""
     *
     * @return the ssl key PEM
     */
    String sslKeyPEM() default "";

    /**
     * CA certificate for verifying the broker's certificate in PEM format
     * ssl.ca.pem in librdkafka
     * default ""
     *
     * @return the ssl CA PEM
     */
    String sslCaPEM() default "";

    /**
     * Client certificate and key in PEM format.
     * Additional Configuration for extension as KeyVault supports uploading certificate only with private key.
     * default ""
     *
     * @return the ssl certificate and key PEM
     */
    String sslCertificateandKeyPEM() default "";

    /**
     * OAuth Bearer method.
     * Either 'default' or 'oidc'
     * sasl.oauthbearer in librdkafka
     * default ""
     *
     * @return the OAuth Bearer method
     */
    OAuthBearerMethod oAuthBearerMethod() default OAuthBearerMethod.Default;

    /**
     * OAuth Bearer Client Id
     * Specify only when OAuthBearerMethod is 'oidc'
     * sasl.oauthbearer.client.id in librdkafka
     * default ""
     *
     * @return the OAuth Bearer client id
     */
    String oAuthBearerClientId() default "";

    /**
     * OAuth Bearer Client Secret
     * Specify only when OAuthBearerMethod is 'oidc'
     * sasl.oauthbearer.client.secret in librdkafka
     * default ""
     *
     * @return the OAuth Bearer client secret
     */
    String oAuthBearerClientSecret() default "";

    /**
     * OAuth Bearer scope.
     * Client use this to specify the scope of the access request to the broker.
     * Specify only when OAuthBearerMethod is 'oidc'
     * sasl.oauthbearer.extensions in librdkafka
     * default ""
     *
     * @return the OAuth Bearer scope
     */
    String oAuthBearerScope() default "";

    /**
     * OAuth Bearer token endpoint url.
     * Specify only when OAuthBearerMethod is 'oidc'
     * sasl.oauthbearer.token.endpoint.url in librdkafka
     * default ""
     *
     * @return the OAuth Bearer token endpoint url
     */
    String oAuthBearerTokenEndpointUrl() default "";

    /**
     * OAuth Bearer extensions.
     * Allow additional information to be provided to the broker.
     * Comma-separated list of key=value pairs. E.g., "supportFeatureX=true,organizationId=sales-emea"
     * sasl.oauthbearer.extensions in librdkafka
     * default ""
     *
     * @return the OAuth Bearer extensions
     */
    String oAuthBearerExtensions() default "";

    /**
     * Maximum number of unprocessed messages a worker is expected to have at an instance.
     * When target-based scaling is not disabled, this is used to divide total unprocessed event count to determine the number of worker instances, which will then be rounded up to a worker instance count that creates a balanced partition distribution.
     * Default: 1000
     * 
     * @return the lag threshold
     */
    int lagThreshold() default 1000;

    /**
     * URL for the Avro Schema Registry
     * default ""
     *
     * @return the avro schema registry url
     */
    String schemaRegistryUrl() default "";

    /**
     * Username for the Avro Schema Registry
     * default ""
     *
     * @return the avro schema registry username
     */
    String schemaRegistryUsername() default "";

    /**
     * Password for the Avro Schema Registry
     * default ""
     *
     * @return the avro schema registry password
     */
    String schemaRegistryPassword() default "";

}
