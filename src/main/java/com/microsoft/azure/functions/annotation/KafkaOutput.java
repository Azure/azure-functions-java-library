/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.functions.annotation;

import com.microsoft.azure.functions.BrokerAuthenticationMode;
import com.microsoft.azure.functions.BrokerProtocol;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * Place this on a parameter whose value would be published to Kafka. The parameter type should be
 * OutputBinding&lt;T&gt;, where T could be one of:
 * </p>
 *
 * <ul>
 *     <li>Any native Java types such as int, String, byte[]</li>
 *     <li>Any POJO type</li>
 * </ul>
 *
 * <p>
 * The following example shows a Java function that produce a message to the Kafka cluster, using event
 * provided in the body of an HTTP Post request.
 * </p>
 *
 * <pre>
 * {@literal @}FunctionName("kafkaInupt-Java")
 *
 * public String input(
 *    {@literal @}HttpTrigger(name = "request", methods = {HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS)
 *     final String message,
 *    {@literal @}KafkaOutput(name = "event", topic = "users", brokerList="broker:29092") OutputBinding&lt;String&lt; output,
 *    final ExecutionContext context) {
 *     context.getLogger().info("Message:" + message);
 *     output.setValue(message);
 *     return "{ \"id\": \"" + System.currentTimeMillis() + "\", \"description\": \"" + message + "\" }";
 * }
 * </pre>
 * 
 * @since 1.4.0
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface KafkaOutput {

    /**
     * The variable name used in function.json.
     * 
     * @return The variable name used in function.json.
     */
    String name();

    /**
     * <p>Defines how Functions runtime should treat the parameter value. Possible values are:</p>
     * <ul>
     *     <li>"" or string: treat it as a string whose value is serialized from the parameter</li>
     *     <li>binary: treat it as a binary data whose value comes from for example OutputBinding&lt;byte[]&lt;</li>
     * </ul>
     * @return The dataType which will be used by the Functions runtime.
     */
    String dataType() default "";

    /**
     * Defines the Topic.
     * 
     * @return The topic name.
     */
    String topic();

    /**
     * Defines the BrokerList.
     * 
     * @return The brokerList name string.
     */
    String brokerList();

    /**
     * Defines the maximum transmit message size. Default: 1MB
     * 
     * @return The maximum trnasmit message size.
     */
    int maxMessageBytes() default 1000012; // Follow the kafka spec https://kafka.apache.org/documentation/

    /**
     * Defines the maximum number of messages batched in one MessageSet. default: 10000
     * 
     * @return The maximum number of messages batched in one MessageSet.
     */
    int batchSize() default 10000;

    /**
     * When set to `true`, the producer will ensure that messages are successfully produced exactly once and in the original produce order. default: false
     * 
     * @return whether idempotence is enabled.
     */
    boolean enableIdempotence() default false;

    /**
     * Local message timeout. This value is only enforced locally and limits the time a produced message waits for successful delivery. A time of 0 is infinite. This is the maximum time used to deliver a message (including retries). Delivery error occurs when either the retry count or the message timeout are exceeded. default: 300000
     * 
     * @return The local message timeout.
     */
    int messageTimeoutMs() default 300000;

    /**
     * The ack timeout of the producer request in milliseconds. default: 5000
     * 
     * @return The ack timeout of the producer request in milliseconds.
     */
    int requestTimeoutMs() default 5000;

    /**
     * How many times to retry sending a failing Message. **Note:** default: 2
     * Retrying may cause reordering unless EnableIdempotence is set to true.
     * @see #enableIdempotence()
     * 
     * @return The number of the max retries.
     */
    int maxRetries() default 2;

    /**
     * SASL mechanism to use for authentication.
     * Default: PLAIN
     * 
     * @return The SASL mechanism.
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
     * Default is plaintext
     *
     * security.protocol in librdkafka
     * 
     * @return The SASL password.
     */
    String password() default "";

    /**
     * Gets or sets the security protocol used to communicate with brokers
     * default is PLAINTEXT
     * 
     * @return The protocol.
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
     * @return The ssl ca location.
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
     * linger.MS property provides the time between batches of messages
     * being sent to cluster. Larger value allows more batching that 
     * results in high throughput.
     * 
     * @return value of linger.ms property.
     */
    int lingerMs() default 5;

    /**
     * Avro schema for generic record serialization
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
     * Specifies the data type of the message key.
     * This data type will be used to serialize the key before sending it to the Kafka topic.
     * If KeyAvroSchema is set, this value is ignored and the key will be serialized using Avro.
     * The default type is System.String.
     * default ""
     *
     * @return the data type of the message key
     */
    String keyDataType() default "";

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

    /**
     * OAuth Bearer method.
     * Either 'default' or 'oidc'
     * sasl.oauthbearer in librdkafka
     * default ""
     *
     * @return the OAuth Bearer method
     */
    String oAuthBearerMethod() default "";

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

}
