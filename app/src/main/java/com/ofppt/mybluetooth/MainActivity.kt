package com.ofppt.mybluetooth

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import java.security.Permission


class MainActivity : AppCompatActivity() {

    private val REQUEST_ENABLE_BT = 0
    private val REQUEST_DISCOVERABLE_BT = 0


    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

          var out =   findViewById<View>(R.id.out);
        var button1 =  findViewById<View>(R.id.button1);
        var button2 =   findViewById<View>(R.id.button2);
        var button3 =  findViewById<View>(R.id.button3);


        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        var mBluetoothAdapter = bluetoothManager.adapter


        button1.setOnClickListener(View.OnClickListener() {
            if (!mBluetoothAdapter.isEnabled()) {

                Dexter.withContext(this@MainActivity).withPermission(   Manifest.permission.BLUETOOTH_CONNECT)
                    .withListener(
                        object: PermissionListener {
                            @SuppressLint("MissingPermission")
                            override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                                val enableBtIntent =  Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);

                            }

                            override fun onPermissionDenied(p0: PermissionDeniedResponse?) {

                             if(p0?.isPermanentlyDenied == true)
                                 Log.d("MainActivity","Permission denied")
                            }

                            override fun onPermissionRationaleShouldBeShown(
                                p0: PermissionRequest?,
                                p1: PermissionToken?
                            ) {
                               p1?.continuePermissionRequest()
                            }

                        }
                    ).check()



            }
        });
        button2.setOnClickListener(View.OnClickListener() {


            Dexter.withContext(this@MainActivity).withPermission(   Manifest.permission.BLUETOOTH_SCAN)
                .withListener(
                    object: PermissionListener {
                        @SuppressLint("MissingPermission")
                        override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                            if (!mBluetoothAdapter.isDiscovering()) {
                                //out.append("MAKING YOUR DEVICE DISCOVERABLE");
                                Toast.makeText(
                                    getApplicationContext(), "MAKING YOUR DEVICE DISCOVERABLE",
                                    Toast.LENGTH_LONG
                                );

                                val enableBtIntent =  Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                                startActivityForResult(enableBtIntent, REQUEST_DISCOVERABLE_BT);

                            }

                        }

                        override fun onPermissionDenied(p0: PermissionDeniedResponse?) {

                            if(p0?.isPermanentlyDenied == true)
                                Log.d("MainActivity","Permission denied")
                        }

                        override fun onPermissionRationaleShouldBeShown(
                            p0: PermissionRequest?,
                            p1: PermissionToken?
                        ) {
                            p1?.continuePermissionRequest()
                        }

                    }
                ).check()


        })
        button3.setOnClickListener( View.OnClickListener() {
            Dexter.withContext(this@MainActivity).withPermission(   Manifest.permission.BLUETOOTH_CONNECT)
                .withListener(
                    object: PermissionListener {
                        @SuppressLint("MissingPermission")
                        override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                            mBluetoothAdapter.disable();
                            //out.append("TURN_OFF BLUETOOTH");
                            Toast.makeText(getApplicationContext(), "TURNING_OFF BLUETOOTH", Toast.LENGTH_LONG);

                        }

                        override fun onPermissionDenied(p0: PermissionDeniedResponse?) {

                            if(p0?.isPermanentlyDenied == true)
                                Log.d("MainActivity","Permission denied")
                        }

                        override fun onPermissionRationaleShouldBeShown(
                            p0: PermissionRequest?,
                            p1: PermissionToken?
                        ) {
                            p1?.continuePermissionRequest()
                        }

                    }
                ).check()

        });
    }

}