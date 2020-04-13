package com.example.loginrealm.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.loginrealm.R;
import com.example.loginrealm.interfaces.OnAnimalesInteractionListener;
import com.example.loginrealm.models.Animales;

import java.util.List;

import io.realm.OrderedRealmCollection;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmResults;

public class MyAnimalRecyclerViewAdapter extends RecyclerView.Adapter<MyAnimalRecyclerViewAdapter.ViewHolder> {

    private final RealmResults<Animales> mValues;
    private final OnAnimalesInteractionListener mListener;
    private Context ctx;
    private RealmChangeListener listenerRefresco;


    public MyAnimalRecyclerViewAdapter(Context context, RealmResults<Animales> items, OnAnimalesInteractionListener listener) {
        mValues = items;
        mListener = listener;
        ctx = context;

        this.listenerRefresco = new RealmChangeListener<OrderedRealmCollection<Animales>>() {
            @Override
            public void onChange(OrderedRealmCollection<Animales> results) {
                notifyDataSetChanged();
            }
        };

        if (items != null) {
            addListener(items);
        }

    }


    private void addListener(OrderedRealmCollection<Animales> items) {
        if (items instanceof RealmResults) {
            RealmResults realmResults = (RealmResults) items;
            realmResults.addChangeListener(listenerRefresco);
        } else if (items instanceof RealmList) {
            RealmList<Animales> list = (RealmList<Animales>) items;
            //noinspection unchecke
            list.addChangeListener((RealmChangeListener) listenerRefresco);
        } else {
            throw new IllegalArgumentException("RealmCollection not supported: " + items.getClass());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.animal_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        holder.txtNombre.setText(holder.mItem.getNombre());
        holder.txtColor.setText(holder.mItem.getColor());
        holder.txtRaza.setText(holder.mItem.getRaza());

        /*Glide.with(ctx)
                .load(holder.mItem.getUrlFoto())
                .into(holder.imgFoto);*/

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onAnimalClick(holder.mItem);
                }
            }
        });

        holder.imgEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onAnimalEdit(holder.mItem);
                }
            }
        });

        holder.imgEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onAnimalEliminar(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;//MI VISTA ANIMAL ITEM
        public final TextView txtNombre;
        public final TextView txtRaza;
        public final TextView txtColor;
        public final ImageView imgFoto;
        public final ImageView imgEditar;
        public final ImageView imgEliminar;
        public Animales mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            txtNombre = (TextView) view.findViewById(R.id.textViewNombre);
            txtColor = (TextView) view.findViewById(R.id.textViewColor);
            txtRaza = (TextView) view.findViewById(R.id.textViewRaza);
            imgFoto = (ImageView) view.findViewById(R.id.imageViewFoto);
            imgEditar = (ImageView) view.findViewById(R.id.imageViewEditar);
            imgEliminar = (ImageView) view.findViewById(R.id.imageViewBorrar);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + txtNombre.getText() + "'";
        }
    }
}
