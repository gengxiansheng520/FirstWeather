package com.example.firstweather.backService;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.firstweather.App;
import com.example.firstweather.db.Repository;
import com.example.firstweather.db.model.County;
import com.example.firstweather.db.model.HeWeather;
import com.example.firstweather.db.model.Weather;
import com.example.firstweather.ui.WeatherActivity;
import com.example.firstweather.ui.chooseProvince.ChooseViewModel;
import com.google.gson.Gson;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WeatherService extends Service {

    private Repository repository;
    private CompositeDisposable compositeDisposable;
    public WeatherService() {
        compositeDisposable = new CompositeDisposable();
        repository = App.getRepository();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        updateWeather();
        updatePic();
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour = 8 * 60 * 60 *1000;
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
        Intent intent1 = new Intent(this,WeatherService.class);
        PendingIntent pi = PendingIntent.getService(this,0,intent1,0);
        manager.cancel(pi);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);
        return super.onStartCommand(intent, flags, startId);
    }

    void updateWeather() {
        String weatherId = App.getWeatherId();
        Disposable disposable = repository.getWeather(weatherId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(s->{
                    HeWeather heWeather = s;
                    if (heWeather.weatherList != null) {
                        Weather weather = heWeather.weatherList.get(0);
                        Gson gson = new Gson();
                        String weatherString = gson.toJson(weather);
                        App.saveWeather(weatherString);
                    }
                });
        compositeDisposable.add(disposable);
    }
    void updatePic() {
        Disposable disposable = repository.getPic("bing_pic").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(s->{
                    String bingPic = new String(s.bytes());
                    App.savePic(bingPic);
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
