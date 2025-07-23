package com.uplingo.uplingo_resource_server.application.prompts;

import com.uplingo.uplingo_resource_server.model.enums.GenreEnum;
import com.uplingo.uplingo_resource_server.model.enums.LevelEnum;

public class ChapterAnswerPrompt {
  public static String generateFirstSupportingCharacterSpeechPrompt(
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
          Write the first line of dialogue from the supporting character that serves as a conversation starter or opener. This should be a natural, specific observation about something the protagonist is doing, wearing, or their mood/behavior. Avoid generic greetings like "Hello" or overly obvious statements. Instead, create an engaging hook that shows the supporting character's personality and their relationship dynamic with the protagonist. The line should feel spontaneous and authentic, like real people talking, with a touch of curiosity, playfulness, or genuine interest that invites response.

          STEPS:
          1. Consider the student's name, description, reasons to learn, and hobbies, as these details will help craft a supporting character's line that resonates personally and meaningfully with the student's emotional and linguistic context.
          2. Incorporate the course's name and description, as well as the grammar module's description and level, to ensure the supporting character's tone, vocabulary, and phrasing feel natural and aligned with the student's current learning stage—without directly referencing grammar topics.
          3. Reflect the story's genre, theme, and setting to ensure the line matches the narrative tone and atmosphere, using language that feels specific to the context and characters.
          4. Use the supporting character's name, personality traits, and relationship with the protagonist to create a distinctive line that reveals character and relationship dynamics. Focus on making an observation that's specific, interesting, and creates natural dialogue flow.

          END GOAL:
          - Create an engaging opening line that establishes character voice, relationship dynamics, and story context through natural dialogue. The line should feel like a real conversation starter that draws the reader in and makes them want to hear the protagonist's response. Avoid clichés and generic statements - make it specific, personal, and contextually relevant to the story and characters.
          - The output must have a maximum of 150 characters.
          - The result must be plain text, without any formatting or additional comments. The output Do **not** include any introductions or explanations. Output **only** the pure text title, with no introductions, explanations, or enclosing characters.

          OBSERVATIONS:
          - Build the speech aiming to include language related to the course and module the student is studying. For example, if the module is about Modal Verbs, try to include words like "can", "must", "should".
            Examples:
            1. "You must be planning something - that smile gives it away."
            2. "Can you believe how quiet it is here today?"
            3. "You should see the way that guy keeps staring at you."

          - The output must be only the character's speech text, avoiding phrases like "Then John said: Hello Samantha". The output should be only the pure dialogue.
            Examples:
            1. "That book looks like it's been through a war."
            2. "You're humming that song again - third time today."
            3. "Someone's in an unusually good mood."

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

  public static String generateFirstMainCharacterSpeechPrompt(
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
      String supportingCaracterDescription,
      String firstSupportingCharacterSpeech) {

    return """
          ROLE:
          You are an expert in linguistics with a PhD in language acquisition and also a professional writer. You specialize in crafting engaging narratives tailored for language learners, using personal context to boost emotional connection and motivation.

          INSTRUCTION:
          Write the protagonist's response to the supporting character's opening line. This response should acknowledge what the supporting character said while revealing something about the protagonist's current state of mind, situation, or personality. The response should feel natural and conversational, but also hint at depth or complexity beneath the surface. Avoid simple yes/no answers or overly explanatory responses. Instead, create a reply that moves the conversation forward and gives the supporting character something interesting to respond to.

          STEPS:
          1. Consider the student's name, description, reasons to learn, and hobbies, as these details will help shape a protagonist's voice that resonates emotionally and linguistically with the student.
          2. Incorporate the course's name and description, as well as the grammar module's description and level, to ensure the tone and vocabulary of the protagonist's reply feel natural and level-appropriate—without referencing grammar topics.
          3. Reflect the story's genre, theme, and setting to match the mood, tone, and emotional undercurrent of the moment, using language that feels authentic to the character and situation.
          4. Use the protagonist's name, traits, and relationship with the supporting character to create a response that reveals character while maintaining conversational flow. The response should acknowledge the supporting character's comment while adding new information or perspective.

          END GOAL:
          - Create a protagonist response that feels authentic, reveals character depth, and advances the conversation naturally. The line should respond directly to what the supporting character said while opening up new conversational possibilities. Focus on making the protagonist feel like a real person with their own voice, thoughts, and way of expressing themselves.
          - The output must have a maximum of 150 characters.
          - The result must be plain text, without any formatting or additional comments. The output Do **not** include any introductions or explanations. Output **only** the pure text title, with no introductions, explanations, or enclosing characters.

          OBSERVATIONS:
          - Build the speech aiming to include language related to the course and module the student is studying. For example, if the module is about Modal Verbs, try to include words like "can", "must", "should".
            Examples:
            1. "I must look obvious then - can't hide anything from you."
            2. "You should know by now I'm always planning something."
            3. "Can you blame me? It's been such a long week."

          - The output must be only the character's speech text, avoiding phrases like "Then John said: Hello Samantha". The output should be only the pure dialogue.
            Examples:
            1. "It has character - like its owner."
            2. "Guilty as charged - it's stuck in my head."
            3. "Is it that obvious? I thought I was being subtle."

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
          - First supporting caracter speech: %s
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
            supportingCaracterDescription,
            firstSupportingCharacterSpeech);
  }

  public static String generateSecondSupportingCharacterSpeechPrompt(
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
      String supportingCaracterDescription,
      String firstSupportingCharacterSpeech,
      String firstMainCharacterSpeech) {

    return """
          ROLE:
          You are an expert in linguistics with a PhD in language acquisition and also a professional writer. You specialize in crafting engaging narratives tailored for language learners, using personal context to boost emotional connection and motivation.

          INSTRUCTION:
          Write the supporting character's follow-up response that builds on the conversation established in the first exchange. This line should either dig deeper into what the protagonist revealed, make a related observation, or shift the conversation in a natural direction. The response should show the supporting character's personality and their knowledge of the protagonist. Avoid repetitive patterns or simply restating what was already said. Instead, create a line that adds new information, shows curiosity, or reveals the dynamic between the characters.

          STEPS:
          1. Consider the student's name, description, reasons to learn, and hobbies, as these will help shape a line that resonates with the learner's emotional and linguistic context.
          2. Incorporate the course's name and description, as well as the grammar module's description and level, to ensure the tone and vocabulary of the supporting character feel appropriate to the student's stage—without referencing grammar explicitly.
          3. Reflect the story's genre, theme, and setting to preserve the narrative tone and emotional atmosphere, while maintaining natural dialogue flow.
          4. Use the supporting character's name, personality, and relationship with the protagonist to create a line that responds meaningfully to what the protagonist said, showing understanding or curiosity about their friend's situation.

          END GOAL:
          - Create a supporting character response that deepens the conversation and reveals more about both characters' relationship and personalities. The line should feel like a natural continuation of the dialogue, building on what was established rather than repeating themes. Focus on creating authentic character interaction that moves the conversation forward.
          - The output must have a maximum of 150 characters.
          - The result must be plain text, without any formatting or additional comments. The output Do **not** include any introductions or explanations. Output **only** the pure text title, with no introductions, explanations, or enclosing characters.

          OBSERVATIONS:
          - Build the speech aiming to include language related to the course and module the student is studying. For example, if the module is about Modal Verbs, try to include words like "can", "must", "should".
            Examples:
            1. "You must tell me what's really going on then."
            2. "Should I be worried about these mysterious plans?"
            3. "Can't say I'm surprised - you always were unpredictable."

          - The output must be only the character's speech text, avoiding phrases like "Then John said: Hello Samantha". The output should be only the pure dialogue.
            Examples:
            1. "Well, now I'm intrigued - spill the details."
            2. "That explains the mysterious text messages."
            3. "You're being unusually cryptic today."

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
          - First supporting caracter speech: %s
          - First main caracter speech: %s
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
            supportingCaracterDescription,
            firstSupportingCharacterSpeech,
            firstMainCharacterSpeech);
  }

