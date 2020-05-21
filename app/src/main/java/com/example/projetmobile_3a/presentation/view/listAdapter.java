package com.example.projetmobile_3a.presentation.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;



import com.bumptech.glide.Glide;
import com.example.projetmobile_3a.Constants;
import com.example.projetmobile_3a.R;
import com.example.projetmobile_3a.presentation.model.Character;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class listAdapter extends RecyclerView.Adapter<listAdapter.ViewHolder> {
    private List<Character> values;
    Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Character item);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
     class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        ImageView imageDB;
         TextView txtHeader;
         TextView txtFooter;
         View layout;

         ViewHolder(View v) {
            super(v);
            layout = v;

            imageDB = (ImageView) v.findViewById(R.id.icon);
            txtHeader = (TextView) v.findViewById(R.id.firstLine);
            txtFooter = (TextView) v.findViewById(R.id.secondLine);
        }
    }

    public void add(int position, Character item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

     public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, values.size());
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public listAdapter(List<Character> myDataset, OnItemClickListener listener) {
        this.values = myDataset;
        this.listener = listener;
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public listAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(
                context);
        View v =
                inflater.inflate(R.layout.row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Character currentCharacter = values.get(position);

        //Load image
        if(currentCharacter.getImage().charAt(0) == '.') {
            Glide.with(context).load(Constants.BASE_URL + currentCharacter.getImage()).circleCrop().into(holder.imageDB);
        }else Glide.with(context).load(currentCharacter.getImage()).circleCrop().into(holder.imageDB);

        holder.txtHeader.setText(currentCharacter.getName());
        holder.txtHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(position);
            }
        });

        holder.txtFooter.setText(currentCharacter.getSpecies());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(currentCharacter);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }

}
