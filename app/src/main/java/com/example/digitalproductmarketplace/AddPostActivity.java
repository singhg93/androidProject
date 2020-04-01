package com.example.digitalproductmarketplace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
    private final String INPUT_TAG = "Input Error";


    // attributes
    private final String AWS_TAG = "AWS ERROR";
    private Toast _myToast;
    Button _chooseFileButton;
    Button _chooseContentFileButton;
    EditText _description;
    Spinner _categories;
    EditText _price;
    Button _createPost;
    TextView _imageFilePath;
    TextView _contentFilePath;
    FilePickerDialog _imageDialog;
    FilePickerDialog _fileDialog;
    String _imageFile;
    String _contentFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        // initialize the views and buttons
        _chooseFileButton = findViewById(R.id.choose_image_file_button);
        _imageFilePath = findViewById(R.id.image_file_path);
        _contentFilePath = findViewById(R.id.content_file_path);
        _chooseContentFileButton = findViewById(R.id.choose_content_file);
        _createPost = findViewById(R.id.create_post);
        _description = findViewById(R.id.description_input);
        _categories = findViewById(R.id.categories_input);
        _price = findViewById(R.id.price_input);

        // start the aws trasfer service
        getApplicationContext().startService(new Intent(getApplicationContext(), TransferService.class));

        // initialize the aws client
        AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {
            @Override
            public void onResult(UserStateDetails userStateDetails) {
                Log.i(AWS_TAG, "AWSMobileClient initialized. User State is " + userStateDetails.getUserState());
            //    uploadWithTransferUtility("hello");
            }

            // if there is an error
            @Override
            public void onError(Exception e) {
                Log.e(AWS_TAG, "Initialization error.", e);
            }
        });




        // REFERENCE : https://github.com/Angads25/android-filepicker

        // initialize the properties for the image choosing dialogbox
        DialogProperties imageDialogProperties = new DialogProperties();

        // set the dialog properties attributes
        imageDialogProperties.selection_mode = DialogConfigs.SINGLE_MODE;
        imageDialogProperties.selection_type = DialogConfigs.FILE_SELECT;
        imageDialogProperties.root = new File(DialogConfigs.DEFAULT_DIR);
        imageDialogProperties.error_dir = new File(DialogConfigs.DEFAULT_DIR);
        imageDialogProperties.offset = new File(DialogConfigs.DEFAULT_DIR);
        String[] imageExtensions = {"jpg","jpeg","png"};
        imageDialogProperties.extensions = imageExtensions;

        // initialize a new dialog box for display image choosing
        _imageDialog = new FilePickerDialog(AddPostActivity.this,imageDialogProperties);
        _imageDialog.setTitle("Select a Image File");

        // initialize the properties for the file choosing dialogbox
        DialogProperties fileDialogProperties = new DialogProperties();

        // set the dialog properties attributes
        fileDialogProperties.selection_mode = DialogConfigs.SINGLE_MODE;
        fileDialogProperties.selection_type = DialogConfigs.FILE_SELECT;
        fileDialogProperties.root = new File(DialogConfigs.DEFAULT_DIR);
        fileDialogProperties.error_dir = new File(DialogConfigs.DEFAULT_DIR);
        fileDialogProperties.offset = new File(DialogConfigs.DEFAULT_DIR);
        fileDialogProperties.extensions = null;


        // initialize a new dialog box for choosing the content file
        _fileDialog = new FilePickerDialog(AddPostActivity.this,fileDialogProperties);
        _fileDialog.setTitle("Select a Content File");

        _imageDialog.setDialogSelectionListener(new DialogSelectionListener() {
            @Override
            public void onSelectedFilePaths(String[] files) {
                //files is the array of the paths of files selected by the Application User.
                // go through all the files, since only single file selection is allowed,
                // only one path will be present in the array
                for (String filePath : files) {
                    Log.e("file Paths", filePath);
                    _imageFile = filePath;
//                    uploadWithTransferUtility(filePath);

                }
            }
        });


        // when the file is selected
        _fileDialog.setDialogSelectionListener(new DialogSelectionListener() {
            @Override
            public void onSelectedFilePaths(String[] files) {
                //files is the array of the paths of files selected by the Application User.
                // go through all the files, since only single file selection is allowed,
                // only one path will be present in the array
                for (String filePath : files) {
                    Log.e("file Paths", filePath);
                    // store the file path in _contentFile variable
                    _contentFile = filePath;
//                    _contentFilePath.setText(filePath);

                }
            }
        });

        // when the user wants to choose a display image for the post
        _chooseFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _imageDialog.show();
            }
        });

        // when the user wants to choose the content files
        _chooseContentFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _fileDialog.show();
            }
        });


        // when the create post button is clicked
        _createPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    // do something
                }
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
        File file = new File(getApplicationContext().getFilesDir(), getUniqueName());
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
    private String getUniqueName() {

        SimpleDateFormat everytimenew=new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        everytimenew.format(new Date());

        //assemble filename

        String  filename="file"+ everytimenew + ".txt";;
        return filename;

    }

    private boolean validateInput() {

        // get the text from the EditText views
        String inputDescription = _description.getText().toString();
        double inputPrice;
        String category = _categories.getSelectedItem().toString();
        try {
            inputPrice = Double.parseDouble(_price.getText().toString());
        } catch ( Exception ex ) {
            _price.setError("Please enter a valid value for price");
            Log.e(INPUT_TAG, ex.getMessage());
            return false;
        }
        // validate all the input
        if (inputDescription.equals("")) {
            _description.setError("Please enter a short description for your product.");
            return false;
        } else if ( inputPrice < 1 ) {
            _price.setError("Please enter a valid value for price.");
            return false;
        } else if ( _imageFile == null || _imageFile == "") {
            _chooseFileButton.setError("Please choose a display image.");
            return false;
        } else if ( _contentFile == null || _contentFile.equals("")) {
            _chooseContentFileButton.setError("Please choose a content file.");
            return false;
        }
        return false;
    }

}
