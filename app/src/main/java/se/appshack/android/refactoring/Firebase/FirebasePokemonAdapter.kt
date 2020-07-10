package se.appshack.android.refactoring.Firebase

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.pokemon_firebase_list.view.*
import se.appshack.android.refactoring.R
import java.text.FieldPosition


class FirebasePokemonAdapter (var context: Context, var pokeFire : List<PokemonFirebaseClass>) : RecyclerView.Adapter<FirebasePokemonAdapter.PokemonFirebaseViewHolder>(){



    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PokemonFirebaseViewHolder {

        var view = LayoutInflater.from(context).inflate(R.layout.pokemon_firebase_list, p0,false)

        return PokemonFirebaseViewHolder(view)


    }

    override fun getItemCount(): Int {
        return pokeFire.size
    }

    override fun onBindViewHolder(holder: PokemonFirebaseViewHolder, position: Int) {

        var pokemons = pokeFire[position]

        var pName = holder.itemView.namePokemon
        var pNumber = holder.itemView.numberPokemon
        var pimgFront = holder.itemView.imgPokemonFront
        pName.text = pokemons.name
        pNumber.text = "# " + pokemons.number
        var imageUrlFront = pokemons.imageFront
       Picasso.with(context).load(imageUrlFront).into(pimgFront)


    }


    class PokemonFirebaseViewHolder(itemPokemon : View) : RecyclerView.ViewHolder(itemPokemon){


    }
}