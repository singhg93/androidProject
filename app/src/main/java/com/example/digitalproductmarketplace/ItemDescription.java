package com.example.digitalproductmarketplace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferNetworkLossHandler;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferService;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;
import com.example.digitalproductmarketplace.boundary.ItemDAO;
import com.example.digitalproductmarketplace.boundary.UserDAO;
import com.example.digitalproductmarketplace.entity.Item;
import com.example.digitalproductmarketplace.entity.User;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class ItemDescription extends AppCompatActivity {

    private final String AWS_TAG = "AWS ERROR";

    ImageView _itemImage;
    Button _buyNow;
    TextView _imageDescription;
    Button _goBack;
    SharedPreferences _sharedPref;
    User _signedInUser;
    UserDAO _userDAO;
    ItemDAO _itemDAO;
    Item _currentItem;
    TextView _priceText;
    TextView _categoryText;
    Toast _myToast;

    private static PayPalConfiguration config = new PayPalConfiguration()
            // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId("AdNM1C0aoiV7nzYMhsZ-TdYZuHAo2CziKwZ-1GDDMuNQqGWMyajWjcXQbTkoxFhYBtrprKykTkDDrGs8");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_description);
        _buyNow = findViewById(R.id.buy_button);
        _imageDescription = findViewById(R.id.description_text);
        _goBack = findViewById(R.id.description_go_back);
        _itemImage = findViewById(R.id.description_image);
        _priceText = findViewById(R.id.details_price);
        _categoryText = findViewById(R.id.category_text);
        _userDAO = new UserDAO(ItemDescription.this);
        _sharedPref = PreferenceManager.getDefaultSharedPreferences(ItemDescription.this);
        _itemDAO = new ItemDAO(ItemDescription.this);





        Intent intent = new Intent(this, PayPalService.class);

        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        startService(intent);

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


        //
        if (_sharedPref.contains("EMAIL")) {
            String userEmail = _sharedPref.getString("EMAIL", "");
            _signedInUser = _userDAO.getUser(userEmail);

        } else {

            Intent loginIntent = new Intent(ItemDescription.this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
        }

        // get the intent and the bundle
        Intent thisIntent = getIntent();
        Bundle myBundle = thisIntent.getExtras();

        long itemId = -1;
        if ( thisIntent != null && myBundle != null ){

            // get the string
            itemId = myBundle.getLong("ITEM_ID", -1 );

        }

        if (itemId > -1) {
            _currentItem = _itemDAO.getItem(itemId);
        } else if (itemId < -1 ) {
            _currentItem = null;
        }

        if (_currentItem == null)
        {
            Intent itemsIntent = new Intent(ItemDescription.this, ItemsActivity.class);
            startActivity(itemsIntent);
        } else {
            downloadImageWithTransferUtility(_currentItem.get_picName());
            Log.e("Pic Name", _currentItem.get_picName());
            _imageDescription.setText(_currentItem.get_description());
            DecimalFormat df = new DecimalFormat("$#.##");
            _priceText.setText("Price:\t\t" + df.format(_currentItem.get_price()));
            _categoryText.setText("Category:\t\t" + _currentItem.get_catagory().toUpperCase());
            Picasso.get().
                    load("https://robohash.org/" + _currentItem.get_picName() + "?set=set3").
                    into(_itemImage);

        }






        _goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goBackProfile = new Intent(ItemDescription.this, ItemsActivity.class);
                startActivity(goBackProfile);
            }
        });

        _buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // PAYMENT_INTENT_SALE will cause the payment to complete immediately.
                // Change PAYMENT_INTENT_SALE to
                //   - PAYMENT_INTENT_AUTHORIZE to only authorize payment and capture funds later.
                //   - PAYMENT_INTENT_ORDER to create a payment for authorization and capture
                //     later via calls from your server.

                PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(_currentItem.get_price())),
                        "CAD", _currentItem.get_description(),
                        PayPalPayment.PAYMENT_INTENT_SALE);

                Intent intent = new Intent(ItemDescription.this, PaymentActivity.class);

                // send the same configuration for restart resiliency
                intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

                intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

                startActivityForResult(intent, 0);


            }
        });



    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null) {
                try {
                    Log.i("paymentExample", confirm.toJSONObject().toString(4));

                    // TODO: send 'confirm' to your server for verification.
                    // see https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
                    // for more details.

                    if (ContextCompat.checkSelfPermission(ItemDescription.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(ItemDescription.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                1999);
                    }
                    else {

                        if (_myToast != null) {
                            _myToast.cancel();
                        }
                        _myToast = Toast.makeText(ItemDescription.this, "Download has started", Toast.LENGTH_LONG);
                        _myToast.show();
                        downloadFileWithTransferUtility(_currentItem.get_fileUrl());

                    }
                } catch (JSONException e) {
                    Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                    if (_myToast != null ) {
                        _myToast.cancel();
                    }
                    _myToast = Toast.makeText(ItemDescription.this, "An error occured while processing the transaciton", Toast.LENGTH_LONG);
                    _myToast.show();
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("paymentExample", "The user canceled.");
        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
        }
    }

    private void downloadImageWithTransferUtility(final String amazonKey) {

        TransferNetworkLossHandler.getInstance(this);

        TransferUtility transferUtility =
                TransferUtility.builder()
                        .context(getApplicationContext())
                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                        .s3Client(new AmazonS3Client(AWSMobileClient.getInstance()))
                        .build();


        TransferObserver downloadObserver =
                transferUtility.download(
                        "orangebucket111948-android",
                        "public/images/" + amazonKey,
                        new File(getApplicationContext().getFilesDir(), amazonKey));

        // Attach a listener to the observer to get state update and progress notifications
        downloadObserver.setTransferListener(new TransferListener() {

            @Override
            public void onStateChanged(int id, TransferState state) {
                if (TransferState.COMPLETED == state) {
                    // Handle a completed upload.

                    Bitmap bmp = null;

                    try {
                        File imageFile = new File(getApplicationContext().getFilesDir(), amazonKey);
                        bmp = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                        _itemImage.setImageBitmap(bmp);
                    } catch ( Exception ex) {
                        Log.e("File Error", "file not found");
                    }
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                float percentDonef = ((float)bytesCurrent/(float)bytesTotal) * 100;
                int percentDone = (int)percentDonef;

                Log.d("Your Activity", "   ID:" + id + "   bytesCurrent: " + bytesCurrent + "   bytesTotal: " + bytesTotal + " " + percentDone + "%");
            }

            @Override
            public void onError(int id, Exception ex) {
                // Handle errors
                Log.e("AWS", ex.getMessage());


            }

        });

        // If you prefer to poll for the data, instead of attaching a
        // listener, check for the state and progress in the observer.
        if (TransferState.COMPLETED == downloadObserver.getState()) {
            // Handle a completed upload.
        }

        Log.d("Your Activity", "Bytes Transferred: " + downloadObserver.getBytesTransferred());
        Log.d("Your Activity", "Bytes Total: " + downloadObserver.getBytesTotal());
    }

    private void downloadFileWithTransferUtility(String amazonKey) {

        TransferUtility transferUtility =
                TransferUtility.builder()
                        .context(getApplicationContext())
                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                        .s3Client(new AmazonS3Client(AWSMobileClient.getInstance()))
                        .build();

        TransferObserver downloadObserver =
                transferUtility.download(
                        "public/content/" + amazonKey,
                        new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), amazonKey));

        // Attach a listener to the observer to get state update and progress notifications
        downloadObserver.setTransferListener(new TransferListener() {

            @Override
            public void onStateChanged(int id, TransferState state) {
                if (TransferState.COMPLETED == state) {
                    // Handle a completed upload.
//                    Toast.makeText(ItemDescription.this, "File Downloaded", Toast.LENGTH_LONG).show();
                    if (_myToast != null) {
                        _myToast.cancel();
                    }
                    _myToast = Toast.makeText(ItemDescription.this, "File is downloaded to the Downloads folder", Toast.LENGTH_LONG);
                    _myToast.show();

                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                float percentDonef = ((float)bytesCurrent/(float)bytesTotal) * 100;
                int percentDone = (int)percentDonef;

                Log.d("Your Activity", "   ID:" + id + "   bytesCurrent: " + bytesCurrent + "   bytesTotal: " + bytesTotal + " " + percentDone + "%");
            }

            @Override
            public void onError(int id, Exception ex) {
                // Handle errors

                Log.e("AWS", ex.getMessage());
                if (_myToast != null ) {
                    _myToast.cancel();
                }
                _myToast = Toast.makeText(ItemDescription.this, "An error occurred. Please enter your card details again you will not be charged.", Toast.LENGTH_LONG);
                _myToast.show();

            }

        });

        // If you prefer to poll for the data, instead of attaching a
        // listener, check for the state and progress in the observer.
        if (TransferState.COMPLETED == downloadObserver.getState()) {
            // Handle a completed upload.
        }


        Log.d("Your Activity", "Bytes Transferred: " + downloadObserver.getBytesTransferred());
        Log.d("Your Activity", "Bytes Total: " + downloadObserver.getBytesTotal());

    }


    @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }


}
