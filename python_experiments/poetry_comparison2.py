import os
from difflib import SequenceMatcher
import matplotlib.pyplot as plt
import Levenshtein
import numpy as np
from statistics import mean

poem_types = ["shakespearean_sonnets", "petrarchan_sonnets", "general"]

for poem_type in poem_types:

    algorithms = ["soundex", "doublemetaphone", "metaphone", "daitchmokotoffsoundex"]
    algos = {}
    names = []

    for algo in algorithms:
        algos[algo] = list()
    for root, dirs, files in os.walk("../generated/" + poem_type):
        for file in files:
            if file.endswith(".man") or file.endswith(".soundex") or file.endswith(".metaphone") or file.endswith(
                    ".doublemetaphone") or file.endswith(".daitchmokotoffsoundex"):
                pass
            else:
                if file not in names:
                    names.append(file)
                # Open both manual and auto labeled files
                with open(os.path.join(root, file), "r") as poem:
                    with open(os.path.join(root, file + ".man"), "r") as poem_man_lab:
                        man_rhyme_scheme = poem_man_lab.read()

                        for algo in algorithms:
                            sequence_matcher = {}
                            levenshtein = {}
                            with open(os.path.join(root, file + "." + algo), "r") as poem_aut_lab:
                                print("Poem : " + file)

                                aut_rhyme_scheme = poem_aut_lab.read()
                                print("Manual label : " + man_rhyme_scheme)
                                print("Auto label : " + aut_rhyme_scheme)

                                # Use SequenceMatcher to calculate distance between 2 matchs
                                ratio = SequenceMatcher(None, man_rhyme_scheme, aut_rhyme_scheme).ratio()
                                optional_ratio = Levenshtein.ratio(man_rhyme_scheme, aut_rhyme_scheme)
                                print("Ratio determined : " + str(ratio))
                                print("Optional ratio : " + str(optional_ratio))
                                print("Algo : " + algo)
                                algos[algo].append(round(optional_ratio * 100, 0))
                                sequence_matcher[file] = ratio
                                levenshtein[file] = optional_ratio
                            sequence_matcher_names = list(sequence_matcher.keys())
                            sequence_matcher_values = list(sequence_matcher.values())
                            levenshtein_names = list(levenshtein.keys())
                            levenshtein_values = list(levenshtein.values())

                            print(levenshtein_values)
    print(algos)
    for algo in algorithms:
        avg = mean(algos[algo])
        print("Algo : " + algo + " " + str(round(avg, 0)) + "%")
    print(names)

    for algo, avg in algos.items():
        plt.figure(figsize=(10, 10))
        plt.bar(names, avg, label=algo, color="#304ffe")
        plt.title("Accuracy of the " + algo.capitalize() + " algorithm for " + poem_type.capitalize())
        plt.xticks(rotation=45)
        plt.xlabel("Poem Name")
        plt.ylabel("Accuracy (%)")
        plt.ylim((0, 100))
        plt.show()
