package com.example.calenderapp.ui.course_details;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calenderapp.R;
import com.example.calenderapp.databinding.ListModulesBinding;
import com.example.calenderapp.model.Module;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ModuleAdapter extends RecyclerView.Adapter<ModuleAdapter.Vh> {
    List<Module> list;

    public ModuleAdapter(List<Module> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.list_modules,parent,false);
        return new Vh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vh holder, int position) {
        Module module=list.get(position);
        holder.binding.tvTitle.setText(module.getName());
        holder.binding.tvType.setText(new SimpleDateFormat("dd/MM/yyyy hh:mm").format(new Date(module.getTime()))+"  "+module.getType());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class Vh extends RecyclerView.ViewHolder {
        ListModulesBinding binding;
        public Vh(@NonNull View itemView) {
            super(itemView);
            binding=ListModulesBinding.bind(itemView);
        }
    }
}
