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
}
