package com.example.firststep;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.firststep.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'firststep' library on application startup.
    static {
        System.loadLibrary("firststep");
        System.loadLibrary("mbedcrypto");
    }

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int res = initRng();
        byte[] rnd = randomBytes(10);

        byte[] key = "rnd[0]".getBytes();
        byte[] enc = encrypt(key, "Test".getBytes());

        String out = new String(decrypt(key, enc));

        // Example of a call to a native method
        TextView tv = binding.sampleText;
        //tv.setText(out);
        tv.setText(stringFromJNI());

        TextView test = findViewById(R.id.rnd_elem);
        test.setText("Some random numbers: "+Byte.toString( rnd[0])+" , "+String.format("%d", rnd[1]));
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