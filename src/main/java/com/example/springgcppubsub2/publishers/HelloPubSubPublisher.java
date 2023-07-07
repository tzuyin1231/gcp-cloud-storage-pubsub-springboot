package com.example.springgcppubsub2.publishers;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelloPubSubPublisher extends PubSubPublisher {

    @Autowired
    public HelloPubSubPublisher(PubSubTemplate pubSubTemplate) {
//        調用父類別中具有相同類型參數的建構子
//        在HelloPubSubPublisher类中，通过调用super(pubSubTemplate)来初始化父类PubSubPublisher、完成父类的初始化工作，以便在子类中使用父类的功能和属性。
//        这样可以确保在HelloPubSubPublisher中具有PubSubPublisher类所提供的功能，并在子类中自定义特定的实现逻辑。
        super(pubSubTemplate);
    }

    @Override
    protected String topic() {
//       topic名
        return "hello-pubsub";
    }

}
