package com.example.pokemons;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PokemonsDecorator extends RecyclerView.ItemDecoration {
    private final int margin;

    public PokemonsDecorator(int margin) {
        this.margin = margin;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildLayoutPosition(view);
        if (position != 0) {
            outRect.top = margin;
        }
    }
}
