package com.example.digitalproductmarketplace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import java.io.File;

public class AddPostActivity extends AppCompatActivity {

    private final String FILE_TAG = "File Chooser Error";
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

}
