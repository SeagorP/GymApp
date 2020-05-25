package com.example.gymapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Použité zdroje  https://stackoverflow.com/questions/40584424/simple-android-recyclerview-example?fbclid=IwAR2NJPa7ds9UX0OC-DzRpmKa_o576dkeCOfzsCo9F02_yK7LAh1zYNXJfIE
 * Triedu som vytvoril podla návodu z linku ktorý som uviedol.
 */
class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
    // https://stackoverflow.com/questions/40584424/simple-android-recyclerview-example?fbclid=IwAR2NJPa7ds9UX0OC-DzRpmKa_o576dkeCOfzsCo9F02_yK7LAh1zYNXJfIE

    private List<String> listUdajov;
    private LayoutInflater inflater;
    private ItemClickListener clickListener;


    /**
     * Nastavenie dát.
     * @param context
     * @param udaje
     */
    MyRecyclerViewAdapter(Context context, List<String> udaje) {
        this.inflater = LayoutInflater.from(context);
        this.listUdajov = udaje;
    }

    /**
     * Zväčšuje počet riadkov.
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.zoznam_item, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Pridava udaju k text viewom.
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String nazov = listUdajov.get(position);
        holder.text.setText(nazov);
    }

    /**
     * Vráti počet udajov v liste.
     * @return
     */
    @Override
    public int getItemCount() {
        return listUdajov.size();
    }


    /**
     * Trieda ktorá uklada dáta ktorá sa posúvaju mimo zobrazovacie pole.
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView text;

        /**
         * Nastavi click lisener nad itemamy.
         * @param itemView
         */
        ViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.listTreningov);
            itemView.setOnClickListener(this);
        }

        /**
         * Ak existuje clickListener volá funkciu onItemClick.
         * @param view
         */
        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onItemClick(view, getAdapterPosition());
        }
    }

    /**
     * Vráti item na danom id.
     * @param index
     * @return
     */
    String getItem(int index) {
        return listUdajov.get(index);
    }

    /**
     * Nastaví click listenera
     * @param itemClickListener
     */
    void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    /**
     * Volá metódu onitemClick z aktivity ktorá vytvorila recycler.
     */
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}