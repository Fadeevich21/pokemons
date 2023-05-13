package com.example.pokemons.pokemon;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PokemonDecorator extends RecyclerView.ItemDecoration {
    private final int top;
    private final int left;

    public PokemonDecorator(int top, int left) {
        this.top = top;
        this.left = left;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.left = left;
        outRect.top = top;
    }
}
