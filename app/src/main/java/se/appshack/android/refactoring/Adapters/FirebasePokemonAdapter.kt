package se.appshack.android.refactoring.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.pokemon_firebase_list.view.*
import se.appshack.android.refactoring.ModelClasses.PokemonFirebaseClass
import se.appshack.android.refactoring.R


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

        var deletePoke = holder.itemView.deletePokemonBtn

        val firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser
        var userNode = firebaseUser!!.uid
        Log.w("Tag"," UserId : =====>>>> " + userNode)


        deletePoke.setOnClickListener {
            val db = FirebaseDatabase.getInstance()
            val myRef = db.getReference(userNode)
            myRef.child(pokemons.name).removeValue()


        }



    }


    class PokemonFirebaseViewHolder(itemPokemon : View) : RecyclerView.ViewHolder(itemPokemon){


    }
}