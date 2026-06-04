package com.example.imagecloud;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.squareup.picasso.Picasso;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.util.Log;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {


    private static int IMAGE_REQ = 1;
    private Uri imagePath;
    private static final String TAG = "Upload ###";

    private ImageView imageView;
    private Button button;
	


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);


        imageView = findViewById(R.id.imageView);
        button = findViewById(R.id.button);
	initConfig();
        /*
        * 1. user want to click the imageview
        * 2. select the image/pdf/video file gallery
        * 3. show the preview of the image in imageView
        * 4. click the upload button to upload image to cloudinary
         */

        //1 user want to click the image
        imageView.setOnClickListener(v -> {
        /*
        * 1. ask the user to give the media permission
        * 2. moving to the gallery
        *
         */

            //1.1
            requestPermission();
        });

	button.setOnClickListener(v -> {
		MediaManager.get().upload(imagePath).callback(new UploadCallback() {
			@Override
			public void onStart(String requestId) {

				Log.d(TAG,"onStart: "+"started");
			}
			
			@Override
			public void onProgress(String requestId, long bytes, long totalBytes) {
				Log.d(TAG,"onStart:"+"uploading");
			}
			@Override
			public void onSuccess(String requestId, Map resultData) {

				Log.d(TAG,"onStart:"+"success");
			}
			@Override
			public void onError(String requestId, ErrorInfo error) {

				Log.d(TAG,"onStart:"+"error");
			}
			@Override
			public void onReschedule(String requestId, ErrorInfo error) {

				Log.d(TAG,"onStart:"+"error");
			}
	}).dispatch();

	});

	
    }

	private void initConfig(){
	Map config = new HashMap();
	
	config.put("cloud_name", "diy5we5ku");
	config.put("api_key", "777886489541779");
	config.put("api_secret", "U5CaxXcSYiqHxdHRvRQvtaq90ME");
	MediaManager.init(this,config);
}
	

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED)
        {
            selectImage();
        }else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, IMAGE_REQ);
        }
    }

        /*
        * select the image from the gallery
        *
         */
    private void selectImage() {
        Intent intent=new Intent();
        intent.setType("image/*"); // if you want you can use pdf/gif/video
        intent.setAction(Intent.ACTION_GET_CONTENT);
   //     startActivityForResult(intent,IMAGE_REQ);
	someActivityResultLauncher.launch(intent);


    }

 //   @Override
 //   protected void onActivityResult(int requestcode,int resultcode,Intent data) {
//        super.onActivityResult(requestcode, resultcode, data);
//
  //      if(requestcode==IMAGE_REQ && resultcode == Activity.RESULT_OK && data != null && data.getData()!=null )
  //      {
  //          imagePath = data.getData();
  //          Picasso.get().load(imagePath).into(imageView);
//        }
//    }

// You can do the assignment inside onAttach or onCreate, i.e, before the activityis displayed
	ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
		new ActivityResultContracts.StartActivityForResult(),
		new ActivityResultCallback<ActivityResult>() {
		@Override
		public void onActivityResult(ActivityResult result) {
			if(result.getResultCode() == Activity.RESULT_OK) {
				//There are no request codes
				Intent data = result.getData();
				imagePath=data.getData();
				Picasso.get().load(imagePath).into(imageView);
			}
		}
	});



}
