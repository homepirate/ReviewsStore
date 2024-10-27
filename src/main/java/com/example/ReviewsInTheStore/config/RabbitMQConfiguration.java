package com.example.ReviewsInTheStore.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.*;

@Configuration
public class RabbitMQConfiguration {

    static final String feedbackQueue = "feedbackQueue";
    static final String assignmentQueue = "assignmentQueue";
    public static final String exchangeName = "testExchange";

    @Bean
    public Queue feedbackQueue() {
        return new Queue(feedbackQueue, false);
    }

    @Bean
    public Queue assignmentQueue() {
        return new Queue(assignmentQueue, false);
    }

    @Bean
    Exchange exchange() {
        return new TopicExchange(exchangeName, false, false);
    }

    @Bean
    Binding feedbackBinding(Queue feedbackQueue, Exchange exchange) {
        return BindingBuilder.bind(feedbackQueue).to(exchange).with("feedback.#").noargs();
    }

    @Bean
    Binding assignmentBinding(Queue assignmentQueue, Exchange exchange) {
        return BindingBuilder.bind(assignmentQueue).to(exchange).with("assignment.#").noargs();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }
}

