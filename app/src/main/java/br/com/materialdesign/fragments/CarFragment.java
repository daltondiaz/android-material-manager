package br.com.materialdesign.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import br.com.materialdesign.adapters.CarAdapter;
import br.com.materialdesign.domain.Car;
import br.com.materialdesign.intarfaces.RecyclerViewOnClickListenerHack;
import br.com.materialdesign.MainActivity;
import materialdesign.toolbar.exemplo.br.materialdesign.R;

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

        recyclerView.addOnItemTouchListener(new RecycleViewTouchListener(getActivity(),recyclerView,this));

        LinearLayoutManager lln = new LinearLayoutManager(getActivity());
        lln.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(lln);

        // Set lista de Car no RecycleView
        cars = ((MainActivity) getActivity()).getSetCarList(10);
        CarAdapter adapter = new CarAdapter(getActivity(),cars);
        // This porque CarFragment esta implementando RecyclerViewOnClickListenerHack
        // adapter.setRecyclerViewOnClickListenerHack(this);
        recyclerView.setAdapter(adapter );

        return view;
    }

    @Override
    public void onClickListener(View view, int position) {
        Toast.makeText(getActivity(),"onClickListener(): "+position,Toast.LENGTH_SHORT).show();

        /*CarAdapter adapter = (CarAdapter)recyclerView.getAdapter();
        adapter.removeListItem(position);*/
    }

    @Override
    public void onLongPressClickListener(View view, int position) {

        Toast.makeText(getActivity(),"onLongPressClickListener(): "+position,Toast.LENGTH_SHORT).show();

        /*CarAdapter adapter = (CarAdapter)recyclerView.getAdapter();
        adapter.removeListItem(position);*/
    }

    private static class RecycleViewTouchListener implements RecyclerView.OnItemTouchListener{


        private Context context;
        // Detecta o toca na tela, se foi um simples clique, duplos clique.. arrastou na tela entre
        // outras informações
        private GestureDetector gestureDetector;
        private RecyclerViewOnClickListenerHack recyclerViewOnClickListenerHack;

        public RecycleViewTouchListener(Context c, final RecyclerView rv, final RecyclerViewOnClickListenerHack recyclerViewHack){
            context = c;
            recyclerViewOnClickListenerHack =recyclerViewHack;

            gestureDetector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
                @Override
                public void onLongPress(MotionEvent e) {
                    super.onLongPress(e);

                    // Obtem a view q está abaixo do evento na coordenada X,Y
                    View chieldView = rv.findChildViewUnder(e.getX(),e.getY());

                    if(chieldView != null && recyclerViewHack != null){
                        recyclerViewHack.onLongPressClickListener(chieldView,
                                rv.getChildPosition(chieldView));
                    }
                }

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    // Obtem a view q está abaixo do evento na coordenada X,Y
                    View chieldView = rv.findChildViewUnder(e.getX(),e.getY());

                    if(chieldView != null && recyclerViewHack != null){
                        recyclerViewHack.onClickListener(chieldView,
                                rv.getChildPosition(chieldView));
                    }
                    return true;
                }
            });
        }

        /*
            No caso se fosse retornado true, então o evento seria interceptado antes
            de chegar no button, no caso da item_car.xml iria para no ViewGroup que
            no caso é o RelativeLayout.
         */
        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            // É interceptado para verificar se é um click ou long press
            gestureDetector.onTouchEvent(e);
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {  }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) { }
    }
}
