package br.com.materialdesign.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.List;

import br.com.materialdesign.R;
import br.com.materialdesign.domain.Car;
import br.com.materialdesign.helper.ImageHelper;
import br.com.materialdesign.intarfaces.RecyclerViewOnClickListenerHack;

/**
 * Created by Dalton on 13/10/2016.
 *
 * See more about RecycleView :
 *      https://developer.android.com/training/material/lists-cards.html
 */

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.MyViewHolder> {

    private Context mContext;
    private List<Car> cars ;
    private LayoutInflater layoutInflater;
    private RecyclerViewOnClickListenerHack recyclerViewOnClickListenerHackI;
    private float scale;
    private int width;
    private int height;

    public CarAdapter(Context c, List<Car> listCar){
        mContext = c;
        this.cars = listCar;
        this.layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        scale = mContext.getResources().getDisplayMetrics().density;
        // Corrigir
        width = mContext.getResources().getDisplayMetrics().widthPixels  -  (int)(14 + scale + 8.5f);
        height = (width/16) * 9;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("LOG", "onCreateViewHolder()");
        View v = layoutInflater.inflate(R.layout.item_car_card,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
        Log.i("LOG", "onBindViewHolder()");
        myViewHolder.ivCar.setImageResource(cars.get(position).getPhoto());
        myViewHolder.tvModel.setText(cars.get(position).getModel());
        myViewHolder.tvBrand.setText(cars.get(position).getBrand());

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            myViewHolder.ivCar.setImageResource(cars.get(position).getPhoto());
        }else{
            Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),cars.get(position).getPhoto());
            bitmap = Bitmap.createScaledBitmap(bitmap,width,height,false);

            //ImageHelper para arredondar a borda
            bitmap = ImageHelper.getRoundedCornerBitmap(mContext,bitmap,10,width,height,false,false,true,true);
            myViewHolder.ivCar.setImageBitmap(bitmap);
        }

        try{
            YoYo.with(Techniques.Tada)
                    .duration(700)
                    .playOn(myViewHolder.itemView);
        }catch(Exception e){
            e.printStackTrace();
        }
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
