# tensorflow_model_example

## Objective
This repository is just to demonstrate what classes are needed to run a tensorflow CNN model (converted to .rflite) on an Android device.
This repository is only for safekeeping and is not used itself as a seperate app.

## Important Classes
The main class dealing with the tensorflow model is ImageClassifier.java which takes a Bitmap as input and runs an interpreter through
a CNN float (as opposed to a quantized) model to get output
