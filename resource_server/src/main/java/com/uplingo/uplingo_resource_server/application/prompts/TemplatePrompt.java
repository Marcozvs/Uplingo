package com.uplingo.uplingo_resource_server.application.prompts;

import com.uplingo.uplingo_resource_server.model.enums.GenreEnum;
import java.util.List;
import java.util.Map;

public class TemplatePrompt {

  private static final Map<GenreEnum, List<String>> genreTitleExamples = Map.ofEntries(
      Map.entry(GenreEnum.FANTASY, List.of(
          "The Dragon's Promise",
          "Whispers of the Enchanted Forest",
          "The Last Mage's Quest",
          "Kingdom of Shadows",
          "The Crystal Sword")),
      Map.entry(GenreEnum.SCI_FI, List.of(
          "Beyond the Stars",
          "The Last Colony",
          "Quantum Rift",
          "Echoes of Mars",
          "The Cyborg Rebellion")),
      Map.entry(GenreEnum.ROMANCE, List.of(
          "Hearts in Harmony",
          "A Love to Remember",
          "Under the Cherry Blossoms",
          "Whispers of the Heart",
          "The Summer We Met")),
      Map.entry(GenreEnum.MYSTERY, List.of(
          "The Vanishing Hour",
          "Secrets in the Fog",
          "The Silent Witness",
          "Shadow of the Detective",
          "Murder at Dawn")),
      Map.entry(GenreEnum.THRILLER, List.of(
          "Edge of Fear",
          "The Final Countdown",
          "Chasing the Truth",
          "Silent Threat",
          "Beneath the Surface")),
      Map.entry(GenreEnum.HORROR, List.of(
          "Nightmare's Door",
          "The Haunting of Hollow House",
          "Blood Moon Rising",
          "Whispers from the Dark",
          "The Forgotten Crypt")),
      Map.entry(GenreEnum.ADVENTURE, List.of(
          "Journey to the Lost City",
          "The Treasure of the Forgotten Island",
          "Quest for the Golden Chalice",
          "The Explorer's Secret",
          "Winds of the Wild")),
      Map.entry(GenreEnum.HISTORICAL, List.of(
          "The King's Gambit",
          "Shadows of the Empire",
          "The Silent Revolution",
          "A Soldier's Honor",
          "Echoes of the Past")),
      Map.entry(GenreEnum.DRAMA, List.of(
          "Breaking the Silence",
          "The Last Goodbye",
          "Paths of the Heart",
          "Between Love and War",
          "Tears of Tomorrow")),
      Map.entry(GenreEnum.COMEDY, List.of(
          "Laughing in the Rain",
          "The Accidental Hero",
          "Oops! Wrong Adventure",
          "The Misfit Chronicles",
          "A Series of Funny Events")),
      Map.entry(GenreEnum.ACTION, List.of(
          "Run to the Edge",
          "Firestorm Rising",
          "Strike of the Thunder",
          "Operation Iron Fist",
          "Race Against Time")),
      Map.entry(GenreEnum.CRIME, List.of(
          "City of Lies",
          "The Silent Heist",
          "Broken Trust",
          "Blood on the Asphalt",
          "Shadows of Justice")),
      Map.entry(GenreEnum.DETECTIVE, List.of(
          "The Missing Clue",
          "Murder in the Alley",
          "Secrets Behind the Badge",
          "The Last Detective",
          "Case of the Hidden Truth")),
      Map.entry(GenreEnum.BIOGRAPHY, List.of(
          "A Life Remembered",
          "Steps of a Legend",
          "Journey Through Time",
          "The Road Less Traveled",
          "Echoes of a Life")),
      Map.entry(GenreEnum.DYSTOPIAN, List.of(
          "Ashes of Tomorrow",
          "The Broken World",
          "Rise of the Forgotten",
          "The Last Sanctuary",
          "City of Shadows")),
      Map.entry(GenreEnum.PARANORMAL, List.of(
          "The Ghost Whisperer",
          "Beyond the Veil",
          "Shadows of the Unknown",
          "Haunted Memories",
          "The Silent Apparition")),
      Map.entry(GenreEnum.SUPERHERO, List.of(
          "Rise of the Guardian",
          "Shadow of the Mask",
          "Legacy of Power",
          "The Last Protector",
          "City of Heroes")),
      Map.entry(GenreEnum.FAIRY_TALE, List.of(
          "The Enchanted Grove",
          "The Princess and the Moon",
          "Tales of the Silver Forest",
          "The Magic Mirror",
          "The Lost Kingdom")),
      Map.entry(GenreEnum.WESTERN, List.of(
          "Dust and Thunder",
          "The Last Outlaw",
          "Saddle Up at Dawn",
          "Blood on the Prairie",
          "The Gunslinger’s Path")),
      Map.entry(GenreEnum.SPOOKY, List.of(
          "The Midnight Howl",
          "Creeping Shadows",
          "The Forgotten Graveyard",
          "Whispers in the Mist",
          "The Haunting Hour")));

