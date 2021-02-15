package com.example.asistencia20;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class MainActivity extends AppCompatActivity {

    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


    private final BroadcastReceiver bReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            // Filtramos por la accion. Nos interesa detectar BluetoothAdapter.ACTION_STATE_CHANGED
            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                // Solicitamos la informacion extra del intent etiquetada como BluetoothAdapter.EXTRA_STATE
                // El segundo parametro indicara el valor por defecto que se obtendra si el dato extra no existe
                final int estado = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);

                switch (estado){
                    // Apagado
                    case BluetoothAdapter.STATE_OFF:
                    {
                        activarBT(bluetoothAdapter);
                        break;
                    }
                    default:
                        break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registrarEventosBluetooth();
        Ubicacion(this);




    }

    private void registrarEventosBluetooth()
    {
        // Registramos el BroadcastReceiver que instanciamos previamente para
        // detectar los distintos eventos que queremos recibir
        IntentFilter filtro = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        this.registerReceiver(bReceiver, filtro);
    }

    public void Ubicacion(Object context){
        if (ContextCompat.checkSelfPermission((Activity)context, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            Log.i("EN UBICAION", "no hay permiso");
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    2);
        }
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }

    }

    //Activar ubicación
    public void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("El servicio se ubicación debe estar activado")
                .setCancelable(false)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public LocationListener l= new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            buildAlertMessageNoGps();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            buildAlertMessageNoGps();
        }

        @Override
        public void onProviderEnabled(String provider) {
            buildAlertMessageNoGps();
        }

        @Override
        public void onProviderDisabled(String provider) {
            buildAlertMessageNoGps();
        }
    };

    public void Enviar(View v){
        if(!bluetoothAdapter.isEnabled()) {
            activarBT(bluetoothAdapter);
        }else{
            String correo = ((EditText) findViewById(R.id.Correo)).getText().toString();
            String pass = ((EditText) findViewById(R.id.Pass)).getText().toString();
            String Mac = getMac();
            Imprimir(this, correo + "--" + pass + "--" + Mac);

            Intent intent = new Intent (v.getContext(), Main2Activity.class);
            startActivityForResult(intent, 0);
        }

    }

    public String getMac(){
        String bluetoothMacAddress="No fue";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M){
            try {
                Field mServiceField = bluetoothAdapter.getClass().getDeclaredField("mService");
                mServiceField.setAccessible(true);

                Object btManagerService = mServiceField.get(bluetoothAdapter);

                if (btManagerService != null) {
                    bluetoothMacAddress = (String) btManagerService.getClass().getMethod("getAddress").invoke(btManagerService);
                }
            } catch (NoSuchFieldException e) {

            } catch (NoSuchMethodException e) {

            } catch (IllegalAccessException e) {

            } catch (InvocationTargetException e) {

            }
        } else {
            bluetoothMacAddress = bluetoothAdapter.getAddress();
        }
        return bluetoothMacAddress;
    }
    public void activarBT(BluetoothAdapter bluetoothAdapter){
        if (bluetoothAdapter != null) {
            if (!bluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 8);
            }
        }
    }
    public static void Imprimir(Context context, String mensaje){
        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
    }

}
