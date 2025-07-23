import { CharacterTypeEnum } from "@modules/story/enums/story.enums";

export interface IStoryCharacterItem {
  id: string;
  storyId: string;
  name: string;
  characterType: CharacterTypeEnum;
  description: string;
}
