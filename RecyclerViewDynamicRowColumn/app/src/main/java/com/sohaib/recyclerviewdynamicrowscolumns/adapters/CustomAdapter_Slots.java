package com.sohaib.recyclerviewdynamicrowscolumns.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.sohaib.recyclerviewdynamicrowscolumns.R;

import java.util.List;

public class CustomAdapter_Slots extends RecyclerView.Adapter<CustomAdapter_Slots.CustomViewHolder> {

    private final List<Boolean> slotList;

    public CustomAdapter_Slots(List<Boolean> slotList) {
        this.slotList = slotList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_slot, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        boolean slot = slotList.get(position);
        if (slot)
            holder.siv_slot.setImageResource(R.drawable.ic_check);
        else
            holder.siv_slot.setImageResource(R.drawable.ic_uncheck);

        holder.itemView.setOnClickListener(view -> {
            boolean tempSlot = slotList.get(position);
            if (!tempSlot) {
                tempSlot = true;
                holder.siv_slot.setImageResource(R.drawable.ic_check);
            } else {
                tempSlot = false;
                holder.siv_slot.setImageResource(R.drawable.ic_uncheck);
            }
            slotList.set(position, tempSlot);
        });
    }

    @Override
    public int getItemCount() {
        return slotList.size();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {

        private final ShapeableImageView siv_slot;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            siv_slot = itemView.findViewById(R.id.siv_slot_List_Item_Slot);
        }
    }
}