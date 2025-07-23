package com.uplingo.uplingo_resource_server.application.utils;

import com.uplingo.uplingo_resource_server.model.enums.GenreEnum;

import java.util.Map;

import static java.util.Map.entry;

public class GenreToneHelper {

  private static final Map<GenreEnum, String> GENRE_TONES = Map.ofEntries(
    entry(GenreEnum.FANTASY, "Majestic and immersive, with a tone that evokes awe, mystery, and ancient power. Worlds are rich in lore, magic flows through the land, and the emotional weight of prophecy, honor, or destiny drives characters. The tone may be noble, hopeful, or shadowed by dark enchantments."),
    entry(GenreEnum.SCI_FI, "Analytical and imaginative, with a tone that explores the unknown—space, time, technology, and society’s evolution. It may be dystopian, philosophical, or thrilling, often provoking questions about humanity’s future, ethics, and our relationship with machines or alien life."),
    entry(GenreEnum.ROMANCE, "Warm and emotionally rich, with a tone that can range from tender and sweet to passionate or bittersweet. Characters navigate desire, vulnerability, and connection. Whether uplifting or tragic, the tone emphasizes intimacy, personal growth, and emotional stakes."),
    entry(GenreEnum.MYSTERY, "Tense and cerebral, with a tone filled with curiosity, suspicion, and revelation. The narrative builds through subtle clues and hidden motives, creating an atmosphere of uncertainty. The tone often shifts from quiet unease to dramatic confrontation as secrets unravel."),
    entry(GenreEnum.THRILLER, "Fast-paced and electrifying, the tone is laced with suspense, danger, and psychological tension. Stakes are high and time is limited. There’s a constant feeling of being watched or pursued, and the narrative thrives on twists, urgency, and adrenaline."),
    entry(GenreEnum.HORROR, "Dark and foreboding, with a tone that instills fear, dread, or psychological unease. Whether supernatural or psychological, horror explores the terrifying unknown and human vulnerability. The tone often grows from eerie stillness to chaotic terror."),
    entry(GenreEnum.ADVENTURE, "Bold and exhilarating, with a tone full of discovery, danger, and heroism. Characters journey through uncharted lands, facing obstacles that test courage and resolve. The tone is optimistic and daring, often blending excitement with moments of wonder or peril."),
    entry(GenreEnum.HISTORICAL, "Reflective and grounded, with a tone that evokes a deep connection to the past. It may be nostalgic, solemn, or dramatic, shaped by real events and social contexts. The tone respects authenticity while exploring the emotional and moral depth of another time."),
    entry(GenreEnum.DRAMA, "Serious and emotionally layered, with a tone that centers on personal struggles, relationships, and moral dilemmas. Often introspective or melancholic, the tone reveals vulnerability and growth, emphasizing character depth over plot spectacle."),
    entry(GenreEnum.COMEDY, "Playful and witty, with a tone that highlights irony, exaggeration, and human flaws in lighthearted or absurd ways. Even when touching on serious themes, the tone remains upbeat, focusing on humor, timing, and the joy of unexpected situations."),
    entry(GenreEnum.ACTION, "Intense and kinetic, with a tone driven by urgency, conflict, and high stakes. Scenes are dynamic and fast-moving, often explosive, with characters under constant pressure. The tone can be gritty or heroic, but always thrives on momentum and tension."),
    entry(GenreEnum.CRIME, "Gritty and morally complex, with a tone that delves into law, justice, and the criminal mind. It may be cold, suspenseful, or intense, exploring both the psychological depth of criminals and the ethical boundaries of those who pursue them."),
    entry(GenreEnum.DETECTIVE, "Observant and methodical, with a tone built around deduction, intrigue, and detail. The narrative is often slow-burning and focused on unraveling puzzles through logic and insight. Tone balances professionalism with underlying emotional or moral tension."),
    entry(GenreEnum.BIOGRAPHY, "Sincere and introspective, with a tone that highlights personal growth, resilience, and the impact of real-life experiences. The tone can be uplifting, tragic, or contemplative, offering emotional depth and historical insight into the subject’s journey."),
    entry(GenreEnum.DYSTOPIAN, "Bleak and intense, with a tone of oppression, resistance, and societal decay. Characters often face surveillance, control, and loss of identity. The tone challenges ideals and reflects fears about the future through emotionally charged, critical storytelling."),
    entry(GenreEnum.PARANORMAL, "Eerie and mysterious, with a tone that blurs reality and the supernatural. Characters confront ghostly forces, psychic events, or unexplained phenomena. The tone ranges from quietly unsettling to terrifying, evoking fear through the unknown."),
    entry(GenreEnum.SUPERHERO, "Bold and dramatic, with a tone that balances moral conflict, personal sacrifice, and epic action. Heroes face powerful foes and inner dilemmas. The tone is often inspiring and cinematic, with themes of justice, identity, and redemption."),
    entry(GenreEnum.FAIRY_TALE, "Enchanting and symbolic, with a tone that blends innocence, danger, and moral lessons. The narrative may be whimsical or dark, often told in a lyrical or magical voice. Tone invites wonder while exploring timeless truths through fantasy."),
    entry(GenreEnum.WESTERN, "Stoic and rugged, with a tone of frontier justice, solitude, and survival. It evokes lawlessness, moral ambiguity, and the harsh beauty of untamed lands. The tone is often slow, reflective, and shaped by silence, grit, and personal codes of honor."),
    entry(GenreEnum.SPOOKY, "Mysterious and playfully eerie, with a tone that blends light horror with intrigue. It may include haunted places, strange events, or ghostly encounters, often told with a sense of suspense and charm rather than full terror.")
  );

  public static String getTone(GenreEnum genre) {
    return GENRE_TONES.getOrDefault(genre, "Neutral and undefined tone.");
  }
}
