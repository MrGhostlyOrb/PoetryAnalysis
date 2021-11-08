import jellyfish as soundex
import string
from nltk.corpus import wordnet as wordnet

lines = ["This is a poem for you", "that rhymes with two", "and has no meaning", "and is definatly leaning"]

print("Input : ", lines)

sounds = []
alphabet = list(reversed(string.ascii_uppercase))
sound_dict = {}
scheme = []
synonym_sounds = {}

for line in lines:
    last_word = line.split(" ")[-1]
    sound = soundex.soundex(f"{last_word}")
    sounds.append(sound)
    for synonym in wordnet.synsets(last_word):
        synonyms = synonym.lemma_names()
        for word in synonyms:
            synonym_sound = soundex.soundex(f"{word}")
            if sound[-3:] == synonym_sound[-3:] and word != last_word:
                if word in synonym_sounds:
                    synonym_sounds[last_word].append(word)
                else:
                    synonym_sounds[last_word] = [word] 
                
print(synonym_sounds)

print(f"Sounds : {sounds}")

for sound in sounds:
    if sound[-3:] in sound_dict:
        print(f"{sound[-3:]} sound is already in dict")
    else:
        sound_dict[sound[-3:]] = alphabet.pop()

for sound in sounds:
    scheme.append(sound_dict[sound[-3:]])

print(f"Rhyme scheme : {scheme}")
