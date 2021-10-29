import jellyfish as soundex
import string

lines = ["This is a poem for you", "that rhymes with two", "and has no meaning", "and is definatly leaning"]

sounds = []
alphabet = list(reversed(string.ascii_uppercase))
sound_dict = {}
scheme = []

for line in lines:
    last_word = line.split(" ")[-1]
    sounds.append(soundex.soundex(f"{last_word}"))

print(f"Sounds : {sounds}")

for sound in sounds:
    if sound[-3:] in sound_dict:
        print(f"{sound[-3:]} sound is already in dict")
    else:
        sound_dict[sound[-3:]] = alphabet.pop()

for sound in sounds:
    scheme.append(sound_dict[sound[-3:]])

print(f"Rhyme scheme : {scheme}")