  private static final Map<GenreEnum, List<String>> genreBackOverExamples = Map.ofEntries(
      Map.entry(GenreEnum.FANTASY, List.of(
          "In a realm where magic breathes through every stone and forest, a young dreamer embarks on a quest to awaken the lost powers of the ancient world and discover their destiny, guided only by courage, friendship, and the light of forgotten legends.",
          "A mysterious prophecy echoes through the winds of an enchanted kingdom. With danger rising and dark forces looming, a brave soul must cross forbidden lands to uncover a truth that could reshape the fate of all magic forever.",
          "Beneath the shimmering moons of Eldoria, a hidden path calls to those who still believe. One traveler, armed with a gift they don't understand, must restore balance to a crumbling realm before the last star falls from the sky.")),
      Map.entry(GenreEnum.SCI_FI, List.of(
          "In a future where AI governs humanity and space colonies drift apart, a young engineer discovers a secret that could unite or destroy civilizations. Her mission across galaxies will test the limits of technology, identity, and the language of survival.",
          "Orbiting the ruins of Earth, a scavenger finds a lost transmission from a forgotten civilization. As factions race to decode its meaning, he must decide whether to protect the truth or rewrite the destiny of mankind in the stars.",
          "Inside a simulation designed to teach empathy to machines, a human trapped between code and consciousness must unlock a way out before their memories fade. Time is running out—and reality may not be what it seems.")),
      Map.entry(GenreEnum.ROMANCE, List.of(
          "In a quiet seaside town, a chance encounter between two dreamers sparks a love that defies distance and language. As they navigate cultural gaps and emotional scars, their hearts discover a rhythm only they can hear.",
          "She believed in logic, he lived through emotion. When a misdelivered letter draws them together, their lives entwine in a journey of healing, second chances, and a love that blossoms between the lines of every shared word.",
          "Under the city lights, two souls meet through a language app. Their conversations turn into connection, and connection into longing. But can a digital romance survive the leap into the real world and the challenges of love unspoken?")),
      Map.entry(GenreEnum.MYSTERY, List.of(
          "In a fog-drenched village where silence hides secrets, a student studying abroad stumbles upon a decades-old diary. Each entry unlocks a mystery that threatens to entangle them in a chilling story far bigger than they imagined.",
          "A stolen journal, a whispering hallway, and a shadow that watches. When a curious mind begins asking questions, long-buried truths start to emerge. Some puzzles were never meant to be solved.",
          "As strange symbols appear across the campus walls, a linguistics learner finds themselves at the center of a riddle spanning generations. With each clue, the danger grows—and so does the realization that words can kill.")),
      Map.entry(GenreEnum.THRILLER, List.of(
          "Trapped in a foreign city after a blackout, a student must decipher cryptic messages to escape a conspiracy that no one dares name. Every step forward could be their last as trust dissolves and time runs out.",
          "When a routine commute turns into a manhunt, an ordinary language learner becomes the only witness to a deadly secret. With the world watching and assassins closing in, silence is no longer an option.",
          "A new AI language tool promises fluency—but hides a dark agenda. As a curious beta tester uncovers more than just vocabulary, she races against time to reveal the truth before her voice—and her freedom—are erased."))

      ,
      Map.entry(GenreEnum.HORROR, List.of(
          "In an abandoned boarding school where time has stopped, a curious student awakens something ancient. As reality unravels and shadows whisper secrets, the line between nightmare and truth fades—and escape may no longer be an option.",
          "Every night at 3:03 AM, the same figure appears in the mirror. When a language learner records a video for class, they capture more than their grammar practice—something unseen is watching and wants to be heard.",
          "When a mysterious lullaby echoes through the woods, a group of friends follows it into darkness. What began as a weekend trip becomes a descent into terror, as an ancient presence forces them to face their worst fears—one by one.")),

      Map.entry(GenreEnum.ADVENTURE, List.of(
          "From hidden temples to lost cities, a language enthusiast sets off on a journey sparked by a cryptic postcard. Along the way, they’ll decipher clues, make allies, and uncover a secret history buried deep within the world—and themselves.",
          "After discovering a strange map inside an old grammar book, a young explorer travels across cultures and continents. With each step, the adventure brings lessons not found in any classroom—and dangers beyond their imagination.",
          "Seeking inspiration, a student joins a global treasure hunt where language is the key to survival. Each challenge tests their wit, courage, and communication—until the final puzzle reveals a truth that changes everything.")),

      Map.entry(GenreEnum.HISTORICAL, List.of(
          "In the heart of 18th-century Europe, a scholar finds themselves caught between revolution and tradition. As empires rise and fall, their letters—written in a new language—carry secrets that could change the course of history.",
          "During World War II, a bilingual spy must balance duty, love, and identity. With every message sent and every word translated, the line between truth and deception grows thinner—and one mistake could cost everything.",
          "In ancient Alexandria, a student uncovers a scroll that speaks of forbidden knowledge. As they translate its forgotten words, political unrest brews—and the choice between silence and rebellion will define generations to come.")),

      Map.entry(GenreEnum.DRAMA, List.of(
          "A young immigrant faces a new language, a new city, and the silence of starting over. Through letters home, they find strength, make mistakes, and slowly discover their voice in a world that speaks faster than they can keep up.",
          "When her family fractures, a teenager uses writing to stay afloat. Each journal entry in her second language helps piece together the truth—and helps her become the person she never thought she could be.",
          "Between grief and hope, a student navigates love, failure, and the weight of expectations. In a classroom far from home, they learn that some of life’s most important lessons are spoken without words.")),

      Map.entry(GenreEnum.COMEDY, List.of(
          "When a student mixes up a verb in class, they accidentally propose marriage to their professor. Chaos, laughter, and cultural confusion follow in this light-hearted tale of learning through trial, error, and a lot of red cheeks.",
          "Two roommates—one serious, one sarcastic—take an online language course together. With every mispronounced word and mistranslated phrase, their friendship grows stronger—and so do the laughs.",
          "Trying to impress a crush, a shy learner pretends to be fluent—but their phone’s translation app has other plans. What follows is a hilarious chain of misunderstandings, mistaken identities, and surprisingly heartfelt moments.")),
      Map.entry(GenreEnum.ACTION, List.of(
          "When a quiet student discovers a hidden message during an online class, they’re thrust into a global conspiracy. With danger at every turn, their knowledge of language becomes their greatest weapon in a race against time to stop the unthinkable.",
          "A courier with a secret past is tasked with delivering a mysterious package across borders. Chased by mercenaries and guided only by coded clues, they must rely on their language skills and instincts to survive.",
          "During a study abroad trip, a student is caught in a violent uprising. To protect those around them, they must make daring choices—and find courage they never knew they had—in a city where every word can be a matter of life or death.")),

      Map.entry(GenreEnum.CRIME, List.of(
          "A language tutor goes missing, and their last message is a grammar exercise filled with strange clues. As the investigation unfolds, a curious student realizes the answers might be hidden in the very lessons they once ignored.",
          "In a city gripped by corruption, an interpreter uncovers a money-laundering scheme hidden within diplomatic conversations. Every word translated could be a lifeline—or a death sentence.",
          "When a local café owner is arrested, a bilingual barista sets out to clear their name. The evidence is buried in receipts, overheard conversations, and misunderstood slang—and solving the mystery means risking everything.")),

      Map.entry(GenreEnum.DETECTIVE, List.of(
          "A young detective-in-training joins an international academy where every case is solved through language, culture, and observation. But when a classmate vanishes, the game turns real—and the clues are hidden in plain sight.",
          "In a small town where no one speaks the same dialect, a foreign exchange student helps uncover the truth behind a string of crimes. Their understanding of language becomes the key to unlocking a tangled web of secrets.",
          "A detective haunted by an old case discovers a clue in a student’s translated short story. Together, they must revisit a trail gone cold—where the only thing more elusive than the killer is the truth buried in forgotten words.")),

      Map.entry(GenreEnum.BIOGRAPHY, List.of(
          "From shy beginnings to confident conversations, this story follows the journey of a student who finds their voice through language. It’s a story of resilience, learning, and the courage to speak even when you’re afraid to.",
          "Told through letters and journal entries, this biography explores how one learner’s passion for storytelling helped them connect with the world—and leave a legacy of empathy and expression.",
          "Through hardship and humor, this story traces the real struggles of learning a language abroad. From embarrassing moments to major victories, it captures one person’s evolution as both a speaker and a human being.")),

      Map.entry(GenreEnum.DYSTOPIAN, List.of(
          "In a future where language is controlled and free speech is forbidden, one student finds an ancient grammar book. With every phrase they learn, they rediscover lost truths—and risk everything to ignite a revolution.",
          "Society is divided by syntax and pronunciation. Those who speak the elite dialect live in luxury, while the rest remain silent. One learner from the outskirts dares to challenge the system with nothing but courage and words.",
          "The world is silent—speech has been outlawed, replaced by symbols. But when a forgotten language resurfaces, a young rebel must decode the past to save the future, using grammar as both shield and sword.")),
      Map.entry(GenreEnum.PARANORMAL, List.of(
          "After moving to a quiet town, a language student starts hearing whispers in unfamiliar tongues. As they translate the voices, a chilling truth emerges: they’ve been chosen to bridge the world of the living and the dead.",
          "While practicing grammar late at night, a student’s dictionary begins changing on its own. Each word leads to a supernatural message—and the key to solving a decades-old haunting trapped between pages.",
          "Strange symbols appear in the student’s notes, ones they never wrote. As unexplainable events unfold, they must master ancient words to uncover the ghostly presence trying to make contact—and why.")),

      Map.entry(GenreEnum.SUPERHERO, List.of(
          "In a world where powers are unlocked through language, a shy student accidentally discovers a phrase that grants them control over time. But with great grammar comes great responsibility—and a villain who wants it silenced forever.",
          "Each new verb unlocks a new power, and one student is learning faster than anyone before. But when heroes go missing, they must decide: study quietly or rise up and become the voice the world needs.",
          "A superhero academy teaches students to wield words like weapons. One learner, often overlooked, holds the rare ability to command peace—but only if they can find the right words in time.")),

      Map.entry(GenreEnum.FAIRY_TALE, List.of(
          "In a land where stories shape reality, a student stumbles into a forgotten tale. To return home, they must rewrite the ending—with the help of enchanted grammar rules and a magical talking book.",
          "A lonely learner is gifted a dusty fairy tale collection. One night, the stories come alive, pulling them into a world where each chapter is a grammar challenge—and every sentence holds a spell.",
          "When a wishing tree responds in English, a curious child is whisked into a fantastical world of fairy queens and fabled beasts. But their stay depends on their ability to learn and speak the language of dreams.")),

      Map.entry(GenreEnum.WESTERN, List.of(
          "In a dusty frontier town where misunderstandings can be fatal, a newcomer with a dictionary and a dream must learn fast. Armed with language and grit, they’ll uncover a hidden plot and find their place in the Wild West.",
          "A drifter arrives in a town split by silence and prejudice. As they teach locals to communicate across cultures, they uncover old wounds, buried secrets—and a showdown that could unite or destroy them all.",
          "Between saloons and desert trails, one student rides west to escape their past. But grammar is the only thing standing between them and survival in a lawless land where every word carries weight.")),

      Map.entry(GenreEnum.SPOOKY, List.of(
          "A student visits a language camp deep in the woods, but strange occurrences begin after dark. Whispers in old dialects and eerie lessons hint that the ghosts of the past still roam—and want to be understood.",
          "During a school exchange, a learner is sent to a mysterious mansion. The locals speak in riddles, and ancient grammar rules seem to unlock hidden rooms—along with secrets best left buried.",
          "Every year, one student disappears from the village’s English class. When strange symbols appear on their homework, they must race to translate them before the next full moon—or become the next victim of the legend.")));

