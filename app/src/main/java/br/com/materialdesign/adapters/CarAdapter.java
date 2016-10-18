package br.com.materialdesign.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.materialdesign.domain.Car;
import br.com.materialdesign.intarfaces.RecyclerViewOnClickListenerHack;
import materialdesign.toolbar.exemplo.br.materialdesign.R;

/**
 * Created by Dalton on 13/10/2016.
 */

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.MyViewHolder> {

    private List<Car> cars ;
    private LayoutInflater layoutInflater;
    private RecyclerViewOnClickListenerHack recyclerViewOnClickListenerHackI;

    public CarAdapter(Context c, List<Car> listCar){
        this.cars = listCar;
        this.layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("LOG", "onCreateViewHolder()");
        View v = layoutInflater.inflate(R.layout.item_car,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
        Log.i("LOG", "onBindViewHolder()");
        myViewHolder.ivCar.setImageResource(cars.get(position).getPhoto());
        myViewHolder.tvModel.setText(cars.get(position).getModel());
        myViewHolder.tvBrand.setText(cars.get(position).getBrand());
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    public void addListItem(Car car, int position){
        cars.add(car);
        notifyItemInserted(position);
    }

    public void removeListItem(int position){
        cars.remove(position);
        notifyItemRemoved(position);
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r){
        recyclerViewOnClickListenerHackI = r;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

        public ImageView ivCar;
        public TextView tvModel;
        public TextView tvBrand;

        public MyViewHolder(View itemView) {
            super(itemView);

            ivCar = (ImageView) itemView.findViewById(R.id.iv_car);
            tvModel = (TextView) itemView.findViewById(R.id.tv_model);
            tvBrand = (TextView) itemView.findViewById(R.id.tv_brand);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(recyclerViewOnClickListenerHackI != null){
                recyclerViewOnClickListenerHackI.onClickListener(v,getPosition());
            }
        }
    }
}
