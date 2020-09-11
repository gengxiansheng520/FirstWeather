package com.example.firstweather.ui.chooseCounty;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.firstweather.R;
import com.example.firstweather.databinding.FragmentCountyBinding;
import com.example.firstweather.db.model.City;
import com.example.firstweather.db.model.County;
import com.example.firstweather.ui.MainActivity;
import com.example.firstweather.ui.WeatherActivity;
import com.example.firstweather.ui.chooseProvince.ChooseViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class CountyFragment extends Fragment {

    private FragmentCountyBinding binding;
    private ChooseViewModel chooseViewModel;
    private CountyAdapter countyAdapter;
    private List<County> countyList;
    private CompositeDisposable compositeDisposable;
    private NavController controller;
    private static final String TAG = "CountyFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_county, container, false);
        binding = FragmentCountyBinding.bind(view);
        init();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        City city = chooseViewModel.getCity();
        queryCounty(city);
        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller = Navigation.findNavController(requireActivity(),R.id.fragment);
                controller.navigate(R.id.cityFragment);
            }
        });

        countyAdapter.setOnItemClick(county -> {
            if (getActivity() instanceof MainActivity) {
                Intent intent = new Intent(requireActivity(),WeatherActivity.class);
                intent.putExtra("county",county);
                startActivity(intent);
                requireActivity().finish();
            } else if (getActivity() instanceof WeatherActivity) {
                WeatherActivity activity = (WeatherActivity) getActivity();
                activity.binding.drawerLayout.closeDrawers();
                activity.binding.swipeRefresh.setRefreshing(true);
                chooseViewModel.setCounty(county);
                activity.requestWeather(county);
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    void queryCounty(City city) {
        binding.frameLayout.setVisibility(View.INVISIBLE);
        binding.backButton.setVisibility(View.VISIBLE);
        binding.textView2.setText(city.getName());
        binding.recyclerView.setAdapter(countyAdapter);
        chooseViewModel.getDatabaseCounty(city.getId()).observe(requireActivity(), new Observer<List<County>>() {
            @Override
            public void onChanged(List<County> counties) {
                if (counties.size() != 0) {
                    countyList = counties;
                    for (County county : countyList) {
                        county.setCity_id(city.getId());
                    }
                    countyAdapter.submitList(countyList);
                } else {
                    String province_id = String.valueOf(city.getProvince_id());
                    String city_id = String.valueOf(city.getId());
                    Disposable disposable = chooseViewModel.getServiceCounty(province_id,city_id)
                            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                            .subscribe(s->{
                                countyList = s;
                                if (countyList.size() != 0) {
                                    for (County county : countyList) {
                                        county.setCity_id(city.getId());
                                    }
                                    countyAdapter.submitList(countyList);
                                    chooseViewModel.insertCounty(countyList);
                                }
                            });
                    compositeDisposable.add(disposable);
                }
            }
        });
        binding.frameLayout.setVisibility(View.VISIBLE);
    }
    private void init() {
        countyAdapter = new CountyAdapter();
        countyList = new ArrayList<>();
        compositeDisposable = new CompositeDisposable();
        chooseViewModel = new ViewModelProvider(requireActivity()).get(ChooseViewModel.class);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireActivity(), LinearLayout.VERTICAL);
        binding.recyclerView.addItemDecoration(dividerItemDecoration);
    }
}