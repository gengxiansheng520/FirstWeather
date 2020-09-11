package com.example.firstweather.ui.chooseProvince;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NavigationRes;
import androidx.annotation.NonNull;
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

import com.example.firstweather.R;
import com.example.firstweather.databinding.FragmentChooseAreaBinding;
import com.example.firstweather.databinding.FragmentProvinceBinding;
import com.example.firstweather.db.model.Province;
import com.example.firstweather.ui.BlankFragment;
import com.example.firstweather.ui.chooseCity.CityFragment;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class chooseFragment extends Fragment {


    private FragmentProvinceBinding binding;
    private ChooseViewModel chooseViewModel;
    private ProvinceAdapter provinceAdapter;
    private CompositeDisposable compositeDisposable;
    private NavController controller;
    private static final String TAG = "chooseFragment";


    public chooseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_province, container, false);
        binding = FragmentProvinceBinding.bind(view);
        init();
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        queryProvince();
        //点击事件
        provinceAdapter.setOnItemClick(province->{
            Log.d(TAG, "onActivityCreated: "+ province.toString());
            chooseViewModel.setProvince(province);
            controller = Navigation.findNavController(requireActivity(),R.id.fragment);
            controller.navigate(R.id.action_chooseFragment_to_cityFragment);
        });
    }


    void queryProvince() {
        binding.backButton.setVisibility(View.INVISIBLE);
        binding.textView2.setText("中国");
        binding.recyclerView.setAdapter(provinceAdapter);
        chooseViewModel.getDatabaseProvince().observe(requireActivity(), new Observer<List<Province>>() {
            @SuppressLint("CheckResult")
            @Override
            public void onChanged(List<Province> provinces) {
                if (provinces.size() != 0) {
                    List<Province> provinceList = provinces;
                    provinceAdapter.submitList(provinceList);
                } else {
                    Log.d(TAG, "onChanged: "+"111");
                    Disposable disposable = chooseViewModel.getServiceProvince("china").subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread()).subscribe(s->{
                        Log.d(TAG, "onChanged: "+s.toString());
                        List<Province> provinceList = s;
                        provinceAdapter.submitList(s);
                        chooseViewModel.insertProvince(s);
                    });
                    compositeDisposable.add(disposable);
                }
            }
        });
}

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    private void init() {
        provinceAdapter = new ProvinceAdapter();
        compositeDisposable = new CompositeDisposable();
        chooseViewModel = new ViewModelProvider(requireActivity()).get(ChooseViewModel.class);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireActivity(),LinearLayout.VERTICAL);
        binding.recyclerView.addItemDecoration(dividerItemDecoration);
    }

}