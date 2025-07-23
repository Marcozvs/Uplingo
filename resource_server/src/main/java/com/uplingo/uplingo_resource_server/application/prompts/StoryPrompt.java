package com.uplingo.uplingo_resource_server.application.prompts;

import com.uplingo.uplingo_resource_server.model.enums.GenreEnum;
import com.uplingo.uplingo_resource_server.model.enums.LevelEnum;

public class StoryPrompt {

  public static String generateStoryThemePrompt(
      String userName,
      String userDescription,
      String reasonsLearn,
      String userHobbies,
      String courseName,
      String courseDescription,
      String grammarModuleName,
      LevelEnum grammarModuleLevel,
      GenreEnum genre,
      String genreDescription) {

    return """
          ROLE:
          You are an expert in linguistics with a PhD in language acquisition and also a professional writer. You specialize in crafting engaging narratives tailored for language learners, using personal context to boost emotional connection and motivation.

          INSTRUCTION:
          Your main task now is to define the core theme of the story. This theme should primarily reflect the emotional or conceptual journey based on the student’s personal profile — including their motivations, description, and hobbies — while also aligning with the story’s genre and learning context. The theme will guide the tone, conflict, and message of the entire narrative.

          STEPS:
          1. Consider the student's name, description, reasons to learn, and hobbies, as this will help define a theme that resonates with the student’s personal context and emotional motivations.
          2. Consider the course's name and description, as well as the grammar module's description and level, to ensure the theme aligns naturally with the student’s current learning journey, without explicitly referencing grammar.
          3. Consider the story's genre, as this will help define a theme that fits the conventions and tone expected in that genre.
          
          END GOAL:
          - The final goal is to create a story theme that is meaningful, engaging, and fitting to the user's personal profile and learning context, with the greatest emphasis on the student's individual information and motivations.
          - The output must be between 150 and 300 characters long, clear and concise, without exceeding the 300-character limit.
          - The result must be plain text, without any formatting or additional comments. The output Do **not** include any introductions or explanations. Output **only** the pure text title, with no introductions, explanations, or enclosing characters.

          OBSERVATIONS:
          - Do not explicitly mention what the user is studying or their level. For example, do not write themes like "The theme is Modal Verbs" or mention the level "A1".

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
            genreDescription);
  }

  public static String generateStorySettingPrompt(
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
      String storyTheme) {

    return """
          ROLE:
          You are an expert in linguistics with a PhD in language acquisition and also a professional writer. You specialize in crafting engaging narratives tailored for language learners, using personal context to boost emotional connection and motivation.

          INSTRUCTION:
          Your main task now is to define the setting of the story. This setting should describe where and when the story takes place, including important environmental, cultural, or contextual details. The setting must primarily reflect the student’s personal profile — including their motivations, description, and hobbies — while also aligning with the story’s genre, theme, and learning context. The setting will shape the atmosphere and background of the entire narrative.

          STEPS:
          1. Consider the student's name, description, reasons to learn, and hobbies, as this will help create a setting that resonates with the student’s personal context and emotional connection.
          2. Consider the course's name and description, as well as the grammar module's description and level, to ensure the setting aligns naturally with the student’s current learning journey, without explicitly referencing grammar.
          3. Consider the story's genre and theme, as this will help define a setting that fits the conventions, tone, and core ideas expected in the story.
          
          END GOAL:
          - The final goal is to create a vivid and meaningful story setting that is fitting to the user's profile and learning context, with special emphasis on the student’s personal information and emotional connection.
          - The output must be between 150 and 300 characters long, clear and concise, without exceeding the 300-character limit.
          - The result must be plain text, without any formatting or additional comments. The output Do **not** include any introductions or explanations. Output **only** the pure text title, with no introductions, explanations, or enclosing characters.

          OBSERVATIONS:
          - Do not explicitly mention what the user is studying or their level. For example, do not write settings like "The setting is Modal Verbs" or mention the level "A1".

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
            storyTheme);
  }

  public static String generateStoryTitlePrompt(
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
      String supportingCaracterName,
      String supportingCaracterDescription) {

    return """
          ROLE:
          You are an expert in linguistics with a PhD in language acquisition and also a professional writer. You specialize in crafting engaging narratives tailored for language learners, using personal context to boost emotional connection and motivation.

          INSTRUCTION:
          Your main task now is to create a compelling and meaningful title for the story. This title should primarily capture the essence of the narrative based on the student’s personal profile — including their motivations, description, and hobbies — while also reflecting the learning context and key story elements such as genre, theme, setting, and characters. The title must be engaging, relevant, and emotionally resonant, helping to motivate the learner and spark curiosity about the story.

          STEPS:
          1. Consider the student's name, description, reasons to learn, and hobbies, as this will help create a title that personally connects with the student.
          2. Consider the name and description of the course, as well as the description and level of the grammar module, to ensure the title naturally aligns with the student’s current learning journey, without explicitly mentioning grammar concepts or levels.
          3. Consider the story's genre, theme, and setting to craft a title that fits the tone and conventions of the narrative.
          4. Consider the main character’s name and description to add narrative depth and emotional resonance to the title.
          5. Consider the supporting character’s name and description to further enrich the meaning and coherence of the title within the story.

          END GOAL:
          - The final goal is to create a compelling, meaningful, and emotionally engaging story title that reflects the user’s personal profile and learning context, with greatest emphasis on the student's individual information and motivations, as well as the story’s genre, theme, setting, and characters.
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
            storyTheme,
            storySetting,
            mainCharacterName,
            mainCharacterDescription,
            supportingCaracterName,
            supportingCaracterDescription);
  }

  public static String generateStoryDescriptionPrompt(
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
      String supportingCaracterName,
      String supportingCaracterDescription,
      String storyTitle) {

    return """
          ROLE:
          You are an expert in linguistics with a PhD in language acquisition and also a professional writer. You specialize in crafting engaging narratives tailored for language learners, using personal context to boost emotional connection and motivation.

          INSTRUCTION:
          Your main task now is to create a compelling and emotionally engaging backover for the story. This backover should primarily reflect the student’s personal profile — including their motivations, description, and hobbies — while also aligning with the learning context and key story elements such as genre, theme, setting, and characters. It should spark curiosity, build emotional connection, and motivate the learner to follow the story.

          STEPS:
          1. Consider the student's name, description, reasons to learn, and hobbies, as this will help create a backover that emotionally resonates with the student’s personal context and motivations.
          2. Consider the name and description of the course, as well as the grammar module’s description and level, to ensure the backover naturally aligns with the student’s current learning journey, without explicitly mentioning grammar topics or levels.
          3. Consider the story's genre, theme, and setting to shape a backover that reflects the tone, narrative style, and core message expected from that genre.
          4. Consider the names and descriptions of both the main and supporting characters to enrich the narrative hook, deepen emotional engagement, and ensure coherence with the plot and character dynamics.

         END GOAL:
          - The final goal is to create a compelling, emotionally resonant, and thematically coherent backover that summarizes the story in a way that sparks curiosity and reflects the student’s personal profile and learning context, with special emphasis on the student’s individual information and motivations, alongside the story’s genre, theme, setting, and characters.
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
          - Supporting caracter description: %s
          - Story title: %s
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
            supportingCaracterName,
            supportingCaracterDescription,
            storyTitle);
  }
}
