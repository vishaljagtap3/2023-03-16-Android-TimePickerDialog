package in.bitcode.timerdialog;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class TimerDialog extends Dialog {


    public interface OnTimerExpired {
        void onTimerExpired();
    }

    private OnTimerExpired onTimerExpired;

    public void setOnTimerExpired(OnTimerExpired onTimerExpired) {
        this.onTimerExpired = onTimerExpired;
    }

    private TimerThread timerThread;

    private TextView txtTimeLeft;
    private int hours, minutes, seconds;

    public TimerDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.timer_dialog);
        initViews();
    }

    public void setTimer(int hours, int minutes, int seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        txtTimeLeft.setText(hours + " : " + minutes + " : " + seconds);

        timerThread = new TimerThread();
        timerThread.execute((Object) null);

    }

    private void initViews() {
        txtTimeLeft = findViewById(R.id.txtTimeLeft);
    }

    private void updateTime() {
        if (seconds > 0) {
            seconds--;
        } else {
            if (minutes > 0) {
                minutes--;
                seconds = 59;
            } else {
                hours--;
                if (hours == 0) {
                    //timer has finished
                    //send callback
                    if (onTimerExpired != null) {
                        onTimerExpired.onTimerExpired();
                    }
                    timerThread.setState(false);
                    dismiss();
                    return;
                }
                minutes = 59;
                seconds = 59;
            }
        }
        txtTimeLeft.setText(hours + " : " + minutes + " : " + seconds);
    }

    private class TimerThread extends AsyncTask<Object, Integer, Object> {

        private boolean state = true;

        public void setState(boolean state) {
            this.state = state;
        }

        @Override
        protected Object doInBackground(Object... objects) {
            while (state) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                publishProgress(null);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            updateTime();
        }
    }
}








