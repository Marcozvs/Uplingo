package com.uplingo.uplingo_resource_server.infrastructure.configurations.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class CognitiveConfiguration {

  @Bean
  public ChatClient chatClient(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory) {
    return chatClientBuilder
        .defaultAdvisors(
            MessageChatMemoryAdvisor.builder(chatMemory).build())
        .build();
  }

  @Bean
  public JdbcChatMemoryRepository jdbcChatMemory(JdbcTemplate jdbcTemplate) {
    return JdbcChatMemoryRepository.builder()
        .jdbcTemplate(jdbcTemplate)
        .build();
  }

}
