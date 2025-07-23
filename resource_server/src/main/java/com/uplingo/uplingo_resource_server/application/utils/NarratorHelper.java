package com.uplingo.uplingo_resource_server.application.utils;

import com.uplingo.uplingo_resource_server.model.enums.NarratorTypeEnum;

import java.util.Map;

import static java.util.Map.entry;

public class NarratorHelper {

  private static final Map<NarratorTypeEnum, String> NARRATOR_DEFINITIONS = Map.ofEntries(
      entry(NarratorTypeEnum.FIRST_PERSON,
          "The story is told from the perspective of a character within the narrative using 'I'. The reader experiences events, thoughts, and emotions directly through this character’s eyes. Limited to what the narrator knows, sees, and feels."),
      entry(NarratorTypeEnum.SECOND_PERSON,
          "The story is told using 'you', placing the reader directly into the role of the protagonist. This creates a highly immersive and personal experience, making the reader feel as if they are taking the actions described."),
      entry(NarratorTypeEnum.THIRD_PERSON,
          "The story is told by an external narrator using 'he', 'she', or 'they'. It can be limited to one character’s perspective or omniscient, revealing the thoughts and experiences of multiple characters across scenes."));

  public static String getDefinition(NarratorTypeEnum type) {
    return NARRATOR_DEFINITIONS.getOrDefault(type, "Unknown narrative point of view.");
  }
}
