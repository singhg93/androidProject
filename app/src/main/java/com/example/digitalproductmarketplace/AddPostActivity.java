package com.example.digitalproductmarketplace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferService;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import java.text.SimpleDateFormat;
import java.util.Date;




public class AddPostActivity extends AppCompatActivity {

    private final String FILE_TAG = "File Chooser Error";


    private final String AWS_TAG = "AWS ERROR";
    private Toast _myToast;
    Button _chooseFileButton;
    Button _chooseContentFileButton;
    TextView _imageFilePath;
    TextView _contentFilePath;
    FilePickerDialog _imageDialog;
    FilePickerDialog _fileDialog;

    // just for testing

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        getApplicationContext().startService(new Intent(getApplicationContext(), TransferService.class));

        AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {
            @Override
            public void onResult(UserStateDetails userStateDetails) {
                Log.i(AWS_TAG, "AWSMobileClient initialized. User State is " + userStateDetails.getUserState());
            //    uploadWithTransferUtility("hello");
            }

            @Override
            public void onError(Exception e) {
                Log.e(AWS_TAG, "Initialization error.", e);
            }
        });


        _chooseFileButton = findViewById(R.id.choose_image_file_button);
        _imageFilePath = findViewById(R.id.image_file_path);
        _contentFilePath = findViewById(R.id.content_file_path);
        _chooseContentFileButton = findViewById(R.id.choose_content_file);






        // REFERENCE : https://github.com/Angads25/android-filepicker

        DialogProperties properties = new DialogProperties();

        properties.selection_mode = DialogConfigs.SINGLE_MODE;
        properties.selection_type = DialogConfigs.FILE_SELECT;
        properties.root = new File(DialogConfigs.DEFAULT_DIR);
        properties.error_dir = new File(DialogConfigs.DEFAULT_DIR);
        properties.offset = new File(DialogConfigs.DEFAULT_DIR);
        properties.extensions = null;

        _imageDialog = new FilePickerDialog(AddPostActivity.this,properties);
        _imageDialog.setTitle("Select a Image File");

        _fileDialog = new FilePickerDialog(AddPostActivity.this,properties);
        _fileDialog.setTitle("Select a Content File");

        _imageDialog.setDialogSelectionListener(new DialogSelectionListener() {
            @Override
            public void onSelectedFilePaths(String[] files) {
                //files is the array of the paths of files selected by the Application User.
                for (String filePath : files) {
                    Log.e("file Paths", filePath);
                    _imageFilePath.setText(filePath);
                    uploadWithTransferUtility(filePath);

                }
            }
        });

        _fileDialog.setDialogSelectionListener(new DialogSelectionListener() {
            @Override
            public void onSelectedFilePaths(String[] files) {
                //files is the array of the paths of files selected by the Application User.
                for (String filePath : files) {
                    Log.e("file Paths", filePath);
                    _contentFilePath.setText(filePath);

                }
            }
        });

        _chooseFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _imageDialog.show();
            }
        });

        _chooseContentFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _fileDialog.show();
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case FilePickerDialog.EXTERNAL_READ_PERMISSION_GRANT: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(_imageDialog !=null)
                    {   //Show dialog if the read permission has been granted.
                        _imageDialog.show();
                    }
                }
                else {
                    if (_myToast !=  null) {
                        _myToast.cancel();
                    } else {
                        //Permission has not been granted. Notify the user.
                        _myToast = Toast.makeText(AddPostActivity.this, "Permission is Required for getting list of files", Toast.LENGTH_SHORT);
                        _myToast.show();
                    }
                }
            }
        }
    }

//AWS_S3
    public void uploadWithTransferUtility( String filePath ) {

        TransferUtility transferUtility =
                TransferUtility.builder()
                        .context(getApplicationContext())
                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                        .s3Client(new AmazonS3Client(AWSMobileClient.getInstance()))
                        .build();
// get file with unique name;
        File file = new File(getApplicationContext().getFilesDir(), "sample.txt");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.append("Howdy World!");
            writer.close();
        }
        catch(Exception e) {
            Log.e(AWS_TAG, e.getMessage());
        }




        TransferObserver uploadObserver =
                transferUtility.upload(
                        "public/sample.txt",
                        new File(filePath));

        // Attach a listener to the observer to get state update and progress notifications
        uploadObserver.setTransferListener(new TransferListener() {



            @Override
            public void onStateChanged(int id, TransferState state) {
                if (TransferState.COMPLETED == state) {
                    // Handle a completed upload.
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                int percentDone = (int)percentDonef;

                Log.d(AWS_TAG, "ID:" + id + " bytesCurrent: " + bytesCurrent
                        + " bytesTotal: " + bytesTotal + " " + percentDone + "%");
            }

            @Override
            public void onError(int id, Exception ex) {
                // Handle errors
            }

        });

        // If you prefer to poll for the data, instead of attaching a
        // listener, check for the state and progress in the observer.
        if (TransferState.COMPLETED == uploadObserver.getState()) {
            // Handle a completed upload.
        }

        Log.d(AWS_TAG, "Bytes Transferred: " + uploadObserver.getBytesTransferred());
        Log.d(AWS_TAG, "Bytes Total: " + uploadObserver.getBytesTotal());
    }




    // we are using date function because every time date with its time cannot be same;


}
