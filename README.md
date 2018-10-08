# Microsoft Azure Functions API for Java
This project contains the Java API for building functions for the Azure Functions service. Visit the [complete documentation of Azure Functions - Java Developer Guide](https://docs.microsoft.com/en-us/azure/azure-functions/functions-reference-java) for more details.

## azure-functions-maven plugin
[How to use azure-functions-maven plugin to create, update, deploy and test azure java functions](https://docs.microsoft.com/en-us/java/api/overview/azure/maven/azure-functions-maven-plugin/readme?view=azure-java-stable)

## Prerequisites

* Java 8

## Summary

Azure Functions is capable of running Function Apps that may contain one or more functions grouped. A function should be a stateless method to process input and produce output. Although you are allowed to write instance methods, your function must not depend on any instance fields of the class. You need to make sure all the function methods are `public` accessible.

A deployable unit is an uber JAR containing one or more functions (see below), and a JSON file with the list of functions and triggers definitions, deployed to Azure Functions. The JAR can be created in many ways, although we recommend the use of the [Azure Functions Maven Plugin](https://docs.microsoft.com/en-us/java/api/overview/azure/maven/azure-functions-maven-plugin/readme), which also generates the JSON file for you automatically.

Typically a function is invoked because of a trigger. Your function needs to process that trigger (sometimes with additional inputs) and provide an optional output.

All the input and output bindings can be defined in `function.json` (not recommended), or in the Java method by using annotations (recommended). All the types and annotations used in this document are included in the `azure-functions-java-library` package.

Here is an example of a HttpTrigger Azure function in Java:


```Java
package com.example;

import com.microsoft.azure.functions.annotation.*;

public class MyClass {
    @FunctionName("echo")
    public static String echo(@HttpTrigger(name = "req", methods = { "post" }, authLevel = AuthorizationLevel.ANONYMOUS) String in) {
        return "Hello, " + in + ".";
    }
}
```

### Adding 3rd Party Libraries

Azure Functions supports the use of 3rd party libraries. If using the Maven plugin for Azure Functions, all of your dependencies specified in your `pom.xml` file will be automatically bundled during the `mvn package` step.

## Data Types

You are free to use all the data types in Java for the input and output data, including native types; customized POJO types and specialized Azure types defined in this API. Azure Functions runtime will try its best to convert the actual input value to the type you need (for example, a `String` input will be treated as a JSON string and be parsed to a POJO type defined in your code).

### JSON Support
The POJO types (Java classes) you may define have to be publicly accessible (`public` modifier). POJO properties/fields may be `private`. For example a JSON string `{ "x": 3 }` is able to be converted to the following POJO type:

```java
public class MyData {
    private int x;
}
```

### Other supported types
Binary data is represented as `byte[]` or `Byte[]` in your Azure functions code. And make sure you specify `dataType = "binary"` in the corresponding triggers/bindings.

Empty input values could be `null` as your functions argument, but a recommended way to deal with potential empty values is to use `Optional<T>` type.


## Inputs

Inputs are divided into two categories in Azure Functions: one is the trigger input and the other is the additional input. Trigger input is the input who triggers your function. And besides that, you may also want to get inputs from other sources (like a blob), that is the additional input.

Let's take the following code snippet as an example:

```java
package com.example;

import com.microsoft.azure.functions.annotation.*;

public class MyClass {
    @FunctionName("echo")
    public String echo(
        @HttpTrigger(name = "req", methods = { "put" }, authLevel = AuthorizationLevel.ANONYMOUS, route = "items/{id}") String in,
        @TableInput(name = "item", tableName = "items", partitionKey = "example", rowKey = "{id}", connection = "AzureWebJobsStorage") MyObject obj
    ) {
        return "Hello, " + in + " and " + obj.getRowKey() + ".";
    }

}

public class MyObject {
    public String getRowKey() { return this.rowKey; }
    private String rowKey;
}

```

When this function is invoked, the HTTP request payload will be passed as the `String` for argument `in`; and one entry will be retrieved from the Azure Table Storage and be passed to argument `obj` as `MyObject` type.

To receive events in a batch when using EventHubTrigger, set cardinality to many

```Java
@FunctionName("ProcessIotMessages")
    public void processIotMessages(
        @EventHubTrigger(name = "message", eventHubName = "%AzureWebJobsEventHubPath%", connection = "AzureWebJobsEventHubSender", cardinality = Cardinality.MANY) List<String> messages,
        final ExecutionContext context)
    {
        context.getLogger().info("Java Event Hub trigger received messages. Batch size: " + messages.size());
    }

```

## Outputs

Outputs can be expressed in return value or output parameters. If there is only one output, you are recommended to use the return value. For multiple outputs, you have to use **output parameters**.

Return value is the simplest form of output, you just return the value of any type, and Azure Functions runtime will try to marshal it back to the actual type (such as an HTTP response). You could apply any *output annotations* to the function method (the `name` property of the annotation has to be `$return`) to define the return value output.

For example, a blob content copying function could be defined as the following code. `@StorageAccount` annotation is used here to prevent the duplicating of the `connection` property for both `@BlobTrigger` and `@BlobOutput`.

```java
package com.example;

import com.microsoft.azure.functions.annotation.*;

public class MyClass {
    @FunctionName("copy")
    @StorageAccount("AzureWebJobsStorage")
    @BlobOutput(name = "$return", path = "samples-output-java/{name}")
    public String copy(@BlobTrigger(name = "blob", path = "samples-input-java/{name}") String content) {
        return content;
    }
}
``` 

To produce multiple output values, use `OutputBinding<T>` type defined in the `azure-functions-java-library` package. If you need to make an HTTP response and push a message to a queue, you can write something like:

```java
package com.example;

import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.*;

public class MyClass {
    @FunctionName("push")
    public String push(
        @HttpTrigger(name = "req", methods = { "post" }, authLevel = AuthorizationLevel.ANONYMOUS) String body,
        @QueueOutput(name = "message", queueName = "myqueue", connection = "AzureWebJobsStorage") OutputBinding<String> queue
    ) {
        queue.setValue("This is the queue message to be pushed");
        return "This is the HTTP response content";
    }
}
```

Use `OutputBinding<byte[]>` type to make a binary output value (for parameters); for return values, just use `byte[]`.

## Execution Context

You interact with Azure Functions execution environment via the `ExecutionContext` object defined in the `azure-functions-java-library` package. You are able to get the invocation ID, the function name and a built-in logger (which is integrated prefectly with Azure Function Portal experience as well as AppInsights) from the context object.

What you need to do is just add one more `ExecutionContext` typed parameter to your function method. Let's take a timer triggered function as an example:

```java
package com.example;

import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.*;

public class MyClass {
    @FunctionName("heartbeat")
    public static void heartbeat(
        @TimerTrigger(name = "schedule", schedule = "*/30 * * * * *") String timerInfo,
        ExecutionContext context
    ) {
        context.getLogger().info("Heartbeat triggered by " + context.getFunctionName());
    }
}
```


## Specialized Data Types

### HTTP Request and Response

Sometimes a function need to take a more detailed control of the input and output, and that's why we also provide some specialized types in the `azure-functions-java-library` package for you to manipulate:

| Specialized Type         |       Target        | Typical Usage                  |
| ------------------------ | :-----------------: | ------------------------------ |
| `HttpRequestMessage<T>`  |    HTTP Trigger     | Get method, headers or queries |
| `HttpResponseMessage<T>` | HTTP Output Binding | Return status other than 200   |

### Metadata

Metadata comes from different sources, like HTTP headers, HTTP queries, and [trigger metadata](https://docs.microsoft.com/en-us/azure/azure-functions/functions-triggers-bindings#trigger-metadata-properties). You can use `@BindingName` annotation together with the metadata name to get the value.

For example, the `queryValue` in the following code snippet will be `"test"` if the requested URL is `http://{example.host}/api/metadata?name=test`.

```Java
package com.example;

import java.util.Optional;
import com.microsoft.azure.functions.annotation.*;

public class MyClass {
    @FunctionName("metadata")
    public static String metadata(
        @HttpTrigger(name = "req", methods = { "get", "post" }, authLevel = AuthorizationLevel.ANONYMOUS) Optional<String> body,
        @BindingName("name") String queryValue
    ) {
        return body.orElse(queryValue);
    }
}
```

### License

This project is under the benevolent umbrella of the [.NET Foundation](http://www.dotnetfoundation.org/) and is licensed under [the MIT License](LICENSE.txt)

This project has adopted the [Microsoft Open Source Code of Conduct](https://opensource.microsoft.com/codeofconduct/). For more information see the [Code of Conduct FAQ](https://opensource.microsoft.com/codeofconduct/faq/) or contact [opencode@microsoft.com](mailto:opencode@microsoft.com) with any additional questions or comments.
