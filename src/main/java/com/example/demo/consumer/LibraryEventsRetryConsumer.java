package com.example.demo.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.example.demo.service.LibraryEventsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LibraryEventsRetryConsumer
{

    @Autowired
    private LibraryEventsService libraryEventsService;

    @KafkaListener(topics = {"${topics.retry}"},groupId = "retry-listener-group")
    public void onMessage(ConsumerRecord<Integer,String> consumerRecord) throws JsonProcessingException {

        log.info("ConsumerRecord in Retry Consumer : {} ", consumerRecord );
        consumerRecord.headers()
                        .forEach(header -> {
                            log.info("Key : {} , value : {}", header.key(), new String(header.value()));
                        });
        libraryEventsService.processLibraryEvent(consumerRecord);

    }
}
