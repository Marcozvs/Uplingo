package com.uplingo.uplingo_resource_server.application.prompts;

import com.uplingo.uplingo_resource_server.model.enums.GenreEnum;
import com.uplingo.uplingo_resource_server.model.enums.LevelEnum;

public class StoryCharacterPrompt {
  public static String generateStoryMainCharacterNamePrompt(
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
      String storyTheme,
      String storySetting) {

    return """
          ROLE:
          You are an expert in linguistics with a PhD in language acquisition and also a professional writer. You specialize in crafting engaging narratives tailored for language learners, using personal context to boost emotional connection and motivation.

          INSTRUCTION:
          Your main task now is to define the name of the story’s main character, who represents the user. This name should be meaningful, emotionally engaging, and closely aligned with the student’s personal profile — including their motivations, description, and hobbies — while also fitting naturally within the story’s genre, theme, and setting. Consider cultural, linguistic, and narrative coherence to ensure the name feels authentic and relevant.

          STEPS:
          1. Prioritize the student’s profile and personal context to choose a name that resonates emotionally and personally.
          2. Consider the story's genre, theme, and setting to ensure the name fits the narrative world and tone.

          END GOAL:
          - The final goal is to create a main character name that is vivid, meaningful, and strongly connected to the user’s identity and learning context.
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
          - The theme of the story: %s
          - The setting of the story: %s
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
            storyTheme,
            storySetting);
  }

  public static String generateStoryMainCharacterDescriptionPrompt(
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
      String storyTheme,
      String storySetting,
      String mainCharacterName) {

    return """
          ROLE:
          You are an expert in linguistics with a PhD in language acquisition and also a professional writer. You specialize in crafting engaging narratives tailored for language learners, using personal context to boost emotional connection and motivation.

          INSTRUCTION:
          Your main task now is to write a compelling and emotionally impactful description of the story’s main character. This description should primarily reflect the student’s personal profile — including their motivations, description, and hobbies — while also aligning with the story’s genre, theme, and setting. The goal is to create a character that feels real, interesting, and closely connected to the learner’s context.

          STEPS:
          1. Consider the student's name, description, reasons to learn, and hobbies, as this will help create a character with depth and personal relevance.
          2. Consider the course's name and description, as well as the grammar module's description and level, to ensure the character’s motivations and traits feel connected to the student’s learning context, without explicitly referencing grammar.
          3. Consider the story's genre, theme, and setting to shape the character’s personality, role, and background in a way that fits the narrative atmosphere and expectations.
          4. Use the main character’s name to give the description personality and coherence, ensuring it matches the tone and cultural context of the narrative.

          END GOAL:
          - The final goal is to create a main character description that is vivid, emotionally engaging, and closely aligned with the user’s personal profile and learning context, as well as the story’s genre, theme, and setting.
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
          - The theme of the story: %s
          - The setting of the story: %s
          - Main caracter name: %s
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
            storyTheme,
            storySetting,
            mainCharacterName);
  }

  public static String generateStorySupportingCharacterNamePrompt(
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
      String storyTheme,
      String storySetting,
      String mainCharacterName,
      String mainCharacterDescription) {

    return """
          ROLE:
          You are an expert in linguistics with a PhD in language acquisition and also a professional writer. You specialize in crafting engaging narratives tailored for language learners, using personal context to boost emotional connection and motivation.

          INSTRUCTION:
          Create a captivating title for the first chapter that primarily reflects the student’s personal profile — including their motivations, description, and hobbies — as they are embodied in the main character. The title should also consider the story’s genre, theme, and setting. Additionally, ensure that the supporting character plays a meaningful role in relation to the protagonist’s journey. For example, if the student aims to become a pilot, the supporting character could be a flight instructor. The title should resonate emotionally, fit the story’s tone, and set the stage for the narrative.

          STEPS:
          1. Consider the story's genre, theme, and setting to select a name that matches the narrative tone and conventions expected in that story.

          END GOAL:
          - The final goal is to create a vivid, emotionally engaging, and coherent first chapter title that fits naturally within the user’s profile and learning context, with strong emphasis on the student’s individual information and motivations, while also integrating the narrative relationship between the main and supporting characters.
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
          - The theme of the story: %s
          - The setting of the story: %s
          - Main caracter name: %s
          - Main caracter description: %s
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
            storyTheme,
            storySetting,
            mainCharacterName,
            mainCharacterDescription);
  }

  public static String generateStorySupportingCharacterDescriptionPrompt(
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
      String storyTheme,
      String storySetting,
      String mainCharacterName,
      String mainCharacterDescription,
      String supportingCaracterName) {

    return """
          ROLE:
          You are an expert in linguistics with a PhD in language acquisition and also a professional writer. You specialize in crafting engaging narratives tailored for language learners, using personal context to boost emotional connection and motivation.

          INSTRUCTION:
          Your main task now is to write a compelling and emotionally rich description of the story’s supporting character. This character should not represent the student, but must have a meaningful connection to the main character, who is based on the student’s profile. Describe their personality, motivations, and background, and how they contribute to the main character’s journey. The description should align with the story’s genre, theme, and setting, and reflect a dynamic that complements the user’s motivations — for example, if the user’s goal is to become a pilot, the supporting character could be a flight instructor.

          STEPS:
          1. The final goal is to create a vivid, emotionally engaging, and coherent description of the supporting character that connects meaningfully with the main character (who represents the user), and fits naturally within the user’s personal context and the story’s genre, theme, and setting.
          2. Consider the course's name and description, as well as the grammar module's description and level, to ensure the supporting character’s motivations and traits feel connected to the student’s learning context, without explicitly referencing grammar.
          3. Consider the story's genre, theme, and setting to shape the supporting character’s personality, role, and background in a way that fits the narrative atmosphere and expectations.
          4. Use the main character’s name to give the supporting character’s description personality and coherence, ensuring it matches the tone and cultural context of the narrative.

          END GOAL:
          - The final goal is to create a vivid, emotionally engaging, and culturally coherent description of the supporting character that fits naturally within the user’s profile and the story’s genre, theme, and setting.
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
          - The theme of the story: %s
          - The setting of the story: %s
          - Main caracter name: %s
          - Main caracter description: %s
          - Supporting caracter name: %s
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
            storyTheme,
            storySetting,
            mainCharacterName,
            mainCharacterDescription,
            supportingCaracterName);
  }
}
