package com.fakih.bandung.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.fakih.bandung.R;
import com.fakih.bandung.model.ModelRekreasi;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RekreasiAdapter extends RecyclerView.Adapter<RekreasiAdapter.ViewHolder> {
    List<ModelRekreasi> items;
    RekreasiAdapter.onSelectData onSelectData;
    Context mContext;

    public interface onSelectData {
        void onSelected(ModelRekreasi modelRekreasi);
    }

    public RekreasiAdapter(Context context, List<ModelRekreasi> items, RekreasiAdapter.onSelectData xSelectData) {
        this.mContext = context;
        this.items = items;
        this.onSelectData = xSelectData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ModelRekreasi data = items.get(position);

        //Get Image
        Picasso.get()
                .load(data.getImage())
                .into(holder.imgRekreasi);
        holder.nama.setText(data.getNama());
        holder.kota.setText(data.getKota());
        holder.jam_operasional.setText(data.getJam());
        holder.cvWisata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectData.onSelected(data);
            }
        });
    }

    @Override
    public int getItemCount() { return items.size(); }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView kota;
        TextView jam_operasional;
        TextView nama;
        CardView cvWisata;
        ImageView imgRekreasi;

        public ViewHolder(View itemView) {
            super(itemView);

            cvWisata = (CardView) itemView.findViewById(R.id.cvWisata);
            imgRekreasi = itemView.findViewById(R.id.imgWisata);
            nama = itemView.findViewById(R.id.tvNama);
            kota = itemView.findViewById(R.id.tvKota);
            jam_operasional = itemView.findViewById(R.id.tvJam);
        }
    }

}
