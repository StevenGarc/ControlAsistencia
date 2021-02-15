package com.example.asistencia20;

import androidx.appcompat.app.AppCompatActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.asistencia20.Model.Clase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;


public class Main2Activity extends AppCompatActivity {
    private HashSet<String> discoveredDevices;
    private BluetoothAdapter bluetoothAdapter;


    public  Clase c1;

    public Clase c2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        c1= new Clase();
        c1.setId("1");
        c1.setMacSalon("F7:05:11:CA:E3:EF");
        c1.setNombre("Hackathon");
        c2= new Clase();
        c2.setId("2");
        c2.setMacSalon("C8:7D:3B:32:38:D6");
        c2.setNombre("Otra Clase");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        discoveredDevices= new HashSet<String>();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 8);
        }
        buscar();
    }

    private BluetoothAdapter.LeScanCallback BLECallback = new BluetoothAdapter.LeScanCallback() {

        @Override
        public void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bytes) {
            discoveredDevices.add(bluetoothDevice.getAddress());
            Log.i("ENCONTRO", "name--- "+bluetoothDevice.getName()+"__MAC__ "+ bluetoothDevice.getAddress() );

        }
    };

    public void buscar() {
        Log.i("EN EL BOTON", "Cuando hay clic en el boton");
        scanLeDevice();

    }

    private Handler handler = new Handler();

    // Stops scanning after 1 second.
    private static final long SCAN_PERIOD = 1000000000;

    //inicia y detiene la busqueda
    private void scanLeDevice() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                bluetoothAdapter.stopLeScan(leScanCallback);
                /*try {
                    PostHTTP();//Enviar las mac
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
            }
        }, SCAN_PERIOD);
        bluetoothAdapter.startLeScan(leScanCallback);

    }

    /**
     * Envia el arreglo de mac
     */
    private void PostHTTP() throws JSONException {

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // Operaciones http
                Log.i("ENVIAR", "Se Envia A Servidor");
                RealizarPost();
        } else {
            // Mostrar errores
        }

    }

    public void RealizarPost() throws JSONException {
        //try {
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "http://7dc5a3aa.ngrok.io/server";
            JSONObject jObject = new JSONObject("{\n" +
                    "\"identificacion_estudiante\": \"10753138465\",\n" +
                    "\"nombre_estudiante\": \"Jose Daniel Escobar Murcia\",\n" +
                    "\"salon_registro\": \"SLX21Z\",\n" +
                    "\"direccion_mac\": \"B8:27:EB:61:92:70\",\n" +
                    "\"hora_entrada\": \"7:04:35\",\n" +
                    "\"hora_salida\": \"8:53:24\"\n" +
                    "\"dispocitivos\":" + discoveredDevices.toString() + "\"}");
            JsonObjectRequest jobReq = new JsonObjectRequest(Request.Method.POST, url, jObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                        }
                    });

            queue.add(jobReq);
        /*}catch (Exception e){
            Log.i("ERROR EN PETICION", "Se Daño");
        }*/
    }


    // Dusqueda.
    private BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback() {
                @Override
                public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
                            if(!discoveredDevices.contains(device)) {
                                discoveredDevices.add(device.getAddress());
                                if (device.getAddress().equals(c1.getMacSalon())){
                                    TextView tv=findViewById(R.id.Clase);
                                    tv.setText("Esta En Clase: "+c1.getNombre());
                                }
                                if (device.getAddress().equals(c2.getMacSalon())){
                                    TextView tv=findViewById(R.id.Clase);
                                    tv.setText("Esta En Clase: "+c2.getNombre());
                                }
                                Log.i("SE ENCONTRO", device.getAddress());
                                Log.i("NUMERO", String.valueOf(discoveredDevices.size()));
                            }
                }
            };
}
