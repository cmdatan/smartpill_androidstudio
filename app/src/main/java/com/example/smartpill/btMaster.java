package com.example.smartpill;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class btMaster extends Application {

    public WordViewModel mWordViewModel;
    public BluetoothAdapter mBTAdapter;
    public ArrayAdapter<String> mBTArrayAdapter;
    public LiveData<List<Word>> mAllWords;

    private final String TAG = btMaster.class.getSimpleName();
    private Handler mHandler; // Our main handler that will receive callback notifications
    public btMaster.ConnectedThread mConnectedThread; // bluetooth background worker thread to send and receive data
    public BluetoothSocket mBTSocket = null; // bi-directional client-to-client data path

    public List<Word> mListWords;

    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); // "random" unique identifier

    // #defines for identifying shared types between calling functions
    private final static int MESSAGE_READ = 2; // used in bluetooth handler to identify message update
    private final static int CONNECTING_STATUS = 3; // used in bluetooth handler to identify message status

    @Override
    public void onCreate() {
        super.onCreate();

        mBTArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        mBTAdapter = BluetoothAdapter.getDefaultAdapter(); // get a handle on the bluetooth radio

        mHandler = new Handler(){
            public void handleMessage(android.os.Message msg){
                if(msg.what == MESSAGE_READ){
                    String readMessage = null;
                    try {
                        readMessage = new String((byte[]) msg.obj, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    //mReadBuffer.setText(readMessage);
                }

                if(msg.what == CONNECTING_STATUS){
                    if(msg.arg1 == 1)
                        Toast.makeText(getBaseContext(), "Connected to Device: " + (String)(msg.obj), Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getBaseContext(), "Connection Failed", Toast.LENGTH_SHORT).show();
                }
            }
        };



    }


    public void btSendAll() {
        if (mConnectedThread != null) {
            new Thread()
            {
                public void run() {
                        SystemClock.sleep(300);
                        mWordViewModel = ViewModelProviders.of(homeActivity.instance).get(WordViewModel.class);
                        mListWords = mWordViewModel.getWordsComplete();

                        mConnectedThread.write("&");
                        String meds; Integer boxNo;

                        for (Word word: mListWords) {
                            meds = word.getMedicine();
                            if (meds.equals("Paracetamol")) {
                                boxNo = 0;
                            } else if (meds.equals("Cefalexin")) {
                                boxNo = 1;
                            } else if (meds.equals("Amoxicillin")) {
                                boxNo = 2;
                            } else {
                                boxNo = 3;
                            }

                            mConnectedThread.write(String.valueOf(boxNo));      //boxNo
                            mConnectedThread.write("-");
                            mConnectedThread.write(String.valueOf(word.getMon()));      //dow
                            mConnectedThread.write("-");
                            mConnectedThread.write(String.valueOf(word.getTue()));      //dow
                            mConnectedThread.write("-");
                            mConnectedThread.write(String.valueOf(word.getWed()));      //dow
                            mConnectedThread.write("-");
                            mConnectedThread.write(String.valueOf(word.getThurs()));      //dow
                            mConnectedThread.write("-");
                            mConnectedThread.write(String.valueOf(word.getFri()));      //dow
                            mConnectedThread.write("-");
                            mConnectedThread.write(String.valueOf(word.getSat()));      //dow
                            mConnectedThread.write("-");
                            mConnectedThread.write(String.valueOf(word.getSun()));      //dow
                            mConnectedThread.write("-");
                            mConnectedThread.write(String.valueOf(word.getHour()));     //hr
                            mConnectedThread.write("-");
                            mConnectedThread.write(String.valueOf(word.getMinute()));      //min
                            mConnectedThread.write("#");              //end
                            SystemClock.sleep(300);
                        }
                    }
            }.start();
        }
    }


    public void pairDevice(MenuItem item){
        if(mBTAdapter.isEnabled()) {
            final String address = "20:18:08:23:44:03";
            final String name = "HC-05";

            //if(mConnectedThread != null) {
                //mConnectedThread.cancel();
                //mConnectedThread = null;
            //}

            new Thread()
            {
                public void run() {
                    boolean fail = false;

                    BluetoothDevice device = mBTAdapter.getRemoteDevice(address);
                    try {
                        mBTSocket = createBluetoothSocket(device);
                    } catch (IOException e) {
                        fail = true;
                        Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_SHORT).show();
                    }
                    // Establish the Bluetooth socket connection.
                    try {
                        mBTSocket.connect();
                    } catch (IOException e) {
                        try {
                            fail = true;
                            mBTSocket.close();
                            mHandler.obtainMessage(CONNECTING_STATUS, -1, -1)
                                    .sendToTarget();
                        } catch (IOException e2) {
                            //insert code to deal with this
                            Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    if(fail == false) {
                        mConnectedThread = new btMaster.ConnectedThread(mBTSocket);
                        mConnectedThread.start();

                        mWordViewModel = ViewModelProviders.of(homeActivity.instance).get(WordViewModel.class);
                        mListWords = mWordViewModel.getWordsComplete();

                        mConnectedThread.write("&");
                        String meds; Integer boxNo;

                        for (Word word: mListWords) {
                            meds = word.getMedicine();
                            if (meds.equals("Paracetamol")) {
                                boxNo = 0;
                            } else if (meds.equals("Cefalexin")) {
                                boxNo = 1;
                            } else if (meds.equals("Amoxicillin")) {
                                boxNo = 2;
                            } else {
                                boxNo = 3;
                            }

                            mConnectedThread.write(String.valueOf(boxNo));      //boxNo
                            mConnectedThread.write("-");
                            mConnectedThread.write(String.valueOf(word.getMon()));      //dow
                            mConnectedThread.write("-");
                            mConnectedThread.write(String.valueOf(word.getTue()));      //dow
                            mConnectedThread.write("-");
                            mConnectedThread.write(String.valueOf(word.getWed()));      //dow
                            mConnectedThread.write("-");
                            mConnectedThread.write(String.valueOf(word.getThurs()));      //dow
                            mConnectedThread.write("-");
                            mConnectedThread.write(String.valueOf(word.getFri()));      //dow
                            mConnectedThread.write("-");
                            mConnectedThread.write(String.valueOf(word.getSat()));      //dow
                            mConnectedThread.write("-");
                            mConnectedThread.write(String.valueOf(word.getSun()));      //dow
                            mConnectedThread.write("-");
                            mConnectedThread.write(String.valueOf(word.getHour()));     //hr
                            mConnectedThread.write("-");
                            mConnectedThread.write(String.valueOf(word.getMinute()));      //min
                            mConnectedThread.write("#");              //end
                            SystemClock.sleep(300);
                        }

                        mHandler.obtainMessage(CONNECTING_STATUS, 1, -1, name)
                                .sendToTarget();

                    }
                }
            }.start();
        }
        else
            Toast.makeText(getApplicationContext(), "Bluetooth not on", Toast.LENGTH_SHORT).show();
    }


    public BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        try {
            final Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", UUID.class);
            return (BluetoothSocket) m.invoke(device, BTMODULEUUID);
        } catch (Exception e) {
            Log.e(TAG, "Could not create Insecure RFComm Connection",e);
        }
        return  device.createRfcommSocketToServiceRecord(BTMODULEUUID);
    }

    public class ConnectedThread extends Thread {
        private BluetoothSocket mmSocket;        //public final originally
        private InputStream mmInStream;
        private OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[1024];  // buffer store for the stream
            int bytes; // bytes returned from read()
            // Keep listening to the InputStream until an exception occurs
            while (!Thread.interrupted()) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.available();
                    if(bytes != 0) {
                        buffer = new byte[1024];
                        SystemClock.sleep(100); //pause and wait for rest of data. Adjust this depending on your sending speed.
                        bytes = mmInStream.available(); // how many bytes are ready to be read?
                        bytes = mmInStream.read(buffer, 0, bytes); // record how many bytes we actually read
                        mHandler.obtainMessage(MESSAGE_READ, bytes, -1, buffer)
                                .sendToTarget(); // Send the obtained bytes to the UI activity
                    }
                } catch (IOException e) {
                    //Thread.currentThread().interrupt();
                    e.printStackTrace();
                    Toast.makeText(getBaseContext(), "Disconnected", Toast.LENGTH_SHORT).show();

                    break;
                }
            }
        }

        /* Call this from the main activity to send data to the remote device */
        public void write(String input) {
            byte[] bytes = input.getBytes();           //converts entered String into bytes
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) { }
        }

        /* Call this from the main activity to shutdown the connection */
        public void cancel() {
            try {
                if (mmSocket.isConnected()) {
                    mmSocket.close();
                }
            } catch (IOException e) { }

            Thread.currentThread().interrupt();
        }

        /*

        public void cancel() {
            if (mmSocket.isConnected()) {
                if (mmInStream != null) {
                    try {
                        mmInStream.close();
                    } catch (Exception e) {
                    }
                    mmInStream = null;
                }

                if (mmOutStream != null) {
                    try {
                        mmOutStream.close();
                    } catch (Exception e) {
                    }
                    mmOutStream = null;
                }

                if (mmSocket != null) {
                    try {
                        mmSocket.close();
                    } catch (Exception e) {
                    }
                    mmSocket = null;
                }
            }

            //interrupt();
        }

        */

    }
}
