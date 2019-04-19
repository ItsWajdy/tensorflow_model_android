package com.wajdy.imagerecognition;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.wajdy.imagerecognition.imagerecognition.Classifier;
import com.wajdy.imagerecognition.imagerecognition.objects.ObjectRecognizer;
import com.wajdy.imagerecognition.imagerecognition.scenes.SceneRecognizer;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView[] textViews = new TextView[3];
        textViews[0] = findViewById(R.id.text1);
        textViews[1] = findViewById(R.id.text2);
        textViews[2] = findViewById(R.id.text3);

        String[] imgs = new String[3];
        imgs[0] = "file:///storage/extSdCard/DCIM/Scene Tests/1.jpg";
        imgs[1] = "file:///storage/extSdCard/DCIM/Scene Tests/2.jpg";
        imgs[2] = "file:///storage/extSdCard/DCIM/Camera/1.jpg";

        try {
            ObjectRecognizer objectRecognizer = new ObjectRecognizer(MainActivity.this,
                    "mobilenet_ssd_v1.tflite",
                    "coco_labels_list.txt",
                    300,
                    0.6f,
                    true);
            SceneRecognizer sceneRecognizer = new SceneRecognizer(MainActivity.this,
                    "resnet50.tflite",
                    "scenes.txt",
                    256,
                    1);

            // Run for each image
            for (int i = 0; i < 3; i++) {
                // Get list of objects and list of predicted scenes
                List<Classifier.Recognition> objects = objectRecognizer.recognizeImage(imgs[i]);
                List<Classifier.Recognition> scenes = sceneRecognizer.recognizeImage(imgs[i]);

                // Show results on text views
                StringBuilder sb = new StringBuilder();

                for (int j = 0; j < objects.size(); j++)
                    sb.append(objects.get(j).getTitle() + " " + objects.get(j).getConfidence() + "\n");
                for (int j = 0; j < scenes.size(); j++)
                    sb.append(scenes.get(j).getTitle() + " " + scenes.get(j).getConfidence() + "\n");

                textViews[i].setText(sb.toString());
            }

        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }
}
