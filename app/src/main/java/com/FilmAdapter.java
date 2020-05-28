package com;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bh183.nugrahawp.InputActivity;
import com.bh183.nugrahawp.R;
import com.bh183.nugrahawp.TampilActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.FilmViewHolder> {

    private Context context;
    private ArrayList<Film> dataFilm;
    private SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");

    public FilmAdapter(Context context, ArrayList<Film> dataFilm) {
        this.context = context;
        this.dataFilm = dataFilm;

    }

    @NonNull
    @Override
    public FilmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_film, parent, false);
        return new FilmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmViewHolder holder, int position) {
        Film tempFilm = dataFilm.get(position);
        holder.idFilm = tempFilm.getIdFilm();
        holder.tvJudul.setText(tempFilm.getJudul());
        holder.tvHeadline.setText(tempFilm.getIsiFilm());
        holder.tanggal = sdFormat.format(tempFilm.getTanggal());
        holder.gambar = tempFilm.getGambar();
        holder.caption = tempFilm.getCaption();
        holder.penulis = tempFilm.getPenulis();
        holder.link = tempFilm.getLink();

        try {
            File file = new File(holder.gambar);
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            holder.imgFilm.setImageBitmap(bitmap);
            holder.imgFilm.setContentDescription(holder.gambar);
        } catch (FileNotFoundException er){
            er.printStackTrace();
            Toast.makeText(context, "Gagal dalam mengambil gambar", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public int getItemCount()  {
        return dataFilm.size();
    }

        public class FilmViewHolder extends RecyclerView.ViewHolder implements OnClickListener, View.OnLongClickListener{

            private ImageView imgFilm;
            private TextView tvJudul, tvHeadline;
            private int idFilm;
            private String tanggal, gambar, caption, penulis, link;

            public FilmViewHolder(@NonNull View itemView) {
                super(itemView);

                imgFilm = itemView.findViewById(R.id.iv_film);
                tvJudul = itemView.findViewById(R.id.tv_judul);
                tvHeadline = itemView.findViewById(R.id.tv_headline);

                itemView.setOnClickListener(this);
                itemView.setOnLongClickListener(this);
    }
            @Override
            public void onClick(View v) {

                Intent bukaFilm = new Intent(context, TampilActivity.class);
                bukaFilm.putExtra("ID", idFilm);
                bukaFilm.putExtra("JUDUL", tvJudul.getText().toString());
                bukaFilm.putExtra("TANGGAL", tanggal);
                bukaFilm.putExtra("GAMBAR", gambar);
                bukaFilm.putExtra("CAPTION", caption);
                bukaFilm.putExtra("PENULIS", penulis);
                bukaFilm.putExtra("ISI_FILM", tvHeadline.getText().toString());
                bukaFilm.putExtra("LINK", link);
                context.startActivity(bukaFilm);

            }

            @Override
            public boolean onLongClick(View v) {

                Intent bukaInput = new Intent(context, InputActivity.class);
                bukaInput.putExtra("ID", idFilm);
                bukaInput.putExtra("JUDUL", tvJudul.getText().toString());
                bukaInput.putExtra("TANGGAL", tanggal);
                bukaInput.putExtra("GAMBAR", gambar);
                bukaInput.putExtra("CAPTION", caption);
                bukaInput.putExtra("PENULIS", penulis);
                bukaInput.putExtra("ISI_FILM", tvHeadline.getText().toString());
                bukaInput.putExtra("LINK", link);
                context.startActivity(bukaInput);
                return true;
            }
        }
    }