  private static final Map<GenreEnum, List<String>> genreCharacterDescriptionExamples = Map.ofEntries(
      Map.entry(GenreEnum.FANTASY, List.of(
          "In a land where magic weaves through every living thing, a young mage burdened by a mysterious past embarks on a perilous quest to restore balance to a fractured realm. Guided by ancient prophecies and unwavering courage, they must face dark forces threatening to consume all light and hope in the world.",
          "An adventurous spirit, gifted with the rare ability to commune with mystical creatures, journeys beyond the enchanted forests to unite fractured kingdoms. With magic and friendship as their strongest weapons, they confront shadows that seek to plunge the realm into eternal darkness, fighting not just for survival, but for the hope of all.",
          "A determined dreamer, born under the light of the twin moons, wields ancient powers forgotten by time. Destined to challenge the fate written in the stars, this character must navigate treacherous lands and forge alliances in a world on the brink of collapse, where every choice carries the weight of the realm’s future.")),
      Map.entry(GenreEnum.SCI_FI, List.of(
          "A brilliant scientist haunted by past failures ventures into uncharted galaxies to unlock secrets of the cosmos. With advanced technology and unyielding resolve, they confront alien threats and unravel mysteries that could alter humanity's fate forever.",
          "A rogue pilot with a cybernetic arm fights against a corrupt interstellar regime, driven by a desire for freedom and redemption. Their quick thinking and unmatched skills keep them alive in the vast coldness of space.",
          "An empathic android struggles to understand human emotions while navigating a universe on the brink of war, torn between programmed logic and a newfound conscience.")),
      Map.entry(GenreEnum.ROMANCE, List.of(
          "A passionate soul seeking connection in a world of chaos, navigating love's delicate dance while overcoming personal fears. Their journey intertwines hope, heartbreak, and the courage to open their heart again against all odds.",
          "A shy artist finds unexpected love in a bustling city, learning to trust and embrace vulnerability while chasing dreams and overcoming past heartbreak.",
          "Two strangers bound by fate cross paths in a small town, discovering that love can blossom even in the darkest moments, inspiring hope and change.")),
      Map.entry(GenreEnum.MYSTERY, List.of(
          "A sharp-minded detective with a troubled past delves into enigmatic crimes, chasing shadows and hidden truths. Driven by justice and personal demons, they uncover secrets that blur the lines between reality and deception.",
          "An investigative journalist risks everything to expose a deep conspiracy, navigating a web of lies and danger while seeking the truth that could shake the foundations of power.",
          "A retired spy is pulled back into the game to solve a chilling disappearance, confronting ghosts of the past and dangerous enemies lurking in plain sight.")),
      Map.entry(GenreEnum.THRILLER, List.of(
          "An adrenaline-fueled operative races against time to prevent catastrophe, facing betrayal and danger at every turn. With nerves of steel and a relentless will, they confront threats lurking in the shadows, risking everything for the greater good.",
          "A former soldier turned vigilante stalks the city’s darkest corners, fighting corruption with unyielding resolve and haunted by personal loss that fuels their crusade.",
          "A skilled hacker uncovers a deadly plot, forced to outsmart powerful enemies in a high-stakes game of survival where trust is a luxury they cannot afford.")),
      Map.entry(GenreEnum.HORROR, List.of(
          "A haunted survivor of a tragic past confronts unspeakable horrors lurking in darkness, battling inner demons while fighting for sanity in a world gone mad.",
          "A paranormal investigator delves into a cursed mansion, uncovering terrifying secrets that threaten to consume their soul and unravel reality itself.",
          "A small-town librarian discovers an ancient evil awakening beneath the streets, forced to face nightmares that blur the lines between life and death.")),
      Map.entry(GenreEnum.ADVENTURE, List.of(
          "A fearless explorer driven by curiosity and a thirst for discovery, navigating uncharted lands with courage and a heart full of hope, inspired by the thrill of adventure and the desire to overcome any obstacle that lies ahead.",
          "An intrepid traveler with a keen mind and steady hand, facing dangers and wonders alike on a quest that tests bravery and resilience, fueled by a deep passion to uncover hidden secrets beyond the horizon.",
          "A daring soul whose spirit is bound to the open road, seeking excitement and meaning in every step, armed with determination and an unbreakable will to face the unknown and forge a legacy of courage.")),
      Map.entry(GenreEnum.HISTORICAL, List.of(
          "A thoughtful individual shaped by the trials of their era, balancing honor and duty amidst turbulent times, whose journey reveals the resilience of the human spirit and the power of choices that shape history.",
          "An ambitious figure navigating the complex social and political landscapes of their age, whose courage and wisdom illuminate the path through conflict and change, embodying the essence of their historical period.",
          "A passionate soul caught in the tides of history, whose personal struggles and triumphs offer a window into a world long past, reflecting timeless themes of love, loss, and perseverance.")),
      Map.entry(GenreEnum.DRAMA, List.of(
          "A deeply emotional character wrestling with inner conflicts and complex relationships, whose journey explores themes of love, loss, and redemption, portrayed with honesty and subtlety that resonate with the learner's own experiences.",
          "An individual confronting the hardships of life with resilience and hope, navigating a world of shifting emotions and difficult choices, whose story is a powerful reflection of human vulnerability and strength.",
          "A nuanced protagonist whose struggles and growth reveal the intricate tapestry of human connections, capturing the essence of drama through authentic, heartfelt moments that inspire empathy and understanding.")),
      Map.entry(GenreEnum.COMEDY, List.of(
          "A witty and charming character whose humor and lightheartedness brighten even the darkest days, navigating life's absurdities with a smile and a quick mind, bringing laughter and joy to every scene.",
          "An endearing misfit with a knack for finding themselves in hilarious predicaments, whose optimism and cleverness turn everyday challenges into comedic adventures that delight and entertain.",
          "A playful spirit with an infectious laugh and an eye for the ridiculous, who uses humor as a tool to connect, overcome obstacles, and celebrate the quirks of life in a way that feels warm and relatable.")),
      Map.entry(GenreEnum.ACTION, List.of(
          "A relentless warrior forged in the heat of battle, driven by a fierce sense of justice and unyielding determination to protect those they love, facing danger head-on with skill and bravery.",
          "An agile and resourceful hero who thrives in high-stakes situations, combining quick thinking and physical prowess to overcome formidable foes and impossible odds with unwavering resolve.",
          "A fearless combatant whose heart beats with the thrill of the fight, balancing raw power and sharp intellect to navigate a world of action and intrigue, always ready to face the next challenge.")),
      Map.entry(GenreEnum.CRIME, List.of(
          "A hardened figure shaped by the gritty streets and harsh realities, whose sharp instincts and unflinching resolve cut through deception and danger in pursuit of justice, embodying the moral complexities of crime and consequence.",
          "A cunning individual entangled in a web of crime and betrayal, navigating a shadowy world with a mix of charm and ruthlessness, driven by survival and an unyielding thirst for truth beneath the city's dark surface.",
          "A complex character walking the fine line between right and wrong, shaped by past mistakes and fierce determination, whose story unveils the tangled motives and hidden dangers lurking in the criminal underworld.")),
      Map.entry(GenreEnum.DETECTIVE, List.of(
          "A brilliant detective with a keen eye for detail and relentless pursuit of truth, unraveling mysteries that baffle others, guided by intuition, logic, and an unwavering commitment to justice.",
          "An enigmatic investigator whose past fuels a tireless drive to solve cold cases and expose secrets, navigating a maze of clues, suspects, and hidden agendas in a quest to bring light to darkness.",
          "A sharp-minded sleuth with a reputation for solving the unsolvable, combining empathy and intellect to piece together puzzles that reveal the heart of human nature and the shadows within.")),
      Map.entry(GenreEnum.BIOGRAPHY, List.of(
          "A life story marked by extraordinary experiences and challenges, tracing the personal growth, struggles, and triumphs that shaped a remarkable individual’s journey through history and self-discovery.",
          "An inspiring tale of resilience and transformation, capturing the highs and lows of a real person’s life, illuminating the human spirit’s capacity to overcome adversity and leave a lasting legacy.",
          "A vivid portrayal of a life lived with passion and purpose, exploring defining moments, relationships, and milestones that reveal the character behind the achievements and struggles.")),
      Map.entry(GenreEnum.DYSTOPIAN, List.of(
          "In a bleak future where freedom is scarce and hope is fragile, a resilient individual fights to reclaim humanity’s lost dignity amidst oppression, surveillance, and the shadows of a broken society.",
          "A survivor in a dystopian world torn by conflict and despair, whose courage and cunning spark a rebellion against a ruthless regime, embodying the enduring fight for justice and change.",
          "A lone rebel navigating desolate landscapes and shattered ideals, driven by a vision of a better world and the determination to overcome the darkness threatening to consume all hope.")),
      Map.entry(GenreEnum.PARANORMAL, List.of(
          "A gifted person caught between the natural and supernatural, grappling with mysterious forces that challenge their understanding of reality, and unlocking secrets hidden beyond the veil.",
          "An enigmatic character who senses the presence of unseen entities, navigating a world where spirits and shadows intermingle, driven by a quest to understand and protect the boundary between worlds.",
          "A soul marked by paranormal abilities, whose journey through haunted places and unexplained phenomena reveals deep truths about fear, courage, and the unseen mysteries of existence.")),
      Map.entry(GenreEnum.SUPERHERO, List.of(
          "A courageous hero with extraordinary powers, battling inner doubts and external threats to protect their city, inspiring hope while struggling to balance a secret identity with personal sacrifices.",
          "An unlikely champion whose strength and compassion unite to face relentless villains, driven by a sense of justice and the burden of responsibility that comes with their abilities.",
          "A symbol of resilience and power, navigating a world of chaos and corruption while standing as a beacon for the oppressed, fighting to make a difference despite personal costs.")),
      Map.entry(GenreEnum.FAIRY_TALE, List.of(
          "A young dreamer enchanted by magical realms, whose innocence and bravery spark wondrous adventures filled with mythical creatures, timeless lessons, and the quest for true belonging.",
          "A character woven from ancient tales, destined to overcome curses and dark forces through kindness, wit, and a touch of magic, discovering the power of hope and friendship.",
          "A spirited soul living between worlds of fantasy and reality, facing trials that test courage and heart, unlocking secrets hidden in enchanted forests and forgotten kingdoms.")),
      Map.entry(GenreEnum.WESTERN, List.of(
          "A lone gunslinger with a troubled past, riding through dusty towns and lawless frontiers, driven by a personal code of honor and the quest for redemption in a harsh, unforgiving land.",
          "A rugged pioneer navigating the wild west, whose grit and determination carve out justice and survival amid danger, rivalries, and the vast untamed wilderness.",
          "An outlaw with a complicated legacy, struggling between a violent past and the hope for peace, caught in a world where every decision echoes across desert canyons and open plains.")),
      Map.entry(GenreEnum.SPOOKY, List.of(
          "A curious soul drawn to haunted places, wrestling with eerie secrets and restless spirits, unraveling mysteries that blur the line between the living and the dead.",
          "A character trapped between reality and nightmare, confronting phantoms of the past and shadowy entities, seeking answers while facing their deepest fears in a chilling tale.",
          "An investigator of the paranormal, whose courage uncovers dark truths lurking in forgotten places, battling unseen forces with a mix of skepticism and growing belief.")));

  public static List<String> getExampleTitlesForGenre(GenreEnum genre) {
    return genreTitleExamples.getOrDefault(genre, List.of(
        "A Tale Untold",
        "Journey Begins",
        "Mystery Unfolds",
        "Shadows and Light",
        "The Unknown Path"));
  }

  public static List<String> getExampleBackOversForGenre(GenreEnum genre) {
    return genreBackOverExamples.getOrDefault(genre, List.of(
        "A journey awaits in a world of wonder and fear. One choice, one path, and everything changes.",
        "When hope is scarce and courage is tested, a single step into the unknown may light the way.",
        "In a land shaped by stories, a new legend begins — told in the language of dreams and discovery."));
  }

  public static List<String> getExampleCharacterDescriptionsForGenre(GenreEnum genre) {
    return genreCharacterDescriptionExamples.getOrDefault(genre, List.of(
        "A unique character shaped by their journey, blending courage and curiosity to explore new horizons and face unexpected challenges with heart and mind.",
        "A thoughtful individual whose story reveals resilience and growth, inspiring others through their struggles and triumphs in a world full of complexity.",
        "An engaging personality with a blend of humor and strength, navigating life's twists and turns with optimism and an unbreakable spirit."));
  }
}
