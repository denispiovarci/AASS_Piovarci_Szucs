package SK.AASS.TELCO.app.camunda;

import org.camunda.bpm.client.ExternalTaskClient;

import java.util.logging.Logger;

public class ProductWorker {
    private final static Logger LOGGER = Logger.getLogger(ProductWorker.class.getName());

    public static void main(String[] args) {
        ExternalTaskClient client = ExternalTaskClient.create()
                .baseUrl("http://localhost:8000/engine-rest")
                .asyncResponseTimeout(10000) // long polling timeout
                .build();



        // subscribe to an external task topic as specified in the process
        client.subscribe("get_products")
                .lockDuration(1000) // the default lock duration is 20 seconds, but you can override this
                .handler((externalTask, externalTaskService) -> {
                    // Put your business logic here

                    // Get a process variab

                    LOGGER.info("Products are ready");

                    // Complete the task
                    externalTaskService.complete(externalTask);
                })
                .open();
    }
}