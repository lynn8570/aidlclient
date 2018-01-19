package demo.lynn.aidlclient;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    private TextView textview;
    private AIDLManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = AIDLManager.getInstance(MainActivity.this);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                textview.setText(manager.getAlVersion());
            }
        });
        textview = (TextView) findViewById(R.id.textview);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        manager.unbindService(MainActivity.this);
    }
}
