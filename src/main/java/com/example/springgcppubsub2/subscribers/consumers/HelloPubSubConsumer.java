package com.example.springgcppubsub2.subscribers.consumers;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.google.cloud.spring.pubsub.support.BasicAcknowledgeablePubsubMessage;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.pubsub.v1.PubsubMessage;
import java.io.IOException;
import java.io.OutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Component;

@Component
public class HelloPubSubConsumer extends PubSubConsumer {
    @Autowired
    private Storage storage;

    @Value("${object.gsutil.uri}")
    private String gsutilUri;

    @Value("${object.gsutil.uri}")
    private Resource gcsResource;

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloPubSubConsumer.class);

    @Override
    public String subscription() {
//        subscription名
        return "hello-pubsub-subscription";
    }

    @Override
    protected void consume(BasicAcknowledgeablePubsubMessage acknowledgeablePubsubMessage)
        throws IOException {

        BlobId id = BlobId.fromGsUtilUri(gsutilUri);

        // 讀取原本資料
        byte[] originalContentByte = storage.readAllBytes(id);
        String originalContentString = new String(originalContentByte, UTF_8);

        // extract wrapped message from pubsub
        PubsubMessage message = acknowledgeablePubsubMessage.getPubsubMessage();

        // process message
        LOGGER.info("message received: " + message.getData().toStringUtf8());

        // 把東西寫到cloud storage
        // https://github.com/GoogleCloudPlatform/spring-cloud-gcp/blob/main/spring-cloud-gcp-samples/spring-cloud-gcp-storage-resource-sample/src/main/java/com/example/WebController.java
        try (OutputStream os = ((WritableResource) gcsResource).getOutputStream()) {
            os.write((originalContentString +message.getData().toStringUtf8()).getBytes());
            LOGGER.info("file was updated");
        }
        
        // acknowledge that message was received
        // ack()是Pub/Sub消息传递中的一种操作，用于确认（acknowledge）消息已经被成功接收和处理。
        // acknowledgeablePubsubMessage.ack() 表示在consumer接收到消息后，通过调用ack()方法来手动确认该消息已经被成功处理。
        // 在Pub/Sub系统中，当消息被发送到订阅器后，订阅器会等待consumer对消息进行确认。通过调用ack()方法，consumer 告知Pub/Sub系统该消息已经被处理，并且可以从订阅器的队列中移除。
        // 确认消息的目的是确保消息不会被重复处理，同时也可以让Pub/Sub系统知道该消息已经被 consumer 接收，以便进行后续的消息传递和处理。
        // 需要注意的是，确认消息的方式和机制可能因具体的Pub/Sub系统和框架而有所不同。
        // 在您的代码中，ack()方法是一个示例，具体的实现可能取决于您使用的Pub/Sub框架或库。您可能需要根据您的实际使用情况和框架文档来了解确认消息的具体方式和方法。
        acknowledgeablePubsubMessage.ack();
    }

}
