import os
from difflib import SequenceMatcher
import matplotlib.pyplot as plt
import Levenshtein
import numpy as np

sequence_matcher = {}
levenshtein = {}
for root, dirs, files in os.walk("../poetry"):
    for file in files:
        if file.endswith(".man") or file.endswith(".aut"):
            pass
        else:
            # Open both manual and auto labeled files
            with open(os.path.join(root, file), "r") as poem:
                with open(os.path.join(root, file + ".man"), "r") as poem_man_lab:
                    with open(os.path.join(root, file + ".aut"), "r") as poem_aut_lab:
                        print("Poem : " + file)
                        man_rhyme_scheme = poem_man_lab.read()
                        aut_rhyme_scheme = poem_aut_lab.read()
                        print("Manual label : " + man_rhyme_scheme)
                        print("Auto label : " + aut_rhyme_scheme)

                        # Use SequenceMatcher to calculate distance between 2 matchs
                        ratio = SequenceMatcher(None, man_rhyme_scheme, aut_rhyme_scheme).ratio()
                        optional_ratio = Levenshtein.ratio(man_rhyme_scheme, aut_rhyme_scheme)
                        print("Ratio determined : " + str(ratio))
                        print("Optional ratio : " + str(optional_ratio))
                        sequence_matcher[file] = ratio
                        levenshtein[file] = optional_ratio
sequence_matcher_names = list(sequence_matcher.keys())
sequence_matcher_values = list(sequence_matcher.values())
levenshtein_names = list(levenshtein.keys())
levenshtein_values = list(levenshtein.values())

print(levenshtein_values)

print("Soundex = " + "[0.9411764705882354, 0.3265306122448979, 0.5853658536585367, 0.41379310344827586, 0.903225806451613, 0.6451612903225806]")
print("Metaphone = " + "[0.4705882352941177, 0.9795918367346939, 0.5853658536585367, 0.48275862068965514, 0.7096774193548387, 0.7741935483870968]")
print("Soundex and Metaphone = " + "[0.4705882352941177, 0.3265306122448979, 0.5853658536585367, 0.41379310344827586, 0.903225806451613, 0.6451612903225806]")
print("Soundex or Metaphone = " + "[0.9411764705882354, 0.9795918367346939, 0.6341463414634146, 0.48275862068965514, 0.7096774193548387, 0.7741935483870968]")

soundex_values = [0.9411764705882354, 0.3265306122448979, 0.5853658536585367, 0.41379310344827586, 0.903225806451613, 0.6451612903225806]
metaphone_values = [0.4705882352941177, 0.9795918367346939, 0.5853658536585367, 0.48275862068965514, 0.7096774193548387, 0.7741935483870968]
soundex_and_metaphone_values = [0.4705882352941177, 0.3265306122448979, 0.5853658536585367, 0.41379310344827586, 0.903225806451613, 0.6451612903225806]
soundex_or_metaphone_values = [0.9411764705882354, 0.9795918367346939, 0.6341463414634146, 0.48275862068965514, 0.7096774193548387, 0.7741935483870968]

# plt.bar(range(len(sequence_matcher)), soundex_values, tick_label=sequence_matcher_names)
# plt.bar(range(len(levenshtein)), metaphone_values, tick_label=levenshtein_names)
# plt.show()

# Numbers of pairs of bars you want
N = len(soundex_values)

# Data on X-axis

# Specify the values of blue bars (height)
blue_bar = soundex_values
# Specify the values of orange bars (height)
orange_bar = metaphone_values

red_bar = soundex_and_metaphone_values

yellow_bar = soundex_and_metaphone_values

# Position of bars on x-axis
ind = np.arange(N)

# Figure size
plt.figure(figsize=(15,5))

# Width of a bar
width = 0.2

# Plotting
plt.bar(ind, blue_bar , width, label='Soundex')
plt.bar(ind + width, orange_bar, width, label='Metaphone')
plt.bar(ind + width + width, red_bar, width, label='Soundex and Metaphone')
plt.bar(ind + width + width + width, yellow_bar, width, label='Soundex or Metaphone')

plt.xlabel('Poem Name')
plt.ylabel('Rhyme Scheme Accuracy')
plt.title('Accuracy of generate poem rhyme schemes')

# xticks()
# First argument - A list of positions at which ticks should be placed
# Second argument -  A list of labels to place at the given locations
plt.xticks(ind + width + width + width / 4, levenshtein_names)

# Finding the best position for legends and putting it
plt.legend(loc='best')
plt.show()