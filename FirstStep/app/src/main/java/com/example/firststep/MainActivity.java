package com.example.firststep;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firststep.databinding.ActivityMainBinding;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class MainActivity<activityResultLauncher> extends AppCompatActivity {
    ActivityResultLauncher activityResultLauncher;
    // Used to load the 'firststep' library on application startup.
    static {
        System.loadLibrary("firststep");
        System.loadLibrary("mbedcrypto");
    }

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityResultLauncher  = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            // обработка результата
                            String pin = data.getStringExtra("pin");
                            Toast.makeText(MainActivity.this, pin,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        int res = initRng();
        byte[] rnd = randomBytes(10);

        byte[] key = "rnd[0]".getBytes();
        byte[] enc = encrypt(key, "Test".getBytes());

        String out = new String(decrypt(key, enc));

        // Example of a call to a native method
      //  TextView tv = binding.sampleText;
        //tv.setText(out);
       // tv.setText(stringFromJNI());

       // TextView test = findViewById(R.id.rnd_elem);
        Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
      //  test.setText("Some random numbers: "+Byte.toString( rnd[0])+" , "+String.format("%d", rnd[1]));
    }



    public static byte[] stringToHex(String s)
    {
        byte[] hex;
        try
        {
            hex = Hex.decodeHex(s.toCharArray());
        }
        catch (DecoderException ex)
        {
            hex = null;
        }
        return hex;
    }

    public void onButtonClick(View v)
    {

        Intent it = new Intent(this, PinpadActivity.class);
        //startActivity(it);
        activityResultLauncher.launch(it);
        //Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
       // byte[] key = stringToHex("0123456789ABCDEF0123456789ABCDE0");
        //byte[] enc = encrypt(key, stringToHex("000000000000000102"));
        //byte[] dec = decrypt(key, enc);
        //String s = new String(Hex.encodeHex(dec)).toUpperCase();
        //Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }


    /**
     * A native method that is implemented by the 'firststep' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
    public static native int initRng();
    public static native byte[] randomBytes(int no);
    public static native byte[] encrypt(byte[] key, byte[] data);
    public static native byte[] decrypt(byte[] key, byte[] data);
}

