package in.bitcode.timerdialog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.sql.Time;

public class MainActivity extends AppCompatActivity {

    protected TimerDialog timerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showTimerDialog();
    }

    protected void showTimerDialog() {
        timerDialog = new TimerDialog(this);
        timerDialog.setOnTimerExpired(new MyOnTimerExpiredListener());
        timerDialog.setTimer(1, 1, 9);
        timerDialog.show();
    }

    private class MyOnTimerExpiredListener implements TimerDialog.OnTimerExpired {
        @Override
        public void onTimerExpired() {

        }
    }
}