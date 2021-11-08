line = ["This", "is", "a", "poem"]

print("Input : ", line)

total_syllables = 0

for word in line:
    word = word.lower()
    count = 0
    vowels = "aeiouy"
    if word[0] in vowels:
        count += 1
    for index in range(1, len(word)):
        if word[index] in vowels and word[index - 1] not in vowels:
            count += 1
        if word.endswith("e"):
            count -= 1
        if count == 0:
            count += 1
    total_syllables += count

print(f"Total line syllables : {total_syllables}")
