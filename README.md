# Azure SAS Generator

The SAS string generated in Azure Portal is not correctly formed to use in the Azure REST API call to Azure Service Bus, nor does it provide input for an expiry date.

This application creates the SAS token in the format as required by the REST APIs.

# Running the App

Replace `namespace` and `key` with the correct values from your Azure Portal and run the application. The SAS token is output to console.

```java
public static void main(String... args){
        String namespace = "AZURE_NAMESPACE_FROM_PORTAL";
        String resourceUri = "https://" + namespace + ".servicebus.windows.net/";
        String keyName = "RootManageSharedAccessKey";
        String key = "AZURE_KEY_FROM_PORTAL";
        String sas = getSasToken(resourceUri, keyName, key);
        System.out.println(sas);
}
```

