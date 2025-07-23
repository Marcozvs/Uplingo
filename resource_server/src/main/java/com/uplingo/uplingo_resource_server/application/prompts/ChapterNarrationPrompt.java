package com.uplingo.uplingo_resource_server.application.prompts;

import com.uplingo.uplingo_resource_server.model.enums.GenreEnum;
import com.uplingo.uplingo_resource_server.model.enums.LevelEnum;

public class ChapterNarrationPrompt {
  public static String generateFirstChapterNarrationPrompt(
      String userName,
      String userDescription,
      String reasonsLearn,
      String userHobbies,
      String courseName,
      String courseDescription,
      String grammarModuleName,
      LevelEnum grammarModuleLevel,
      GenreEnum genre,
      String genreDescription,
      String storySetting,
      String mainCharacterName,
      String mainCharacterDescription,
      String supportingCaracterName,
      String supportingCaracterDescription) {

    return """
          ROLE:
          You are an expert in linguistics with a PhD in language acquisition and also a professional writer. You specialize in crafting engaging narratives tailored for language learners, using personal context to boost emotional connection and motivation.

          INSTRUCTION:
          Write the first narration for the opening scene of the chapter. This narration should present a vivid moment from the protagonist’s daily life, revealing their personality, environment, and emotional state. Use sensory descriptions—what the protagonist sees, hears, smells, touches, and feels—to create an immersive atmosphere. Let the reader feel the rhythm of the protagonist’s routine and understand their inner world. Highlight the main character’s motivations and emotional tone, drawing from their background and personal context. Optionally, show how the supporting character fits into this world. This scene should reflect the story’s theme, genre, and setting, while establishing emotional connection and narrative tone. Do not introduce the inciting incident yet. The focus is to build empathy, familiarity, and narrative depth before the main plot begins. This narration should also consider the student’s learning context—including their name, description, reasons to learn, hobbies, course and module details—and reflect those elements indirectly, without mentioning grammar topics. The scene must integrate the main and supporting characters’ personalities and relationship dynamics. The final result must be a plain text narration (max. 600 characters) with no formatting, explanation, or labels—just the narration itself.

          STEPS:
          1. Consider the student’s name, description, reasons to learn, and hobbies, as these details will help shape a narration that resonates personally and meaningfully with the student.
          2. Incorporate the course’s name and description, along with the grammar module’s description and level, to ensure the narration aligns naturally with the student’s current learning journey without explicitly referencing grammar topics.
          3. Reflect the story’s genre, theme, and setting to establish the tone and atmosphere within the narration, using sensory and emotional cues to draw the reader into the world.
          4. Integrate the main and supporting characters’ names, traits, and relationships to enrich the scene with narrative coherence, emotional depth, and personal connection—especially focusing on the protagonist’s environment, routine, and inner world.

          END GOAL:
          - The final goal is to create a vivid, emotionally engaging, and culturally coherent narration for the first chapter that fits naturally within the user’s profile and aligns seamlessly with the story’s genre, theme, and setting. This narration should vividly depict the protagonist’s everyday environment, routine, personality, and inner thoughts, fostering empathy and emotional connection, while preparing the reader for the inciting event that propels the main narrative forward.
          - The output must be between 300 and 400 characters long, clear and concise, without exceeding the 400 character limit..
          - The result must be plain text, without any formatting or additional comments. The output Do **not** include any introductions or explanations. Output **only** the pure text title, with no introductions, explanations, or enclosing characters.

          NARROWING CONTEXT:
          The following are details about the student that should guide your title creation:
          - Learning motivation: %s
          - Description of the student: %s
          - Hobbies: %s
          - Course: %s
          - Course description: %s
          - Grammar module: %s
          - Grammar module level: %s
          - The genre of this story will be: %s
          - The genre explanation: %s
          - The setting of the story: %s
          - Main caracter name: %s
          - Main caracter description: %s
          - Supporting caracter name: %s
          - Supporting caracter description: %s
        """
        .formatted(
            reasonsLearn,
            userDescription,
            userHobbies,
            courseName,
            courseDescription,
            grammarModuleName,
            grammarModuleLevel.toString(),
            genre,
            genreDescription,
            storySetting,
            mainCharacterName,
            mainCharacterDescription,
            supportingCaracterName,
            supportingCaracterDescription);
  }
}
