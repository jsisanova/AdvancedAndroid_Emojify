package com.example.android.emojify;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

class Emojifier {

    private static final String LOG_TAG = Emojifier.class.getSimpleName();

    /**
     * Method for detecting faces in a bitmap.
     *
     * @param context The application context.
     * @param picture The picture in which to detect the faces.
     */
    static void detectFaces(Context context, Bitmap picture) {

        // Create the face detector using builder pattern, disable tracking and enable classifications
        // The FaceDetector.Builder specifies the properties of the face detector and initiates its creation
        FaceDetector detector = new FaceDetector.Builder(context)
                // Disable tracking - for detection for unrelated individual images
                // (as opposed to video or a series of consecutively captured still images), since this will give a more accurate result.
                // Improve performance by disabling tracking (which maintains an ID between consecutive frames if the same face exists in both of them)
                .setTrackingEnabled(false)
                // turn on Classifications
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .build();

        // Create a frame from the bitmap and run face detection on the frame.
        // Build the frame
        // Given a bitmap, we can create Frame instance from the bitmap to supply to the detector
        Frame frame = new Frame.Builder().setBitmap(picture).build();

        // Detect the faces
        // The detector can be called synchronously with a frame to detect faces
        SparseArray<Face> faces = detector.detect(frame);

        // Log the number of faces
        Log.d(LOG_TAG, "detectFaces: number of faces = " + faces.size());
        Toast.makeText(context,  faces.size() + " faces detected", Toast.LENGTH_SHORT).show();


        // If there are no faces detected, show a Toast message
        if(faces.size() == 0){
            Toast.makeText(context, R.string.no_faces_message, Toast.LENGTH_SHORT).show();
        }

        // Release the detector
        // Although detector may be used multiple times for different images, it should be released
        // when it is no longer needed in order to free native resources.
        detector.release();
    }
}