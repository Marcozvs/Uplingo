CREATE TABLE app_user (
  id UUID PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(320) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  roles VARCHAR[] NOT NULL,
  description TEXT,
  reasons_learn TEXT,
  user_language VARCHAR(20) NOT NULL,
  photo_url TEXT NOT NULL,
  banned BOOLEAN NOT NULL,
  banned_at TIMESTAMP,
  banned_by UUID,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP,
  updated_by UUID,
  first_time BOOLEAN NOT NULL
);

CREATE TABLE user_hobby (
  id UUID PRIMARY KEY,
  user_id UUID NOT NULL,
  description VARCHAR(300) NOT NULL,
  created_by UUID NOT NULL,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP,
  updated_by UUID,
  CONSTRAINT fk_app_user_hobbies_user FOREIGN KEY (user_id) REFERENCES app_user (id)
);

CREATE TABLE user_energy (
  id UUID PRIMARY KEY,
  user_id UUID NOT NULL,
  valid_until TIMESTAMP NOT NULL,
  consumed BOOLEAN NOT NULL,
  created_by UUID NOT NULL,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP,
  updated_by UUID,
  CONSTRAINT fk_user_energy_user FOREIGN KEY (user_id) REFERENCES app_user (id)
);


CREATE TABLE course (
  id UUID PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  description TEXT NOT NULL,
  created_by UUID NOT NULL,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP,
  updated_by UUID
);

CREATE TABLE grammar_module (
  id UUID PRIMARY KEY,
  course_id UUID NOT NULL,
  name VARCHAR(255) NOT NULL,
  description TEXT NOT NULL,
  level VARCHAR(10) NOT NULL,
  created_by UUID NOT NULL,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP,
  updated_by UUID,
  CONSTRAINT fk_grammar_module_course FOREIGN KEY (course_id) REFERENCES course (id)
);

CREATE TABLE story (
  id UUID PRIMARY KEY,
  user_id UUID NOT NULL,
  grammar_module_id UUID NOT NULL,
  title TEXT NOT NULL,
  back_over TEXT NOT NULL,
  story_status VARCHAR(20) NOT NULL,
  genre VARCHAR(20) NOT NULL,
  narrator_type VARCHAR(20) NOT NULL,
  setting TEXT NOT NULL,
  created_by UUID NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_story_user FOREIGN KEY (user_id) REFERENCES app_user (id),
  CONSTRAINT fk_story_grammar_module FOREIGN KEY (grammar_module_id) REFERENCES grammar_module (id)
);


CREATE TABLE story_character (
  id UUID PRIMARY KEY,
  story_id UUID NOT NULL,
  name TEXT NOT NULL,
  description TEXT NOT NULL,
  character_type VARCHAR(20) NOT NULL,
  created_by UUID NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_story_character_story FOREIGN KEY (story_id) REFERENCES story (id)
);

CREATE TABLE chapter (
  id UUID PRIMARY KEY,
  user_id UUID NOT NULL,
  story_id UUID NOT NULL,
  chapter_index INTEGER NOT NULL,
  current_dialog_index INTEGER NOT NULL,
  title TEXT NOT NULL,
  introduction TEXT NOT NULL,
  correct_answers INTEGER NOT NULL,
  chapter_status VARCHAR(20) NOT NULL,
  created_by UUID NOT NULL,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP,
  updated_by UUID,
  CONSTRAINT fk_chapter_user FOREIGN KEY (user_id) REFERENCES app_user (id),
  CONSTRAINT fk_chapter_story FOREIGN KEY (story_id) REFERENCES story (id)
);

CREATE TABLE
  spring_ai_chat_memory (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid (),
    conversation_id VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    type VARCHAR(50) NOT NULL,
    timestamp TIMESTAMP NOT NULL
  );

CREATE TABLE user_chapter_result (
  id UUID PRIMARY KEY,
  user_id UUID NOT NULL,
  chapter_id UUID NOT NULL,
  grade VARCHAR(5) NOT NULL,
  xp_earned INTEGER NOT NULL,
  completed_at TIMESTAMP NOT NULL,
  created_by UUID NOT NULL,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP,
  updated_by UUID,
  CONSTRAINT fk_user_chapter_result_user FOREIGN KEY (user_id) REFERENCES app_user (id),
  CONSTRAINT fk_user_chapter_result_chapter FOREIGN KEY (chapter_id) REFERENCES chapter (id)
);

CREATE TABLE chapter_answer (
  id UUID PRIMARY KEY,
  chapter_id UUID NOT NULL,
  character_id UUID NOT NULL,
  character_type VARCHAR(20) NOT NULL,
  character_name TEXT NOT NULL,
  answer_index INTEGER NOT NULL,
  content TEXT NOT NULL,
  correct BOOLEAN NOT NULL,
  step INTEGER NOT NULL,
  created_by UUID NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP,
  updated_by UUID,
  CONSTRAINT fk_chapter_answer_chapter FOREIGN KEY (chapter_id) REFERENCES chapter (id),
  CONSTRAINT fk_chapter_answer_character FOREIGN KEY (character_id) REFERENCES story_character (id)
);

CREATE TABLE chapter_narration (
  id UUID PRIMARY KEY,
  chapter_id UUID NOT NULL,
  step INTEGER NOT NULL,
  content TEXT NOT NULL,
  created_by UUID NOT NULL,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP,
  updated_by UUID,
  CONSTRAINT fk_narration_chapter FOREIGN KEY (chapter_id) REFERENCES chapter (id)
);