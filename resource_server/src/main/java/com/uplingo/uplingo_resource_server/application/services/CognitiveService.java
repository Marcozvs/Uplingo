package com.uplingo.uplingo_resource_server.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CognitiveService {

  private final ChatClient chatClient;

  public String sendPrompt(String chapterId, String message, Integer maxTokens) {
    return chatClient.prompt()
        .user(message)
        .advisors(a -> {
          a.param("chat_memory_conversation_id", chapterId);
          a.param("chat_memory_retrieve_size", 100);
          a.param("max_tokens", maxTokens);
        })
        .call()
        .content();
  }

  public String generateText(String prompt, int maxTokens) {
    return chatClient.prompt()
        .user(prompt)
        .advisors(a -> a.param("max_tokens", maxTokens))
        .call()
        .content();
  }
}
