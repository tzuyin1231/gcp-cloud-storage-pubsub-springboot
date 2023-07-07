package com.example.springgcppubsub2.subscribers.consumers;

import com.google.cloud.spring.pubsub.support.BasicAcknowledgeablePubsubMessage;
import com.google.pubsub.v1.PubsubMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class HelloPubSubConsumer extends PubSubConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloPubSubConsumer.class);

    @Override
    public String subscription() {
        return "hello-pubsub-subscription";
    }

    @Override
    protected void consume(BasicAcknowledgeablePubsubMessage acknowledgeablePubsubMessage) {
        // extract wrapped message
        PubsubMessage message = acknowledgeablePubsubMessage.getPubsubMessage();

        // process message
        LOGGER.info("message received: " + message.getData().toStringUtf8());

        // acknowledge that message was received
        // ack()是Pub/Sub消息传递中的一种操作，用于确认（acknowledge）消息已经被成功接收和处理。
        // 在代码中的这行注释和代码 acknowledgeablePubsubMessage.ack();
        // 表示在消费者接收到消息后，通过调用ack()方法来手动确认该消息已经被成功处理。
        // 在Pub/Sub系统中，当消息被发送到订阅器后，订阅器会等待消费者对消息进行确认。通过调用ack()方法，消费者告知Pub/Sub系统该消息已经被处理，并且可以从订阅器的队列中移除。
        // 确认消息的目的是确保消息不会被重复处理，同时也可以让Pub/Sub系统知道该消息已经被消费者接收，以便进行后续的消息传递和处理。
        // 需要注意的是，确认消息的方式和机制可能因具体的Pub/Sub系统和框架而有所不同。在您的代码中，ack()方法是一个示例，具体的实现可能取决于您使用的Pub/Sub框架或库。您可能需要根据您的实际使用情况和框架文档来了解确认消息的具体方式和方法。
        acknowledgeablePubsubMessage.ack();
    }

}
