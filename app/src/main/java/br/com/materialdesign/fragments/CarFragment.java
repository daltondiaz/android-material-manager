package br.com.materialdesign.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import br.com.materialdesign.adapters.CarAdapter;
import br.com.materialdesign.domain.Car;
import br.com.materialdesign.intarfaces.RecyclerViewOnClickListenerHack;
import materialdesign.toolbar.exemplo.br.exempletoolbarmaterialdesign.MainActivity;
import materialdesign.toolbar.exemplo.br.exempletoolbarmaterialdesign.R;

/**
 * Created by Dalton on 13/10/2016.
 */

public class CarFragment extends Fragment implements RecyclerViewOnClickListenerHack {

    private RecyclerView recyclerView;
    private List<Car> cars;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view  = inflater.inflate(R.layout.fragment_car,container,false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        // Valor maximo
        recyclerView.setHasFixedSize(true);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager lln = (LinearLayoutManager) recyclerView.getLayoutManager();
                CarAdapter adapter = (CarAdapter)recyclerView.getAdapter();

                if(cars.size() == lln.findLastCompletelyVisibleItemPosition()+1){
                    List<Car> moreTenCars = ((MainActivity) getActivity()).getSetCarList(10);

                    for(int i=0 ; i<moreTenCars.size(); i++){
                        adapter.addListItem(moreTenCars.get(i),cars.size());
                    }
                }
            }
        });

        LinearLayoutManager lln = new LinearLayoutManager(getActivity());
        lln.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(lln);

        // Set lista de Car no RecycleView
        cars = ((MainActivity) getActivity()).getSetCarList(10);
        CarAdapter adapter = new CarAdapter(getActivity(),cars);
        // This porque CarFragment esta implementando RecyclerViewOnClickListenerHack
        adapter.setRecyclerViewOnClickListenerHack(this);
        recyclerView.setAdapter(adapter );

        return view;
    }

    @Override
    public void onClickListener(View view, int position) {
        Toast.makeText(getActivity(),"Position: "+position,Toast.LENGTH_SHORT).show();

        CarAdapter adapter = (CarAdapter)recyclerView.getAdapter();
        adapter.removeListItem(position);
    }
}
