package SK.AASS.TELCO.app.camunda;

import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.topic.TopicSubscriptionBuilder;

import java.net.URI;
import java.util.logging.Logger;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.io.IOException;

public class TaskClient {
    private final static Logger LOGGER = Logger.getLogger(TaskClient.class.getName());

    public static void main(String[] args) throws IOException, InterruptedException {
        final String[] idOfOrder = new String[1];

        ExternalTaskClient client = ExternalTaskClient.create()
                .baseUrl("http://localhost:8000/engine-rest")
                .asyncResponseTimeout(10000) // long polling timeout
                .disableBackoffStrategy()
                .disableAutoFetching()
                .maxTasks(1)
                .build();

        TopicSubscriptionBuilder CreateOrder = (TopicSubscriptionBuilder) client.subscribe("CreateOrder")
                .lockDuration(1000)
                .handler((externalTask, externalTaskService) -> {
                    String body = "{\"userEmail\":\"test@test.com\",\"orderProducts\":[{\"product\":{\"productId\":5},\"quantity\":1},{\"product\":{\"productId\":6},\"quantity\":2}]}";

                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create("http://localhost:8080/telco/api/v1/order/create"))
                            .header("Content-Type", "application/json")
                            .POST(HttpRequest.BodyPublishers.ofString(body))
                            .build();
                    HttpResponse<String> response = null;
                    try {
                        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
                        idOfOrder[0] = response.body();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    LOGGER.info("CreateOrder complete!");

                    // Complete the task
                    externalTaskService.complete(externalTask);
                });

        TopicSubscriptionBuilder ConfirmOrder = (TopicSubscriptionBuilder) client.subscribe("ConfirmOrder")
                .lockDuration(1000)
                .handler((externalTask, externalTaskService) -> {

                    String requestBody = "";

                    HttpClient client2 = HttpClient.newHttpClient();
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create("http://localhost:8080/telco/api/v1/order/" + idOfOrder[0] + "/confirm"))
                            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                            .build();

                    HttpResponse<String> response = null;
                    try {
                        response = client2.send(request,
                                HttpResponse.BodyHandlers.ofString());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    LOGGER.info(response.body());

                    LOGGER.info("ConfirmOrder complete!");

                    externalTaskService.complete(externalTask);
                });

        TopicSubscriptionBuilder GenerateInvoice = (TopicSubscriptionBuilder) client.subscribe("GenerateInvoice")
                .lockDuration(1000)
                .handler((externalTask, externalTaskService) -> {

                    String requestBody = "";

                    HttpClient client2 = HttpClient.newHttpClient();
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create("http://localhost:8080/telco/api/v1/order/" + idOfOrder[0] + "/invoice/create"))
                            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                            .build();

                    HttpResponse<String> response = null;
                    try {
                        response = client2.send(request,
                                HttpResponse.BodyHandlers.ofString());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    LOGGER.info(response.body());

                    LOGGER.info("GenerateInvoice complete!");

                    externalTaskService.complete(externalTask);
                });

        TopicSubscriptionBuilder ChangeToPaid = (TopicSubscriptionBuilder) client.subscribe("ChangeToPaid")
                .lockDuration(1000)
                .handler((externalTask, externalTaskService) -> {

                    String requestBody = "";

                    HttpClient client2 = HttpClient.newHttpClient();
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create("http://localhost:8080/telco/api/v1/order/" + idOfOrder[0] + "/pay"))
                            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                            .build();

                    HttpResponse<String> response = null;
                    try {
                        response = client2.send(request,
                                HttpResponse.BodyHandlers.ofString());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    LOGGER.info(response.body());

                    LOGGER.info("ChangeToPaid complete!");

                    externalTaskService.complete(externalTask);
                });

        TopicSubscriptionBuilder UpdateWarehouse = (TopicSubscriptionBuilder) client.subscribe("UpdateWarehouse")
                .lockDuration(1000)
                .handler((externalTask, externalTaskService) -> {

                    String requestBody = "";

                    HttpClient client2 = HttpClient.newHttpClient();
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create("http://localhost:8080/telco/api/v1/order/" + idOfOrder[0] + "/warehouse/update"))
                            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                            .build();

                    HttpResponse<String> response = null;
                    try {
                        response = client2.send(request,
                                HttpResponse.BodyHandlers.ofString());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    LOGGER.info(response.body());

                    LOGGER.info("UpdateWarehouse complete!");

                    externalTaskService.complete(externalTask);
                });

        client.start();
        CreateOrder.open();
        ConfirmOrder.open();
        GenerateInvoice.open();
        ChangeToPaid.open();
        UpdateWarehouse.open();

    }
}