package com.uplingo.uplingo_resource_server.application.prompts;

import com.uplingo.uplingo_resource_server.model.enums.GenreEnum;
import com.uplingo.uplingo_resource_server.model.enums.LevelEnum;

public class ChapterPrompt {

  public static String generateFirstChapterTitlePrompt(
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
          Create a captivating title for the first chapter that primarily reflects the student’s personal profile — including their motivations, description, and hobbies — while also aligning with the story’s theme, genre, and setting. The title should resonate emotionally, fit the overall tone, and set the narrative’s stage.

          STEPS:
          1. Consider the student’s name, description, reasons to learn, and hobbies, as this will help create a chapter title that resonates personally and meaningfully.
          2. Consider the course’s name and description, as well as the grammar module’s description and level, to ensure the chapter title aligns naturally with the student’s current learning journey without explicit grammar references.
          3. Consider the story’s genre, theme, and setting to craft a chapter title that fits the narrative tone and atmosphere.
          4. Use the main and supporting characters’ names and traits to add depth and coherence to the chapter title, reflecting the narrative’s emotional and cultural context.

          END GOAL:
          - The final goal is to create a vivid, emotionally engaging, and coherent first chapter title that fits naturally within the user’s profile and learning context, with greatest emphasis on the student’s individual information and motivations alongside the story’s genre, theme, and setting.
          - The output must be between 50 and 100 characters long, clear and concise, without exceeding the 100-character limit.
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

  public static String generateFirstChapterIntroductionPrompt(
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
          Write a captivating introduction for the first chapter that primarily reflects the student’s personal profile — including their motivations, description, and hobbies — while aligning with the story’s theme, genre, and setting. Introduce the protagonist’s normal world, motivations, and relationships, then describe the inciting event that begins the main journey.

          STEPS:
          1. Consider the student’s name, description, reasons to learn, and hobbies, as these details will help craft an introduction that resonates personally and meaningfully with the student.
          2. Incorporate the course’s name and description, along with the grammar module’s description and level, to ensure the introduction aligns naturally with the student’s current learning journey without explicitly mentioning grammar topics.
          3. Reflect the story’s genre, theme, and setting to establish the narrative tone and atmosphere in the introduction.
          4. Integrate the main and supporting characters’ names and traits to add depth and coherence, highlighting their personalities, motivations, and relationships to emotionally engage the reader and establish the story’s foundation.

          END GOAL:
          - The final goal is to create a vivid, emotionally engaging, and coherent introduction for the first chapter that fits naturally within the user’s profile and learning context, emphasizing the student’s individual information and motivations alongside the story’s genre, theme, and setting.
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
