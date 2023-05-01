package ru.tinkoff.edu.java.bot.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

	@Bean
	public MessageConverter jsonMessageConverter() {

		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public Queue scrapperQueue(ApplicationConfig config) {

		return QueueBuilder.durable(config.queueName())
				.withArgument("x-dead-letter-exchange", config.queueName().concat(".dlx"))
				.build();
	}

	@Bean
	public DirectExchange scrapperExchange(ApplicationConfig config) {

		return new DirectExchange(config.exchangeName());
	}

	@Bean
	public Binding bindingScrapper(Queue scrapperQueue, DirectExchange scrapperExchange) {

		return BindingBuilder.bind(scrapperQueue).to(scrapperExchange).withQueueName();
	}

	@Bean
	public Queue deadLetterQueue(ApplicationConfig config) {

		return QueueBuilder.durable(config.queueName().concat(".dlq")).build();
	}

	@Bean
	public FanoutExchange deadLetterExchange(ApplicationConfig config) {

		return new FanoutExchange(config.queueName().concat(".dlx"));
	}

	@Bean
	public Binding deadLetterBinding(Queue deadLetterQueue, FanoutExchange fanoutExchange) {

		return BindingBuilder.bind(deadLetterQueue).to(fanoutExchange);
	}
}
