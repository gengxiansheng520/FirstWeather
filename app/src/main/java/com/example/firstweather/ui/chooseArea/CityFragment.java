package com.example.firstweather.ui.chooseArea;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.firstweather.R;
import com.example.firstweather.databinding.FragmentCityBinding;
import com.example.firstweather.databinding.FragmentProvinceBinding;
import com.example.firstweather.db.model.City;
import com.example.firstweather.db.model.County;
import com.example.firstweather.db.model.Province;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class CityFragment extends Fragment {

    private FragmentCityBinding binding;
    private ChooseViewModel chooseViewModel;
    private ProvinceAdapter provinceAdapter;
    private CityAdapter cityAdapter;
    private CountyAdapter countyAdapter;
    private List<Province> provinceList;
    private List<City> cityList;
    private List<County> countyList;
    private CompositeDisposable compositeDisposable;
    private static final String TAG = "chooseFragment";
    private int currentLevel;
    private int Level_Province = 0;
    private int Level_City = 1;
    private int Level_County = 2;
    private Province currentProvince;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_city, container, false);
        binding = FragmentCityBinding.bind(view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }

    void queryCity(Province province) {
        currentProvince = province;
        currentLevel = Level_City;
        binding.backButton.setVisibility(View.VISIBLE);
        binding.textView2.setText(province.getName());
        binding.recyclerView.setAdapter(cityAdapter);
        chooseViewModel.getDatabaseCity(province.getId()).observe(requireActivity(), new Observer<List<City>>() {
            @SuppressLint("CheckResult")
            @Override
            public void onChanged(List<City> cities) {
                if (cities.size() != 0) {
                    cityList = cities;
                    binding.recyclerView.setAdapter(cityAdapter);
                    for (City city : cityList) {
                        city.setProvince_id(province.getId());
                    }
                    cityAdapter.submitList(cityList);
                } else {
                    Disposable disposable =chooseViewModel.getServiceCity(String.valueOf(province.getId())).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread()).subscribe(s->{
                                Log.d(TAG, "onChanged: "+ province.getId());
                                cityList = s;
                                if (cityList.size() != 0) {
                                    binding.recyclerView.setAdapter(cityAdapter);
                                    for (City city : cityList) {
                                        city.setProvince_id(province.getId());
                                    }
                                    cityAdapter.submitList(cityList);
                                    chooseViewModel.insertCity(cityList);
                                }
                            });
                    compositeDisposable.add(disposable);
                }
            }
        });
    }
    private void init() {
        provinceAdapter = new ProvinceAdapter();
        cityAdapter = new CityAdapter();
        countyAdapter = new CountyAdapter();
        provinceList = new ArrayList<>();
        cityList = new ArrayList<>();
        countyList = new ArrayList<>();
        compositeDisposable = new CompositeDisposable();
        chooseViewModel = new ViewModelProvider(this).get(ChooseViewModel.class);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireActivity(), LinearLayout.VERTICAL);
        binding.recyclerView.addItemDecoration(dividerItemDecoration);
    }
}