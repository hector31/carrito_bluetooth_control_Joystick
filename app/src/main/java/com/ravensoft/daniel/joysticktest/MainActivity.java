package com.ravensoft.daniel.joysticktest;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements JoystickView.JoystickListener{
    TextView valorX,valorY,mRadio,mAngulo ;
    private String sText,sText2;
    private Integer num1,num2,radio,angulo;
//in your OnCreate() method
private Handler mHandler = new Handler();
private Runnable mWaitRunnable = new Runnable() {
    public void run() {
        valorX.setText(sText);
        valorY.setText(sText2);
    }
};

    //-------------------------------------------
    Handler bluetoothIn;
    final int handlerState = 0;
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder DataStringIN = new StringBuilder();
    private ConnectedThread MyConexionBT;
    // Identificador unico de servicio - SPP UUID
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    // String para la direccion MAC
    private static String address = null;
    //-------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //2)
        //Enlaza los controles con sus respectivas vistas

        bluetoothIn = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == handlerState) {
                    String readMessage = (String) msg.obj;
                    DataStringIN.append(readMessage);

                    int endOfLineIndex = DataStringIN.indexOf("#");

                    if (endOfLineIndex > 0) {
                        DataStringIN.delete(0, DataStringIN.length());
                    }
                }
            }
        };

        btAdapter = BluetoothAdapter.getDefaultAdapter(); // get Bluetooth adapter
        VerificarEstadoBT();

        // Configuracion onClick listeners para los botones
        // para indicar que se realizara cuando se detecte
        // el evento de Click

    }
    @Override
    public void onJoystickMoved(float xPercent, float yPercent, int id) {

        switch (id)
        {
            case R.id.testJoystick:
                Log.d("Right Joystick", "X percent: " + xPercent + " Y percent: " + yPercent);
                valorX=new TextView(this);
                valorY=new TextView(this);
                mRadio=new TextView(this);
                mAngulo=new TextView(this);

                valorX=(TextView)findViewById(R.id.IdX);
                valorY=(TextView)findViewById(R.id.IdY);
                mRadio=(TextView)findViewById(R.id.IdRadio);
                mAngulo=(TextView) findViewById(R.id.IdAngulo);
                sText = String.valueOf(xPercent*100);//aqui estara la posicion en flotante
                sText2 = String.valueOf((yPercent*(-100)));
                num1= (int) (xPercent*100);
                num2=(int) (yPercent*(-100));
                radio=(int) Math.sqrt(( Math.pow(num1,2))+(Math.pow(num2,2)));
                angulo=(int) Math.toDegrees(Math.atan2(num2,num1));
                valorX.setText(String.valueOf(num1));
                valorY.setText(String.valueOf(num2));
                mRadio.setText(String.valueOf(radio));
                mAngulo.setText(String.valueOf(angulo));
                //NIVEL DE FUERZA 2
                if(radio>80&&(angulo>81&&angulo<=99))
                MyConexionBT.write("A");
                if (radio>80&&(angulo>63&&angulo<=81))
                MyConexionBT.write("B");
                if(radio>80&&(angulo>45&&angulo<=63))
                MyConexionBT.write("C");
                if(radio>80&&(angulo>27&&angulo<=45))
                MyConexionBT.write("D");
                if(radio>80&&(angulo>9&&angulo<=27))
                MyConexionBT.write("z");
                if (radio>80&&(angulo>-9&&angulo<=9))
                MyConexionBT.write("F");
                if(radio>80&&(angulo>-27&&angulo<=-9))
                MyConexionBT.write("G");
                if(radio>80&&(angulo>-45&&angulo<=-27))
                MyConexionBT.write("H");
                if(radio>80&&(angulo>-63&&angulo<=-45))
                MyConexionBT.write("J");
                if (radio>80&&(angulo>-81&&angulo<=-63))
                MyConexionBT.write("K");
                if(radio>80&&(angulo>-99&&angulo<=-81))
                MyConexionBT.write("L");
                if(radio>80&&(angulo>-117&&angulo<=-99))
                MyConexionBT.write("Z");
                if(radio>80&&(angulo>-135&&angulo<=-117))
                MyConexionBT.write("X");
                if (radio>80&&(angulo>-153&&angulo<=-135))
                MyConexionBT.write("|");
                if(radio>80&&(angulo>-171&&angulo<=-153))
                MyConexionBT.write("V");
                if(radio>80&&(angulo>171||angulo<=-171))
                MyConexionBT.write("b");
                if(radio>80&&(angulo>153&&angulo<=171))
                    MyConexionBT.write("N");
                if(radio>80&&(angulo>135&&angulo<=153))
                    MyConexionBT.write("M");
                if (radio>80&&(angulo>117&&angulo<=135))
                    MyConexionBT.write("Q");
                if(radio>80&&(angulo>99&&angulo<=117))
                    MyConexionBT.write("W");

                //NIVEL DE FUERZA 1
                if(radio>40&&radio<=80&&(angulo>81&&angulo<=99))
                    MyConexionBT.write("E");
                if (radio>40&&radio<=80&&(angulo>63&&angulo<=81))
                    MyConexionBT.write("R");
                if(radio>40&&radio<=80&&(angulo>45&&angulo<=63))
                    MyConexionBT.write("T");
                if(radio>40&&radio<=80&&(angulo>27&&angulo<=45))
                    MyConexionBT.write("Y");
                if(radio>40&&radio<=80&&(angulo>9&&angulo<=27))
                    MyConexionBT.write("U");
                if (radio>40&&radio<=80&&(angulo>-9&&angulo<=9))
                    MyConexionBT.write("I");
                if(radio>40&&radio<=80&&(angulo>-27&&angulo<=-9))
                    MyConexionBT.write("O");
                if(radio>40&&radio<=80&&(angulo>-45&&angulo<=-27))
                    MyConexionBT.write("P");
                if(radio>40&&radio<=80&&(angulo>-63&&angulo<=-45))
                    MyConexionBT.write("0");
                if (radio>40&&radio<=80&&(angulo>-81&&angulo<=-63))
                    MyConexionBT.write("1");
                if(radio>40&&radio<=80&&(angulo>-99&&angulo<=-81))
                    MyConexionBT.write("2");
                if(radio>40&&radio<=80&&(angulo>-117&&angulo<=-99))
                    MyConexionBT.write("3");
                if(radio>40&&radio<=80&&(angulo>-135&&angulo<=-117))
                    MyConexionBT.write("4");
                if (radio>40&&radio<=80&&(angulo>-153&&angulo<=-135))
                    MyConexionBT.write("5");
                if(radio>40&&radio<=80&&(angulo>-171&&angulo<=-153))
                    MyConexionBT.write("6");
                if(radio>40&&radio<=80&&(angulo>171||angulo<=-171))
                    MyConexionBT.write("7");
                if(radio>40&&radio<=80&&(angulo>153&&angulo<=171))
                    MyConexionBT.write("8");
                if(radio>40&&radio<=80&&(angulo>135&&angulo<=153))
                    MyConexionBT.write("9");
                if (radio>40&&radio<=80&&(angulo>117&&angulo<=135))
                    MyConexionBT.write("+");
                if(radio>40&&radio<=80&&(angulo>99&&angulo<=117))
                    MyConexionBT.write("¿");
            //nivel 0 solo direcciones totales arriba abajo izquierda derecha
                if(radio>10&&radio<=40&&(angulo>45&&angulo<=135))
                    MyConexionBT.write("a");
                if(radio>10&&radio<=40&&(angulo>-45&&angulo<=45))
                    MyConexionBT.write("i");
                if (radio>10&&radio<=40&&(angulo>-135&&angulo<=-45))
                    MyConexionBT.write("d");
                if(radio>10&&radio<=40&&(angulo>135||angulo<=-135))
                    MyConexionBT.write("h");
                if(radio<=10)
                MyConexionBT.write("p");

                break;

        }
    }
    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException
    {
        //crea un conexion de salida segura para el dispositivo
        //usando el servicio UUID
        return device.createRfcommSocketToServiceRecord(BTMODULEUUID);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        //Consigue la direccion MAC desde DeviceListActivity via intent
        Intent intent = getIntent();
        //Consigue la direccion MAC desde DeviceListActivity via EXTRA
        address = intent.getStringExtra(DispositivosBT.EXTRA_DEVICE_ADDRESS);//<-<- PARTE A MODIFICAR >->->
        //Setea la direccion MAC
        BluetoothDevice device = btAdapter.getRemoteDevice(address);

        try
        {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "La creacción del Socket fallo", Toast.LENGTH_LONG).show();
        }
        // Establece la conexión con el socket Bluetooth.
        try
        {
            btSocket.connect();
        } catch (IOException e) {
            try {
                btSocket.close();
            } catch (IOException e2) {}
        }
        MyConexionBT = new ConnectedThread(btSocket);
        MyConexionBT.start();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        try
        { // Cuando se sale de la aplicación esta parte permite
            // que no se deje abierto el socket
            btSocket.close();
        } catch (IOException e2) {}
    }

    //Comprueba que el dispositivo Bluetooth Bluetooth está disponible y solicita que se active si está desactivado
    private void VerificarEstadoBT() {

        if(btAdapter==null) {
            Toast.makeText(getBaseContext(), "El dispositivo no soporta bluetooth", Toast.LENGTH_LONG).show();
        } else {
            if (btAdapter.isEnabled()) {
            } else {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }

    //Crea la clase que permite crear el evento de conexion
    private class ConnectedThread extends Thread
    {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket)
        {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            try
            {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }
            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run()
        {
            byte[] buffer = new byte[256];
            int bytes;

            // Se mantiene en modo escucha para determinar el ingreso de datos
            while (true) {
                try {
                    bytes = mmInStream.read(buffer);
                    String readMessage = new String(buffer, 0, bytes);
                    // Envia los datos obtenidos hacia el evento via handler
                    bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }
        //Envio de trama
        public void write(String input)
        {
            try {
                mmOutStream.write(input.getBytes());
            }
            catch (IOException e)
            {
                //si no es posible enviar datos se cierra la conexión
                Toast.makeText(getBaseContext(), "La Conexión fallo", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

}
