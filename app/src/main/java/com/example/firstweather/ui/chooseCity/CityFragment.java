package com.example.firstweather.ui.chooseCity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
//import androidx.navigation.NavController;
//import androidx.navigation.Navigation;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.firstweather.R;
import com.example.firstweather.databinding.FragmentCityBinding;
import com.example.firstweather.db.model.City;
import com.example.firstweather.db.model.Province;
import com.example.firstweather.ui.chooseProvince.ChooseViewModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class CityFragment extends Fragment {

    private FragmentCityBinding binding;
    private ChooseViewModel chooseViewModel;
    private CityAdapter cityAdapter;
    private CompositeDisposable compositeDisposable;
    private NavController controller;
    private static final String TAG = "CityFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_city, container, false);
        binding = FragmentCityBinding.bind(view);
        binding.frameLayout.setVisibility(View.INVISIBLE);
        init();
        Province province = chooseViewModel.getProvince();
        queryCity(province);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        controller = Navigation.findNavController(requireActivity(),R.id.fragment);
        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.navigate(R.id.chooseFragment);
            }
        });
        cityAdapter.setOnItemClick(city -> {
            chooseViewModel.setCity(city);
            controller.navigate(R.id.action_cityFragment_to_countyFragment);
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    void queryCity(Province province) {
        binding.frameLayout.setVisibility(View.INVISIBLE);
        binding.backButton.setVisibility(View.VISIBLE);
        binding.textView2.setText(province.getName());
        binding.recyclerView.setAdapter(cityAdapter);
        chooseViewModel.getDatabaseCity(province.getId()).observe(requireActivity(), new Observer<List<City>>() {
            @SuppressLint("CheckResult")
            @Override
            public void onChanged(List<City> cities) {
                if (cities.size() != 0) {
                    List<City> cityList = cities;
                    binding.recyclerView.setAdapter(cityAdapter);
                    for (City city : cityList) {
                        city.setProvince_id(province.getId());
                    }
                    cityAdapter.submitList(cityList);
                } else {
                    Disposable disposable =chooseViewModel.getServiceCity(String.valueOf(province.getId())).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread()).subscribe(s->{
                                Log.d(TAG, "onChanged: "+ province.getId());
                                List<City> cityList = s;
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
        binding.frameLayout.setVisibility(View.VISIBLE);
    }
    private void init() {
        cityAdapter = new CityAdapter();
        compositeDisposable = new CompositeDisposable();
        chooseViewModel = new ViewModelProvider(requireActivity()).get(ChooseViewModel.class);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireActivity(), LinearLayout.VERTICAL);
        binding.recyclerView.addItemDecoration(dividerItemDecoration);
    }
}