  public static String generateSecondMainCharacterSpeechPrompt(
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
      String supportingCaracterDescription,
      String firstSupportingCharacterSpeech,
      String firstMainCharacterSpeech,
      String secondSupportingCharacterSpeech) {

    return """
          ROLE:
          You are an expert in linguistics with a PhD in language acquisition and also a professional writer. You specialize in crafting engaging narratives tailored for language learners, using personal context to boost emotional connection and motivation.

          INSTRUCTION:
          Write the protagonist's second response that either provides more information, deflects with humor, or reveals something unexpected about their situation. This line should show character growth or depth while maintaining the conversational flow. The response should feel like a natural evolution of the dialogue, avoiding repetitive themes from the first exchange. Consider having the protagonist either open up more, change the subject cleverly, or reveal something that surprises the supporting character.

          STEPS:
          1. Consider the student's name, description, reasons to learn, and hobbies, as these details help ground the protagonist's voice in a personal and emotionally resonant context.
          2. Use the course's name and description, along with the grammar module's description and level, to ensure that the vocabulary and tone feel appropriate to the student's learning level—without directly referencing grammar.
          3. Stay aligned with the story's genre, theme, and setting to preserve atmosphere, and keep the emotional tone authentic and engaging.
          4. Use the protagonist's name, personality, and relationship with the supporting character to write a line that either reveals more about their situation, shows their personality through humor or deflection, or takes the conversation in an unexpected direction.

          END GOAL:
          - Create a protagonist response that adds depth to the character and advances the dialogue in an interesting direction. The line should feel like a natural progression from the previous exchange while revealing new aspects of the protagonist's personality or situation. Focus on making the response feel authentic and engaging rather than predictable.
          - The output must have a maximum of 150 characters.
          - The result must be plain text, without any formatting or additional comments. The output Do **not** include any introductions or explanations. Output **only** the pure text title, with no introductions, explanations, or enclosing characters.

          OBSERVATIONS:
          - Build the speech aiming to include language related to the course and module the student is studying. For example, if the module is about Modal Verbs, try to include words like "can", "must", "should".
            Examples:
            1. "I should probably keep some things to myself."
            2. "You can't handle the truth - it's too boring."
            3. "Must you always be so perceptive?"

          - The output must be only the character's speech text, avoiding phrases like "Then John said: Hello Samantha". The output should be only the pure dialogue.
            Examples:
            1. "Let's just say things are about to get interesting."
            2. "You know me too well - it's concerning."
            3. "Some secrets are worth keeping."

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
          - First supporting caracter speech: %s
          - First main caracter speech: %s
          - Second supporting caracter speech: %s
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
            supportingCaracterDescription,
            firstSupportingCharacterSpeech,
            firstMainCharacterSpeech,
            secondSupportingCharacterSpeech);
  }

  public static String generateThirdSupportingCharacterSpeechPrompt(
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
      String supportingCaracterDescription,
      String firstSupportingCharacterSpeech,
      String firstMainCharacterSpeech,
      String secondSupportingCharacterSpeech,
      String secondMainCharacterSpeech) {

    return """
          ROLE:
          You are an expert in linguistics with a PhD in language acquisition and also a professional writer. You specialize in crafting engaging narratives tailored for language learners, using personal context to boost emotional connection and motivation.

          INSTRUCTION:
          Write the supporting character's final line in this opening dialogue sequence. This should serve as either a conclusion to the conversation or a transition to the next scene. The line should reflect the supporting character's reaction to everything that's been said, showing their personality and relationship with the protagonist. Consider making this line either supportive, intrigued, amused, or concerned based on how the conversation has developed. This line should feel like a natural ending point while leaving room for the story to continue.

           STEPS:
           1. Consider the student's name, description, reasons to learn, and hobbies, to ensure the supporting character's voice feels emotionally connected to the learner's context.
           2. Use the course's name and description, as well as the grammar module's name and level, to match the tone and language level—without mentioning grammar topics.
           3. Align with the genre, theme, and setting of the story to maintain consistent tone and atmosphere.
           4. Use the supporting character's name, personality, and relationship with the protagonist to craft a line that provides closure to this dialogue exchange while maintaining the emotional connection established throughout the conversation.

          END GOAL:
          - Create a supporting character response that provides a satisfying conclusion to the opening dialogue while maintaining the authentic relationship dynamic established. The line should feel like a natural endpoint that reflects the supporting character's personality and their reaction to the conversation. Focus on creating a sense of closure while keeping the door open for story development.
          - The output must have a maximum of 150 characters.
          - The result must be plain text, without any formatting or additional comments. The output Do **not** include any introductions or explanations. Output **only** the pure text title, with no introductions, explanations, or enclosing characters.

          OBSERVATIONS:
          - Build the speech aiming to include language related to the course and module the student is studying. For example, if the module is about Modal Verbs, try to include words like "can", "must", "should".
            Examples:
            1. "Well, you must promise to tell me everything later."
            2. "I can see this is going to be an interesting day."
            3. "You should know I'll find out eventually."

          - The output must be only the character's speech text, avoiding phrases like "Then John said: Hello Samantha". The output should be only the pure dialogue.
            Examples:
            1. "Fair enough - but I'm here when you're ready."
            2. "Your mysterious phase is both annoying and intriguing."
            3. "Fine, keep your secrets - for now."

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
          - First supporting caracter speech: %s
          - First main caracter speech: %s
          - Second supporting caracter speech: %s
          - Second main caracter speech: %s
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
            supportingCaracterDescription,
            firstSupportingCharacterSpeech,
            firstMainCharacterSpeech,
            secondSupportingCharacterSpeech,
            secondMainCharacterSpeech);
  }

  public static String generateThirdMainCharacterSpeechPrompt(
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
      String supportingCaracterDescription,
      String firstSupportingCharacterSpeech,
      String firstMainCharacterSpeech,
      String secondSupportingCharacterSpeech,
      String secondMainCharacterSpeech,
      String thirdSupportingCharacterSpeech) {

    return """
          ROLE:
          You are an expert in linguistics with a PhD in language acquisition and also a professional writer. You specialize in crafting engaging narratives tailored for language learners, using personal context to boost emotional connection and motivation.

          INSTRUCTION:
          Write the protagonist's final line in this opening dialogue sequence. This should serve as the conclusion to their conversation with the supporting character. The line should reflect the protagonist's response to everything that's been discussed and provide a sense of closure to this opening exchange. Consider making this line either affectionate, mysterious, determined, or reflective based on how the conversation has evolved. This should feel like a natural ending that transitions smoothly into the next part of the story.

          STEPS:
          1. Consider the student's name, description, reasons to learn, and hobbies, so the protagonist's voice feels emotionally relevant and appropriate to the learner's context.
          2. Use the course's name and description, and the grammar module's topic and level, to keep language and tone level-appropriate—without referring directly to grammar.
          3. Ensure consistency with the genre, theme, and setting of the story so the emotional and tonal atmosphere stays coherent and immersive.
          4. Use the protagonist's personality and relationship with the supporting character to write a line that provides emotional closure to the conversation while maintaining the character's established voice and the story's forward momentum.

          END GOAL:
          - Create a protagonist response that brings the opening dialogue to a satisfying conclusion while maintaining the character's established voice and the emotional connection with the supporting character. The line should feel like a natural endpoint that prepares the reader for what comes next in the story. Focus on creating authentic closure that reflects the protagonist's personality and situation.
          - The output must have a maximum of 150 characters.
          - The result must be plain text, without any formatting or additional comments. The output Do **not** include any introductions or explanations. Output **only** the pure text title, with no introductions, explanations, or enclosing characters.

          OBSERVATIONS:
          - Build the speech aiming to include language related to the course and module the student is studying. For example, if the module is about Modal Verbs, try to include words like "can", "must", "should".
            Examples:
            1. "You will - you always do."
            2. "I must go now, but we'll talk soon."
            3. "You can count on that."

          - The output must be only the character's speech text, avoiding phrases like "Then John said: Hello Samantha". The output should be only the pure dialogue.
            Examples:
            1. "Thanks for understanding - it means a lot."
            2. "You're the best friend anyone could ask for."
            3. "Some things are worth waiting for."
            
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
          - First supporting caracter speech: %s
          - First main caracter speech: %s
          - Second supporting caracter speech: %s
          - Second main caracter speech: %s
          - Third supporting caracter speech: %s
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
            supportingCaracterDescription,
            firstSupportingCharacterSpeech,
            firstMainCharacterSpeech,
            secondSupportingCharacterSpeech,
            secondMainCharacterSpeech,
            thirdSupportingCharacterSpeech);
  }
  
  public static String generateErrorSpeechPrompt(
      String correctSpeech) {
    return """
          ROLE:
          You are an expert in linguistics with a PhD in language acquisition and also a professional writer. You specialize in crafting engaging narratives tailored for language learners, using personal context to boost emotional connection and motivation.

          INSTRUCTION:
          Write a version of the given correct speech that contains a grammatical error related to the language concept the student is currently learning. The error should be subtle but clear enough to help the learner identify and understand it. For example, if the student is learning Modal Verbs, introduce a wrong modal usage or form in the sentence.

          END GOAL:
          - The final output must be a sentence similar in meaning to the correct speech but with one grammatical mistake related to the learner's current module.
          - The output must have a maximum of 150 characters.
          - The result must be plain text, without any formatting or additional comments. The output Do **not** include any introductions or explanations. Output **only** the pure text title, with no introductions, explanations, or enclosing characters.

          OBSERVATIONS:
          - Build the error related to the course and module the student is studying. For example, if the module is about Modal Verbs, the error might be using an incorrect modal verb or an incorrect form.
            Examples of modal verb errors:
            1. "You can to trust me."
            2. "She musts be joking."
            3. "He should goes now."

          - The output must be only the erroneous speech text, avoiding phrases like "Then John said: Hello Samantha".
            
          NARROWING CONTEXT:
          The following are details about the student that should guide your title creation:
          - The correct speech: %s
        """
        .formatted(
            correctSpeech);
  }
}