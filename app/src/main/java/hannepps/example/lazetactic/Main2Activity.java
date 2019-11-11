package hannepps.example.lazetactic;

import android.content.Intent;
import android.os.Bundle;

import com.example.lazetactic.R;

import androidx.appcompat.app.AppCompatActivity;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
