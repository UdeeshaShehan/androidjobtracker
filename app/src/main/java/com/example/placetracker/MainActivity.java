package com.example.placetracker;

import android.annotation.TargetApi;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.placetracker.adapter.PlaceSelfieAdapter;
import com.example.placetracker.domain.PlaceSelfie;
import com.example.placetracker.domain.PlaceSelfieRest;
import com.example.placetracker.retrofit.SelfieApiClient;
import com.example.placetracker.retrofit.SelfieApiInterface;
import com.example.placetracker.services.LocationService;
import com.example.placetracker.utility.Commonutility;
import com.example.placetracker.utility.CurrentJob;
import com.example.placetracker.utility.SessionObject;
import com.example.placetracker.utility.Utility;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    //private ArrayList<PlaceSelfie> imageArry;

    private static int REQUEST_CAMERA = 0, SELECT_FILE = 1, REQUEST_CAMERA_LAST = 2;
    /*private FloatingActionButton fab1;*/
    private FloatingActionButton fab_main, fab1, fab2;
    private Animation fab_open, fab_close, fab_clock, fab_anticlock;
    private ImageView ivImage;
    private String userChoosenTask;
    private Location location;

    protected LocationManager locationManager;
    protected LocationListener locationListener;

    private PlaceSelfie selfie;
    PlaceSelfieAdapter imageAdapter;

    //ListView dataList;
    byte[] imageName;
    int imageId;
    Bitmap theImage;
    //DataBaseHandler db;

    private ArrayList<String> permissionsToRequest;
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList<String> permissions = new ArrayList();

    private final static int ALL_PERMISSIONS_RESULT = 101;
    LocationService locationTrack;

    TextView textview_mail, textview_share;

    Boolean isOpen = false;

    private View popupInputDialogView = null;
    private EditText jobNameEditText = null;
    private EditText jobDescriptionEditText = null;
    private Button saveUserDataButton = null;
    private Button cancelUserDataButton = null;

    SelfieApiInterface selfieApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CurrentJob.getInstance().setMainActivity(this);
        selfieApiInterface = SelfieApiClient.getClient(SessionObject.getInstance().getToken()).create(SelfieApiInterface.class);
        //dataList = findViewById(R.id.list);
        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //db = new DataBaseHandler(this);
        final ArrayList<PlaceSelfieRest> placeSelfies = new ArrayList<>();

        try {
            //db.deleteAll();
            selfieApiInterface.getplaceSelfiesByEmail(
                    SessionObject.getInstance().getEmail()).enqueue(new Callback<List<PlaceSelfieRest>>() {
                @Override
                public void onResponse(Call<List<PlaceSelfieRest>> call, Response<List<PlaceSelfieRest>> response) {
                    List<PlaceSelfieRest> list = response.body();
                    placeSelfies.addAll(list);
                    adapter = new PlaceSelfieAdapter(placeSelfies);
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onFailure(Call<List<PlaceSelfieRest>> call, Throwable t) {

                }
            });
            //placeSelfies = db.getAllPlaceSelfies();
        } catch (Exception e) {
            try {
               // db.onCreate(db.getWritableDatabase());
                //placeSelfies = db.getAllPlaceSelfies();
            } catch (Exception e1) {

            }
        }


        fab_main = findViewById(R.id.fab);
        fab1 = findViewById(R.id.fab1);
        fab2 = findViewById(R.id.fab2);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_clock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_clock);
        fab_anticlock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_anticlock);

        textview_mail = findViewById(R.id.textview_mail);
        textview_share = findViewById(R.id.textview_share);

        fab_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isOpen) {

                    textview_mail.setVisibility(View.INVISIBLE);
                    textview_share.setVisibility(View.INVISIBLE);
                    fab2.startAnimation(fab_close);
                    fab1.startAnimation(fab_close);
                    fab_main.startAnimation(fab_anticlock);
                    fab2.setClickable(false);
                    fab1.setClickable(false);
                    isOpen = false;
                } else {
                    textview_mail.setVisibility(View.VISIBLE);
                    textview_share.setVisibility(View.VISIBLE);
                    fab2.startAnimation(fab_open);
                    fab1.startAnimation(fab_open);
                    fab_main.startAnimation(fab_clock);
                    fab2.setClickable(true);
                    fab1.setClickable(true);
                    isOpen = true;
                }

            }
        });


        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setTitle("User Data Collection Dialog.");
                alertDialogBuilder.setIcon(R.drawable.ic_launcher_background);
                alertDialogBuilder.setCancelable(false);
                initPopupViewControls();
                alertDialogBuilder.setView(popupInputDialogView);
                final AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                saveUserDataButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // Get user data from popup dialog editeext.
                        String jobName = jobNameEditText.getText().toString();
                        String jobDescription = jobDescriptionEditText.getText().toString();

                        PlaceSelfieRest placeSelfie = new PlaceSelfieRest(null,
                                null, 0.0,
                                0.0, 0.0, 0.0, null,
                                null, jobName, 1, jobDescription, new Date(),
                                SessionObject.getInstance().getUsername(), SessionObject.getInstance().getEmail());
                        addPlaceSelfieToRest(placeSelfie);
                        CurrentJob.getInstance().setPlaceSelfie(placeSelfie);
                        alertDialog.cancel();
                        /*Intent i = new Intent(MainActivity.this,
                                MainActivity.class);
                        startActivity(i);*/
                        finish();
                        startActivity(getIntent());
                    }
                });

                cancelUserDataButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.cancel();
                    }
                });

            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Refresh jobs", Toast.LENGTH_SHORT).show();
                try {
                    //db.deleteAll();
                    selfieApiInterface.getplaceSelfiesByEmail(
                            SessionObject.getInstance().getEmail()).enqueue(new Callback<List<PlaceSelfieRest>>() {
                        @Override
                        public void onResponse(Call<List<PlaceSelfieRest>> call, Response<List<PlaceSelfieRest>> response) {
                            placeSelfies.clear();
                            List<PlaceSelfieRest> list = response.body();
                            placeSelfies.addAll(list);
                            adapter = new PlaceSelfieAdapter(placeSelfies);
                            recyclerView.setAdapter(adapter);
                        }

                        @Override
                        public void onFailure(Call<List<PlaceSelfieRest>> call, Throwable t) {

                        }
                    });
                    //placeSelfies = db.getAllPlaceSelfies();
                } catch (Exception e) {
                    try {
                        // db.onCreate(db.getWritableDatabase());
                        //placeSelfies = db.getAllPlaceSelfies();
                    } catch (Exception e1) {

                    }
                }
                //onClickFloatingButton();
            }
        });

        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);

        permissionsToRequest = findUnAskedPermissions(permissions);
        //get the permissions we have asked for before but are not granted..
        //we will store this in a global list to access later
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }

        locationTrack = new LocationService(MainActivity.this);
    }

    private void addPlaceSelfieToRest(PlaceSelfieRest placeSelfie) {
        Call<PlaceSelfieRest> postCall = selfieApiInterface.selfieAdd(
                //"Bearer " + SessionObject.getInstance().getToken() ,
                placeSelfie
                );
        //"Bearer " +
        postCall.enqueue(new Callback<PlaceSelfieRest>() {
            @Override
            public void onResponse(Call<PlaceSelfieRest> call, final Response<PlaceSelfieRest> response) {
                Log.e(TAG, "onResponse: " + response.body() );
                Toast.makeText(getBaseContext(), "Added", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<PlaceSelfieRest> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getLocalizedMessage() );
                Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

            }
        });
    }

    private void updatePlaceSelfieToRest(PlaceSelfieRest placeSelfie) {
        Call<PlaceSelfieRest> putCall = selfieApiInterface.updateSelfie(placeSelfie.getId(),
                //"Bearer " + SessionObject.getInstance().getToken() ,
                placeSelfie
        );
        //"Bearer " +
        putCall.enqueue(new Callback<PlaceSelfieRest>() {
            @Override
            public void onResponse(Call<PlaceSelfieRest> call, final Response<PlaceSelfieRest> response) {
                Log.e(TAG, "onResponse: " + response.body() );
                Toast.makeText(getBaseContext(), "Added", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<PlaceSelfieRest> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getLocalizedMessage() );
                Toast.makeText(getBaseContext(), "update" + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    private void deletePlaceSelfieToRest(PlaceSelfieRest placeSelfie) {
        Call<Map<String, Boolean>> putCall = selfieApiInterface.deleteSelfie(placeSelfie.getId()
                //"Bearer " + SessionObject.getInstance().getToken() ,

        );
        //"Bearer " +
        putCall.enqueue(new Callback<Map<String, Boolean>>() {
            @Override
            public void onResponse(Call<Map<String, Boolean>> call, Response<Map<String, Boolean>> response) {
                Log.e(TAG, "onResponse: " + response.body() );
                Toast.makeText(getBaseContext(), "Added", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Map<String, Boolean>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getLocalizedMessage() );
                Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

            }
        });
    }

    private void initPopupViewControls()
    {
        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        popupInputDialogView = layoutInflater.inflate(R.layout.job_form, null);
        jobNameEditText =  popupInputDialogView.findViewById(R.id.jobname);
        jobDescriptionEditText =  popupInputDialogView.findViewById(R.id.jobdescription);
        saveUserDataButton = popupInputDialogView.findViewById(R.id.button_save_user_data);
        cancelUserDataButton = popupInputDialogView.findViewById(R.id.button_cancel_user_data);

    }

    private ArrayList findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList result = new ArrayList();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    public void clickAction() {
        final CharSequence[] items = { "Take Selfie", "Take End Selfie", "Delete Item", "View", "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Click Actions!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= Utility.checkPermission(MainActivity.this);
                if (items[item].equals("Take Selfie")) {
                    userChoosenTask ="Take Selfie";
                    if(result)
                        cameraIntent();

                } else if (items[item].equals("Take End Selfie")) {
                    //userChoosenTask ="Take End Selfie";
                    if(result)
                        lastCameraIntent();

                } else if (items[item].equals("Delete Item")) {
                    //userChoosenTask ="Delete Item";
                    if(result)
                        removeItem();

                } else if (items[item].equals("View")) {
                    //userChoosenTask ="Delete Item";
                    if(result) {
                        Intent intent = new Intent(MainActivity.this, DisplayImageActivity.class);
                        intent.putExtra("selfie", CurrentJob.getInstance().getPlaceSelfie());
                        startActivity(intent);
                    }

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void removeItem() {
        //db.deleteSelfie(CurrentJob.getInstance().getPlaceSelfie());
        deletePlaceSelfieToRest(CurrentJob.getInstance().getPlaceSelfie());
        finish();
        startActivity(getIntent());
        /*Intent i = new Intent(MainActivity.this,
                MainActivity.class);
        startActivity(i);
        finish();*/
    }

    private void lastCameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA_LAST);
    }

    public PlaceSelfie getSelfie() {
        return selfie;
    }

    public void setSelfie(PlaceSelfie selfie) {
        this.selfie = selfie;
    }

    /*public void removeItem1() {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(MainActivity.this)
                // set message, title, and icon
                .setTitle("Delete")
                .setMessage("Do you want to Delete")
                .setIcon(R.drawable.delete)

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        db.deleteSelfie(myOnClickListener.selfie);
                        dialog.dismiss();
                    }

                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();
        myQuittingDialogBox.show();
    }*/

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Selfie"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale((String) permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions((String[]) permissionsRejected.toArray(
                                                        new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }
    }

    private void onClickFloatingButton() {
        final CharSequence[] items = { "Take Selfie",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Take First Selfie!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= Utility.checkPermission(MainActivity.this);

                if (items[item].equals("Take Selfie")) {
                    userChoosenTask ="Take Selfie";
                    if(result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask ="Choose from Library";
                    if(result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }



    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    private  void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void buildAlertMessageNoGps() {

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
            else if (requestCode == REQUEST_CAMERA_LAST)
                onCaptureImageResultLast(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap yourImage = (Bitmap) data.getExtras().get("data");
        // convert bitmap to byte
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        //yourImage.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        yourImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte imageInByte[] = stream.toByteArray();
        Log.e("output bfr conversion", imageInByte.toString());
        // Inserting Contacts
        Log.d("Insert: ", "Inserting ..");



        double longitude = 0.0;
        double latitude = 0.0;

        if (locationTrack.canGetLocation()) {

            longitude = locationTrack.getLongitude();
            latitude = locationTrack.getLatitude();

            Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) +
                    "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();
        } else {

            locationTrack.showSettingsAlert();
        }
        PlaceSelfieRest placeSelfie = CurrentJob.getInstance().getPlaceSelfie();
        placeSelfie.setFirstSelfie(Commonutility.encodeImage(imageInByte));
        placeSelfie.setLatitude1(latitude);
        placeSelfie.setLongitude1(longitude);
        placeSelfie.setFirstSelfieDate(new Date());
        updatePlaceSelfieToRest(placeSelfie);
        //db.updateSelfie(placeSelfie);
        finish();
        startActivity(getIntent());
        /*Intent i = new Intent(MainActivity.this,
                MainActivity.class);
        startActivity(i);
        finish();*/
        //ivImage.setImageBitmap(thumbnail);
    }

    private void onCaptureImageResultLast(Intent data) {
        Bitmap yourImage = (Bitmap) data.getExtras().get("data");
        // convert bitmap to byte
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        //yourImage.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        yourImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte imageInByte[] = stream.toByteArray();
        PlaceSelfieRest placeSelfie = CurrentJob.getInstance().getPlaceSelfie();
        placeSelfie.setLastSelfie(Commonutility.encodeImage(imageInByte));

        double longitude = 0.0;
        double latitude = 0.0;

        if (locationTrack.canGetLocation()) {

            longitude = locationTrack.getLongitude();
            latitude = locationTrack.getLatitude();

            Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) +
                    "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();
        } else {

            locationTrack.showSettingsAlert();
        }

        placeSelfie.setLatitude2(latitude);
        placeSelfie.setLongitude2(longitude);
        placeSelfie.setLastSelfieDate(new Date());
        updatePlaceSelfieToRest(placeSelfie);
        //db.updateSelfie(placeSelfie);
        /*Intent i = new Intent(MainActivity.this,
                MainActivity.class);
        startActivity(i);
        finish();*/
        finish();
        startActivity(getIntent());
    }


    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ivImage.setImageBitmap(bm);
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationTrack != null)
        locationTrack.stopListener();
    }
}
