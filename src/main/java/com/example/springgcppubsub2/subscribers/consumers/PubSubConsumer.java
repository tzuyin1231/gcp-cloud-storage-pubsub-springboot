package com.example.springgcppubsub2.subscribers.consumers;

import com.google.cloud.spring.pubsub.support.BasicAcknowledgeablePubsubMessage;
import java.util.function.Consumer;

public abstract class PubSubConsumer {

    /**
     * Name of an existing Pub/Sub subscription.
     */
    public abstract String subscription();

    /**
     * The actual consumer logic.
     * 用於處理接收到的訊息
     * @param message Wrapper of a Pub/Sub message that adds acknowledging capability.
     * BasicAcknowledgeablePubsubMessage类型的参数，包装了Pub/Sub消息，并提供了消息确认的能力。具体的消费者实现类需要根据自己的需求来实现消息的处理逻辑。
     */
    protected abstract void consume(BasicAcknowledgeablePubsubMessage message);

    /**
     * Implementation of a {@link Consumer} functional interface which only calls the
     * {@link #consume(BasicAcknowledgeablePubsubMessage) consume} method.
     */
    public Consumer<BasicAcknowledgeablePubsubMessage> consumer() {
//        這個 basicAcknowledgeablePubsubMessage 不知道是從哪裡來的
        return basicAcknowledgeablePubsubMessage -> consume(basicAcknowledgeablePubsubMessage);
    }

}
