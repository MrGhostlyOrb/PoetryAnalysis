import os
from difflib import SequenceMatcher
import matplotlib.pyplot as plt
import Levenshtein

results = {}
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
                        print("Manual label : " + poem_man_lab.read())
                        print("Auto label : " + poem_aut_lab.read())
                        man_rhyme_scheme = poem_man_lab.read()
                        aut_rhyme_scheme = poem_aut_lab.read()

                        # Use SequenceMatcher to calculate distance between 2 matchs
                        ratio = SequenceMatcher(None, man_rhyme_scheme, aut_rhyme_scheme).ratio()
                        optional_ratio = Levenshtein.ratio(man_rhyme_scheme, aut_rhyme_scheme)
                        print("Ratio determined : " + ratio)
                        print("Optional ratio : " + optional_ratio)
                        results[file] = ratio
plt.plot(results)
plt.show()

