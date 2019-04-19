package com.wajdy.imagerecognition.imagerecognition.scenes;

import android.app.Activity;
import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import com.wajdy.imagerecognition.imagerecognition.Classifier;

import java.io.IOException;
import java.util.List;

public class SceneRecognizer {

    private static int INPUT_SIZE;

    private static ContentResolver contentResolver;
    private ImageClassifier classifier;

    public SceneRecognizer(Activity activity,
                           String modelFile,
                           String labelFile,
                           int inputSize,
                           float rescale) throws IOException {

        INPUT_SIZE = inputSize;

        contentResolver = activity.getContentResolver();
        classifier = new ImageClassifier(activity, modelFile, labelFile, inputSize, rescale);
    }

    public List<Classifier.Recognition> recognizeImage(String uri) throws IOException {
        Bitmap bitmap = this.readAndResizeBitmap(uri);
        return classifier.recognizeImage(bitmap);
    }

    private Bitmap readAndResizeBitmap(String imageUri) throws IOException {
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(contentResolver, Uri.parse(imageUri));
        return Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, true);
    }
}
