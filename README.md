# Algorithm:

Given a content video, find all matching pre-rolls. Pre-roll matching takes country, language and aspect ratio into account while finding the match.

In case multiple pre-rolls exist for a specific video, we generate all ordered subsets of the pre-rolls that can be played before the video. This is an assumption based on the spec.

# How to run:

run.sh


# Testing:

## ContentDbFunctionalTest:
Takes the input from the contents.json provided with the spec and ensures it passes all attached test cases.
In addition to that, there are also tests that verify multiple pre-rolls before the content.



