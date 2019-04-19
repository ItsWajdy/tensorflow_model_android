package com.wajdy.imagerecognition.imagerecognition.objects;

import android.app.Activity;
import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.net.Uri;
import android.provider.MediaStore;

import com.wajdy.imagerecognition.imagerecognition.Classifier;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ObjectRecognizer {

    private static int INPUT_SIZE;
    private static float MINIMUM_CONFIDENCE;

    private static ContentResolver contentResolver;
    private Classifier classifier;

    public ObjectRecognizer(Activity activity,
                            String modelFile,
                            String labelFile,
                            int inputSize,
                            float minConfidence,
                            boolean isQuantized) throws IOException {

        INPUT_SIZE = inputSize;
        MINIMUM_CONFIDENCE = minConfidence;

        contentResolver = activity.getContentResolver();
        classifier =
                TFLiteObjectDetectionAPIModel.create(
                        activity.getAssets(),
                        modelFile,
                        labelFile,
                        INPUT_SIZE,
                        isQuantized);
    }

    public List<Classifier.Recognition> recognizeImage(String uri) throws IOException {
        Bitmap bitmap = this.readAndResizeBitmap(uri);
        List<Classifier.Recognition> results = classifier.recognizeImage(bitmap);

        final List<Classifier.Recognition> mappedRecognitions =
                new LinkedList<Classifier.Recognition>();

        for (final Classifier.Recognition result : results) {
            final RectF location = result.getLocation();
            if (location != null && result.getConfidence() >= MINIMUM_CONFIDENCE) {
                result.setLocation(location);
                mappedRecognitions.add(result);
            }
        }

        return mappedRecognitions;
    }

    private Bitmap readAndResizeBitmap(String imageUri) throws IOException {
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(contentResolver, Uri.parse(imageUri));
        return Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, true);
    }
}
